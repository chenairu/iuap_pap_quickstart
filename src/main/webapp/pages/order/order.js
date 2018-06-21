define(['text!./order.html',
    "css!../../style/common.css",
    'css!./order.css',
    './viewModel.js',
    '../../config/sys_const.js',
    "../../utils/utils.js",
    "../../utils/pjt-common.js",
    'uiReferComp',
    'uiNewReferComp',
    'refer'], function (template) {
        var listRowUrl, saveRowUrl, delRowUrl, getUrl, subGridListUrl, subGridDeleteUrl, element;
        function init(element) {
            element = element;
            $(element).html(template);
            listRowUrl = "/demo_order/list";
            saveRowUrl = "/demo_order/save";
            delRowUrl = "/demo_order/delete";
            getUrl = "/demo_order/get";
            subGridListUrl = "/demo_order_detail/list";
            subGridDeleteUrl = "/demo_order_detail/delete";
            viewModel.event.pageinit(element);
            //撑满高度布局
            $("#myLayout").height(document.body.scrollHeight);
        }

        viewModel.event = {
            pageinit: function (element) {
                viewModel.app = u.createApp({
                    el: element,
                    model: viewModel
                });
                //清除主表格数据
                viewModel.gridData.clear();
                //设置表格每页面数据量
                viewModel.gridData.pageSize(10);
                viewModel.subGridData.pageSize(10);
                viewModel.condition.clear();
                viewModel.condition.createEmptyRow();
                viewModel.condition.setRowSelect(0);
                viewModel.event.queryData();
            },
            //表格分页
            pageChange: function (index) {
                viewModel.gridData.pageIndex(index);
                viewModel.event.queryData();
            },

            //当前页显示记录数
            sizeChange: function (size) {
                viewModel.gridData.pageSize(size);
                viewModel.gridData.pageIndex(0);
                viewModel.event.queryData();
            },

            //查询数据
            queryData: function () {
                var queryParameters = {};
                queryParameters["pageIndex"] = viewModel.gridData.pageIndex();
                queryParameters["pageSize"] = viewModel.gridData.pageSize();
                var searchinfo = viewModel.gridData.params;
                for (var key in searchinfo) {
                    if (searchinfo[key] && searchinfo[key] != null) {
                        queryParameters[key] = encodeURI(removeSpace(searchinfo[key]));
                    }
                }
                pjt.ajaxQueryData(listRowUrl, queryParameters, function (data) {
                    if (data != null) {
                        viewModel.gridData.setSimpleData(data.content, { unSelect: true });
                        viewModel.gridData.totalPages(data.totalPages);
                        viewModel.gridData.totalRow(data.totalElements);
                    }
                }, function (data) {
                    pjt.message(data);
                });
            },

            // 新增按钮点击
            addBtnClicked: function () {
                viewModel.formData.clear();
                viewModel.formData.createEmptyRow();
                viewModel.formData.setRowSelect(0);
                pjt.showDiv('#form-div');
                document.getElementById("myTitle").innerHTML = "新增记录";
            },

            //编辑按钮点击
            editBtnClicked: function () {
                var currentData = viewModel.gridData.getSimpleData({ type: 'select' });
                if (currentData != null && currentData != "") {
                    pjt.showDiv('#form-div');
                    document.getElementById("myTitle").innerHTML = "编辑记录";
                    var id = currentData[0].id;
                    var jsonData = {
                        sortField: "ts",
                        sortDirection: "asc"
                    };
                    jsonData["pageIndex"] = viewModel.subGridData.pageIndex();
                    jsonData["pageSize"] = viewModel.subGridData.pageSize();
                    jsonData['search_orderId'] = id;
                    pjt.ajaxQueryData(getUrl, jsonData, function (result) {
                        console.log(result);
                        //表单数据
                        var curFormData = result.data;
                        viewModel.formData.clear();
                        viewModel.formData.setSimpleData(curFormData);
                        // 子表数据
                        viewModel.subGridData.removeAllRows();
                        viewModel.subGridData.clear();
                        var subPage = result.subPage;
                        viewModel.subGridData.setSimpleData(subPage.content, { unSelect: true });
                        viewModel.subGridData.totalPages(subPage.totalPages);
                        viewModel.subGridData.totalRow(subPage.totalElements);
                    }, function (data) {
                        iuap.message("请求数据异常!");
                    })
                } else {
                    pjt.message("请选择要编辑的数据！");
                }
            },

            // 返回按钮点击
            backBtnClick: function () {
                viewModel.formData.clear();
                pjt.hideDiv('#form-div');
            },

            //保存按钮点击
            saveClick: function () {
                //form表单校验
                if (!viewModel.app.compsValidate($(element).find("#addPage")[0])) {
                    pjt.message("请检查必填项");
                    return;
                }
                var data = viewModel.formData.getSimpleData()[0];
                // 添加子表信息
                var subData = viewModel.subGridData.getSimpleData();
                data.orderDetails = subData;
                pjt.ajaxSaveData(saveRowUrl, data, function (result) {
                    viewModel.formData.clear();
                    pjt.hideDiv('#form-div');
                    viewModel.event.queryData();
                });
            },

            //删除按钮点击
            delRows: function (data) {
                var currentData = viewModel.gridData.getSimpleData({ type: 'select' });
                if (currentData != null && currentData != "") {
                    u.confirmDialog({
                        msg:
                            '<div class="pull-left col-padding u-msg-content-center" >' +
                            '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                        title: "",
                        onOk: function () {
                            viewModel.event.del(currentData)
                        }
                    });
                } else {
                    pjt.message("请选择要删除的数据！");

                }
            },

            //真正删除逻辑
            del: function (data) {
                var arr = [];
                for (var i = 0; i < data.length; i++) {
                    arr.push({
                        id: data[i].id
                    });
                }
                pjt.ajaxDelData(delRowUrl, arr, function (result) {
                    pjt.message("删除成功！");
                    viewModel.event.queryData();
                });
            },
            // 搜索
            search: function () {
                viewModel.gridData.clear();
                var conditions = viewModel.condition.getSimpleData();
                if (conditions != null && conditions != "") {
                    viewModel.gridData.addParams(conditions[0]);
                }
                viewModel.event.queryData();
            },
            // 清除搜索
            cleanSearch: function () {
                viewModel.condition.clear();
                viewModel.condition.createEmptyRow();
                viewModel.condition.setRowSelect(0);
                viewModel.gridData.addParams(null);
            },

            // 子表增一行
            addSubGridRow: function () {
                viewModel.subGridData.createEmptyRow();
            },

            //表格分页
            subPageChange: function (index) {
                viewModel.subGridData.pageIndex(index);
                // viewModel.event.queryData();
            },
            //当前页显示记录数
            sizeChange: function (size) {
                viewModel.subGridData.pageSize(size);
                viewModel.subGridData.pageIndex(0);
                // viewModel.event.queryData();
            },
            //子表行——悬浮事件
            rowHover4SubGrid: function (obj) {
                $(".editTable").hide();
                var num = obj["rowIndex"];
                var a = $("#subDataGrid_content_tbody").find("tr")[num];
                var ele = a.getElementsByClassName("editTable");
                ele[0].style.display = "block";
            },
            //子表行——操作列
            optFunc4SubGrid: function (obj) {
                var curRowId = obj.row.value["$_#_@_id"];
                var rownum = obj.rowIndex;
                var delFunc = "data-bind=click:event.subDeleteClick.bind($data," + curRowId + "," + rownum + ")";
                obj.element.innerHTML = '<div class="editTable" style="display:none;">' +
                    '<button class="u-button u-button-border" title="删除" ' + delFunc + ">删除</button></div>";
                ko.cleanNode(obj.element);
                ko.applyBindings(viewModel, obj.element);
            },
            //子表删除行
            subDeleteClick: function (rowId, rowIndex) {
                if (rowId && rowId != -1) {
                    var datatableRow = viewModel.subGridData.getRowByRowId(rowId);
                    var rowData = datatableRow.getSimpleData();
                    viewModel.event.doDelete4SubGrid(rowData, rowIndex);
                }
            },
            //删除行
            doDelete4SubGrid: function (data, rowIndex) {
                if (data.id == null || data.id == "") {//空行
                    viewModel.subGridData.removeRow(rowIndex);
                    return;
                }
                u.confirmDialog({
                    msg: '<div class="pull-left col-padding u-msg-content-center" >' +
                        '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                    title: '提示',
                    onOk: function () {
                        var ids = [data.id];
                        pjt.ajaxDelData(subGridDeleteUrl, ids, function (data) {
                            pjt.message("删除成功！");
                            viewModel.subGridData.removeRow(rowIndex);
                        }, function () {
                            pjt.message("删除失败");
                        })
                    }
                });
            }
        }
        return {
            template: template,
            init: init
        };
    });