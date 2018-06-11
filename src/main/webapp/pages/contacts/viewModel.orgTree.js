define(["../../config/sys_const.js",
    "../../utils/iuap-common.js",
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js"], function () {
        var meta = {
            meta: {
                institcode: {
                    type: "string",
                    required: true,
                    notipFlag: true,
                    hasSuccess: true,
                    nullMsg: "机构编码不能为空!"
                },
                institname: {
                    type: "string",
                    required: true,
                    notipFlag: true,
                    hasSuccess: true,
                    nullMsg: "机构名称不能为空!"
                },
                institid: {
                    type: "string"
                },
                parentid: {
                    type: "string"
                },
                parentname: {
                    type: "string"
                },
                shortname: {
                    type: "string"
                }
            }
        };

        // 请求服务地址
        var treeListUrl = appCtx + "/instit/list";
        var treeDelUrl = appCtx + "/instit/del/";
        var treeSaveUrl = appCtx + "/instit/save";

        var viewModel = {
            treeid: [],
            app: {},
            orgTreeData: new u.DataTable(meta),//tree数据
            orgFormData: new u.DataTable(meta),//tree form表单
            // 树设置
            treeSetting: {
                view: {
                    showLine: false,
                    selectedMulti: false
                },
                callback: {
                    onClick: function (e, id, node) {
                        treeid = [];//重置
                        viewModel.event.getAllChildren(node, treeid);
                        $("#myLayout").trigger("treeNodeClicked", [node.id, node.name, treeid]);//触发自定义事件,并传递当前节点Id，以及其所有子孙节点Id
                    }
                }
            },
            event: {
                pageInit: function () {
                    viewModel.app = u.createApp({
                        el: '#orgNode',
                        model: viewModel
                    });
                    this.loadTree();
                },

                // 加载组织机构树
                loadTree: function () {
                    $.ajax({
                        type: "get",
                        url: treeListUrl,
                        dataType: "json",
                        success: function (res) {
                            if (res) {
                                if (res.success == "success") {
                                    if (res.detailMsg.data) {
                                        viewModel.orgTreeData.removeAllRows();
                                        viewModel.orgTreeData.clear();
                                        viewModel.orgTreeData.setSimpleData(res.detailMsg.data, {
                                            unSelect: true
                                        });
                                        $("#orgTree")[0]["u-meta"].tree.expandAll(true);
                                    }
                                } else {
                                    var msg = "";
                                    if (res.success == "fail_global") {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    iuap.message(msg);
                                }
                            } else {
                                iuap.message("数据格式错误");
                            }
                        },
                        error: function (er) {
                            iuap.message("请求错误");
                        }
                    });

                },
                // 新增组织机构按钮点击
                addinstitClick: function () {
                    $("#institcode").removeAttr("readonly");
                    $("#dialog_content_instit").find(".u-msg-title")
                        .html("<h4>新增机构</h4>");
                    viewModel.event.clearDt(viewModel.orgFormData);
                    var row = viewModel.orgTreeData.getSelectedRows()[0];

                    if (row) {
                        var parentId = row.getValue("institid");
                        var parentName = row.getValue("institname");
                    }

                    var newr = viewModel.orgFormData.createEmptyRow();
                    viewModel.orgFormData.setRowSelect(newr);

                    if (row) {
                        var newrow = viewModel.orgFormData.getSelectedRows()[0];
                        newrow.setValue("parentid", parentId);
                        newrow.setValue("parentname", parentName);
                    }

                    window.md = u.dialog({
                        id: "add_depart",
                        content: "#dialog_content_instit",
                        hasCloseMenu: true
                    });
                },
                // 修改组织机构按钮被点击
                editinstitClick: function () {
                    $("#dialog_content_instit").find(".u-msg-title")
                        .html("<h4>编辑机构</h4>");
                    viewModel.event.clearDt(viewModel.orgFormData);
                    var row = viewModel.orgTreeData.getSelectedRows()[0];
                    if (row) {
                        if (row.data.parentid.value) {
                            row.setValue("parentname", $("#orgTree")[0]["u-meta"].tree.getNodeByParam("id", row.getValue("parentid")).name);
                        }
                        viewModel.orgFormData.setSimpleData(viewModel.orgTreeData.getSimpleData({
                            type: "select"
                        }));
                        $("#institcode").attr("readonly", "readonly");
                        window.md = u.dialog({
                            id: "edit_depart",
                            content: "#dialog_content_instit",
                            hasCloseMenu: true
                        });
                        $('.u-msg-dialog').css('width', '800px')
                    } else {
                        iuap.message("请选择要编辑的数据！");
                    }
                },
                // 删除组织机构按钮被点击
                delinstitClick: function () {
                    var row = viewModel.orgTreeData.getSelectedRows()[0];
                    if (row) {
                        var data = viewModel.orgTreeData
                            .getSelectedRows()[0].getSimpleData();
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
                        iuap.message("请选择要删除的数据！");
                    }
                },
                // 新增或修改组织机构保存按钮被点击
                saveinstitClick: function () {
                    var data = viewModel.orgFormData.getSelectedRows()[0].getSimpleData();
                    if (!viewModel.app.compsValidate($("#dialog_content_instit")[0])) {
                        return;
                    }
                    viewModel.event.saveTree(data);
                },
                // 新增或修改组织机构取消按钮被点击
                cancelinstitClick: function () {
                    md.close();
                },
                //新增和更新组织机构
                saveTree: function (data) {
                    var list = viewModel.event.genDataList(data);
                    $.ajax({
                        type: "post",
                        url: treeSaveUrl,
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(list),
                        success: function (res) {
                            if (res) {
                                if (res.success == "success") {
                                    iuap.message("保存成功");
                                    viewModel.event.loadTree();
                                    md.close();
                                } else {
                                    var msg = "";
                                    if (res.success == "fail_global") {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key]
                                                + "<br/>";
                                        }
                                    }
                                    iuap.message(msg);
                                }
                            } else {
                                iuap.message("没有返回数据")
                            }
                        }
                    });
                },
                //删除组织机构
                deleteTree: function (data) {
                    var datalist = viewModel.event.genDataList(data);
                    var json = JSON.stringify(datalist);
                    $.ajax({
                        url: treeDelUrl,
                        data: json,
                        dataType: "json",
                        type: "post",
                        contentType: "application/json",
                        success: function (res) {
                            if (res) {
                                if (res.success == "success") {
                                    viewModel.orgTreeData.removeRow(viewModel.orgTreeData.getSelectedIndexs());
                                    iuap.message("删除成功");
                                } else {
                                    var msg = "";
                                    if (res.success == "fail_global") {
                                        msg = res.message;
                                        if (msg == "Data had been referenced,remove is forbidden!") {
                                            msg = "该部门含有子部门不能删除";
                                        }
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key]
                                                + "<br/>";
                                        }
                                    }
                                    iuap.message(msg);
                                }
                            } else {
                                iuap.message("无返回数据");
                            }
                        },
                        error: function (er) {
                            iuap.message("操作请求失败，" + er);
                        }
                    });
                },

                //清除datatable数据
                clearDt: function (dt) {
                    dt.removeAllRows();
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
                //组装list
                genDataList: function (data) {
                    var datalist = [];
                    datalist.push(data);
                    return datalist;
                },
            }
        };
        return { viewModel }
    })