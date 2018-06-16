define(["text!./contacts.html",
    "css!../../style/common.css",
    "css!./contacts.css",
    "../../config/sys_const.js",
    "../sever.js",
    "../../utils/pjt-common.js",
    "./viewModel.js",
], function (html) {
    var element;
    var treeListUrl, treeDelUrl, treeSaveUrl, tableListUrl, tableDelUrl, tableSaveUrl;
    function init(element, cookie) {
        element = element;
        $(element).html(html);
        treeListUrl = "/instit/list";
        treeDelUrl = "/instit/del";
        treeSaveUrl = "/instit/save";
        tableListUrl = "/telBook/list";
        tableDelUrl = "/telBook/del";
        tableSaveUrl = "/telBook/save";
        viewModel.event.pageInit(element);
        if (u.isIE8) {
            $(".ie8-bg").css("display", "block");
        }
        //  调整div高度适应满屏，树的高度固定后才会有滚动条
        $("#myLayout").height(document.body.scrollHeight);
        $("#orgTree").height(document.body.scrollHeight - 100);
    };
    viewModel.event = {
        //页面初始化
        pageInit: function (element) {
            treeid = [];
            viewModel.app = u.createApp({
                el: element,
                model: viewModel
            });
            viewModel.md = document.querySelector('#u-mdlayout');
            viewModel.gridData.clear();
            viewModel.gridData.pageSize(10);
            viewModel.condition.clear();
            viewModel.condition.createEmptyRow();
            viewModel.condition.setRowSelect(0);
            this.loadTree();
            //回车搜索
            $(".input_enter").keydown(function (e) {
                if (e.keyCode == 13) {
                    viewModel.event.loadTelbook(treeid);
                    u.stopEvent(e);
                }
            });
        },

        //加载组织机构树
        loadTree: function () {
            pjt.ajaxQueryData(treeListUrl, '', function (data) {
                if (data != null) {
                    viewModel.treeData.removeAllRows();
                    viewModel.treeData.clear();
                    viewModel.treeData.setSimpleData(data, {
                        unSelect: true
                    });
                    $("#orgTree")[0]["u-meta"].tree.expandAll(true);
                }
            }, function (data) {
                pjt.message(data);
            });
        },
        /* 获得树节点的所有子节点 */
        getAllChildren: function (node, childrenlist) {
            childrenlist.push(node.id);
            if (node.children) {
                var i;
                for (i = 0; i < node.children.length; i++) {
                    viewModel.event.getAllChildren(
                        node.children[i], childrenlist);
                }
            }
        },
        //新增组织机构按钮点击
        addinstitClick: function () {
            $("#institcode").removeAttr("readonly");
            $("#dialog_content_instit").find(".u-msg-title").html("<h4>新增机构</h4>");
            viewModel.formData_org.removeAllRows();
            var newr = viewModel.formData_org.createEmptyRow();
            viewModel.formData_org.setRowSelect(newr);
            // 获取当前选中的treeNode
            var row = viewModel.treeData.getSelectedRows()[0];
            if (row) {
                var parentId = row.getValue("institid");
                var parentName = row.getValue("institname");
                var newrow = viewModel.formData_org.getSelectedRows()[0];
                newrow.setValue("parentid", parentId);
                newrow.setValue("parentname", parentName);
            }
            window.md = u.dialog({
                id: "add_depart",
                content: "#dialog_content_instit",
                hasCloseMenu: true
            });
        },
        // 编辑组织机构按钮点击
        editinstitClick: function () {
            $("#dialog_content_instit").find(".u-msg-title").html("<h4>编辑机构</h4>");
            viewModel.formData_org.removeAllRows();
            var row = viewModel.treeData.getSelectedRows()[0];
            if (row) {
                if (row.data.parentid.value) {
                    row.setValue("parentname", $("#orgTree")[0]["u-meta"].tree.getNodeByParam("id", row.getValue("parentid")).name);
                }
                viewModel.formData_org.setSimpleData(viewModel.treeData.getSimpleData({ type: "select" }));
                $("#institcode").attr("readonly", "readonly");//只读
                window.md = u.dialog({
                    id: "edit_depart",
                    content: "#dialog_content_instit",
                    hasCloseMenu: true
                });
                $('.u-msg-dialog').css('width', '800px')
            } else {
                pjt.message("请选择要编辑的数据！");
            }
        },
        // 保存组织机构点击
        saveinstitClick: function () {
            if (!viewModel.app.compsValidate($("#dialog_content_instit")[0])) {
                return;
            }
            var data = viewModel.formData_org.getSimpleData()[0];
            var listData = pjt.genDataList(data);
            pjt.ajaxSaveData(treeSaveUrl, listData, function (result) {
                viewModel.formData_org.clear();
                viewModel.event.loadTree();
                md.close();
            });
        },
        //取消保存组织机构
        cancelinstitClick: function () {
            md.close();
        },
        // 删除组织机构按钮点击
        delinstitClick: function () {
            var row = viewModel.treeData.getSelectedRows()[0];
            if (row) {
                var data = viewModel.treeData.getSelectedRows()[0].getSimpleData();
                u.confirmDialog({
                    msg: "是否删除" + data.institname + "?",
                    title: "删除确认",
                    onOk: function () {
                        viewModel.event.deleteTree(data);
                    },
                    onCancel: function () {
                    }
                });
            } else {
                pjt.message("请选择要删除的数据！");
            }
        },
        //删除组织机构
        deleteTree: function (data) {
            var datalist = pjt.genDataList(data);
            pjt.ajaxDelData(treeDelUrl, datalist, function (result) {
                pjt.message("删除成功！");
                viewModel.event.loadTree();
            });
        },

        //载入表格数据
        loadTelbook: function (instit) {
            viewModel.gridData.removeAllRows();
            viewModel.gridData.clear();
            var queryParameters = {};
            queryParameters["pageIndex"] = viewModel.gridData.pageIndex();
            queryParameters["pageSize"] = viewModel.gridData.pageSize();
            queryParameters["sortField"] = "peocode";
            queryParameters["sortDirection"] = "asc";
            var searchinfo = viewModel.gridData.params;
            for (var key in searchinfo) {
                if (searchinfo[key] && searchinfo[key] != null) {
                    queryParameters[key] = encodeURI(removeSpace(searchinfo[key]));
                }
            }
            if (instit) {
                if (instit != "" || instit.length != 0) {
                    queryParameters["institid"] = instit.join();
                }
            }
            /*右表的上面详细信息显示*/
            var infoDiv = document.getElementById("infoPanel");
            var dtVal = viewModel.treeData.getValue("institname");
            infoDiv.innerHTML = dtVal;

            pjt.ajaxQueryData(tableListUrl, queryParameters, function (data) {
                if (data != null) {
                    viewModel.gridData.removeAllRows();
                    viewModel.gridData.setSimpleData(data.content, { unSelect: true });
                    viewModel.gridData.totalPages(data.totalPages);
                    viewModel.gridData.totalRow(data.totalElements);
                }
            }, function (data) {
                pjt.message(data);
            });
        },

        //表格分页
        pageChange: function (index) {
            viewModel.gridData.pageIndex(index);
            viewModel.event.loadTelbook(treeid);
        },
        //当前页显示记录数
        sizeChange: function (size) {
            viewModel.gridData.pageSize(size);
            viewModel.gridData.pageIndex(0);
            viewModel.event.loadTelbook(treeid);
        },
        //删除人员
        delMan: function (data) {
            var datalist = pjt.genDataList(data);
            pjt.ajaxDelData(tableDelUrl, datalist, function (result) {
                pjt.message("删除成功！");
                viewModel.event.loadTelbook(treeid);
            }, function (data) {
                pjt.message(data);
            });
        },

        addManClick: function () {
            $("#dialog_content_man").find(".u-msg-title").html("<h4>新增人员</h4>");
            viewModel.formData_person.clear();
            var row = viewModel.treeData.getSelectedRows()[0];
            if (row) {
                var institId = row.getValue("institid");
                var instit = row.getValue("institname");
                var newr = viewModel.formData_person.createEmptyRow();
                viewModel.formData_person.setRowSelect(newr);
                var newrow = viewModel.formData_person.getSelectedRows()[0];
                newrow.setValue("institid", institId);
                newrow.setValue("institname", instit);
                $("#add_peocode").removeAttr("readonly");//只读移除
                window.md = u.dialog({
                    id: "add_man",
                    content: "#dialog_content_man",
                    hasCloseMenu: true
                });
                $('.u-msg-dialog').css('width', '800px');
            } else {
                pjt.message("请选择部门！");
            }
        },

        editManClick: function (rowId) {
            if (!rowId || rowId === -1) {
                return;
            }
            $("#dialog_content_man").find(".u-msg-title").html("<h4>编辑人员</h4>");
            viewModel.formData_person.clear();
            var row = viewModel.gridData.getRowByRowId(rowId);
            if (row) {
                viewModel.formData_person.setSimpleData(row.getSimpleData());
                $("#add_peocode").attr("readonly", "readonly");
                window.md = u.dialog({
                    id: "edit_man",
                    content: "#dialog_content_man",
                    hasCloseMenu: true
                });
                $('.u-msg-dialog').css('width', '800px');
            } else {
                pjt.message("请选择要编辑的数据！");
            }
        },
        delManClick: function (rowId) {
            if (!rowId || rowId === -1) {
                return;
            }
            var row = viewModel.gridData.getRowByRowId(rowId);
            if (row) {
                var data = row.getSimpleData();
                u.confirmDialog({
                    msg: "是否删除" + data.peoname + "?",
                    title: "删除确认",
                    onOk: function () {
                        viewModel.event.delMan(data);
                        viewModel.gridData.removeRow(viewModel.gridData.getSelectedIndexs());
                    },
                    onCancel: function () {
                    }
                });
            } else {
                pjt.message("请选择要删除的数据！");
            }
        },
        saveManClick: function () {
            if (!viewModel.app.compsValidate($("#add-form")[0])) {
                return;
            }
            var data = viewModel.formData_person.getSimpleData()[0];
            var listData = pjt.genDataList(data);
            pjt.ajaxSaveData(tableSaveUrl, listData, function (result) {
                viewModel.formData_person.clear();
                viewModel.event.loadTelbook(treeid);
                md.close();
            });
        },
        cancelManClick: function () {
            md.close();
        },
        rowHoverHandel: function (obj) {
            $(".editTable").hide();
            let num = obj["rowIndex"];
            let a = $("#gridaddress_content_tbody").find("tr")[num];
            let ele = a.getElementsByClassName("editTable");
            ele[0].style.display = "block";
        },
        mouseoverFun: function () {
            $(".editTable").hide();
        },

        //定义操作列的内容
        optFun: function (obj) {
            var dataTableRowId = obj.row.value["$_#_@_id"];
            // 绑定删除事件
            var delfun = "data-bind=click:event.delManClick.bind($data,"
                + dataTableRowId + ")";
            // 绑定修改事件
            var editfun = "data-bind=click:event.editManClick.bind($data,"
                + dataTableRowId + ")";
            // 绑定查看事件
            obj.element.innerHTML = '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="修改"'
                + editfun
                + ">修改</button>"
                + '<button class="u-button u-button-border" title="删除" '
                + delfun + ">删除</button></div>";
            ko.applyBindings(viewModel, obj.element);
        }
    };
    return {
        template: html,
        init: init
    };
});
