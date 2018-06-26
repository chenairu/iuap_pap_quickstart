define(['text!./workorder.html',
    'cookieOperation',
    '/eiap-plus/pages/flow/bpmapproveref/bpmopenbill.js',
    "css!../../style/common.css",
    'css!./workorder.css',
    '../../config/sys_const.js',
    "../../utils/utils.js",
    "../../utils/pjt-common.js",
    "./viewModel.js",
    'uiReferComp',
    'uiNewReferComp',
    'refer'], function (template, cookie, bpmopenbill) {
        var element;
        var listRowUrl = "/example_workorder/list"; //列表查询URL
        var saveRowUrl = "/example_workorder/batchSave"; //新增和修改URL， 有id为修改 无id为新增
        var delRowUrl = "/example_workorder/delete"; //刪除URL
        var getUrl = "/example_workorder/get";
        var submitUrl = '/example_workorder/submit';
        var recallUrl = '/example_workorder/recall';
        function init(element, cookie) {
            element = element;
            $(element).html(template);
            //合并bpm model 和 viewModel
            viewModel = $.extend({}, viewModel, bpmopenbill.model); //扩展viewModel
            if (cookie && cookie.vtype && cookie.vtype == 'bpm') {
                viewModel.flowEvent.initAuditPage(element, cookie);
            } else {
                viewModel.event.pageinit(element);
            }
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
                queryParameters["sortField"] = "lastModified";
                queryParameters["sortDirection"] = "desc";
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

            //编辑按钮点击,只有未提交过的工单才能编辑。
            editBtnClicked: function () {
                var currentData = viewModel.gridData.getSimpleData({ type: 'select' });
                if (currentData != null && currentData != "") {
                    viewModel.formData.setSimpleData(currentData[0]);
                    if (currentData[0].status != 0) {
                        pjt.message("只能编辑未提交的工单");
                        return;
                    }
                    pjt.showDiv('#form-div');
                    document.getElementById("myTitle").innerHTML = "编辑记录";
                } else {
                    pjt.message("请选择要编辑的数据！");
                }
            },

            // 返回按钮点击
            backBtnClick: function () {
                viewModel.formData.clear();
                pjt.hideDiv('#form-div');
            },

            //保存按钮点击，只有工单的状态为未提交的时候才能修改。
            saveClick: function () {
                //form表单校验
                if (!viewModel.app.compsValidate($(element).find("#addPage")[0])) {
                    pjt.message("请检查必填项");
                    return;
                }
                var data = viewModel.formData.getSimpleData()[0];
                if (data.type == "投诉工单") {
                    data.type = 0;
                } else {
                    data.type = 1;
                }
                data.status = 0;//

                //由于后台要求传递list对象。所以做了list组装，如果后台没有则不需要组装list
                var listData = pjt.genDataList(data);
                pjt.ajaxSaveData(saveRowUrl, listData, function (result) {
                    viewModel.formData.clear();
                    pjt.hideDiv('#form-div');
                    viewModel.event.queryData();
                });
            },

            //删除按钮点击
            delRows: function (data) {
                var currentData = viewModel.gridData.getSimpleData({ type: 'select' });
                if (currentData != null && currentData != "") {
                    if (currentData[0].status != 0) {
                        pjt.message("只能删除未提交的工单");
                        return;
                    }
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

            //value转name
            typeRender: function (obj) {
                var innerHtml = "";
                var ty = obj.row.value.type;
                if (ty == "0") {
                    innerHtml = "<div>投诉工单</div>";
                }
                else if (ty == "1") {
                    innerHtml = "<div>对账工单</div>";
                }
                obj.element.innerHTML = innerHtml
                ko.applyBindings(viewModel, obj.element);
            },
            //value转name
            statusRender: function (obj) {
                var innerHtml = "";
                var ty = obj.row.value.status;
                if (ty == "0") {
                    innerHtml = "<div>未提交</div>";
                }
                else if (ty == "1") {
                    innerHtml = "<div>已提交</div>";
                }
                else if (ty == "2") {
                    innerHtml = "<div>审批中</div>";
                }
                else if (ty == "3") {
                    innerHtml = "<div>已完结</div>";
                }
                obj.element.innerHTML = innerHtml
                ko.applyBindings(viewModel, obj.element);
            },
        }
        //流程事件定义
        viewModel.flowEvent = {
            //提交工作流
            submit: function () {
                var selectArray = viewModel.gridData.selectedIndices();
                if (selectArray.length != 1) {
                    pjt.message("请保证选中且只选中一条记录")
                    return;
                }
                var selectedData = viewModel.gridData.getSimpleData({
                    type: "select"
                });

                if (selectedData[0].state && selectedData[0].state != "0") {
                    //状态不为"未提交"
                    message("该单据已经使用关联流程，不能启动", "error");
                    return;
                }
                var checkUrl = "/eiap-plus/appResAllocate/queryBpmTemplateAllocate?funccode=" + getAppCode() + "&nodekey=workorder_001";
                pjt.ajaxQueryThridService(checkUrl, {}, function (data) {
                    console.log("OK:", data);
                    var processDefineCode = data.res_code;
                    viewModel.flowEvent.submitBPMByProcessDefineCode(selectedData, processDefineCode);
                }, function (data) {
                    pjt.message("请求数据错误!");
                })
            },
            submitBPMByProcessDefineCode: function (selectedData, processDefineCode) {
                var postUrl = submitUrl + "?processDefineCode=" + processDefineCode;
                pjt.ajaxSaveData(postUrl, selectedData, function (data) {
                    pjt.message("流程提交成功");
                    //TODO
                }, function (data) {
                    pjt.message("流程提交失败");
                })
            },
            //查看工单
            doView: function () {
                var selectArray = viewModel.gridData.selectedIndices();
                if (selectArray.length != 1) {
                    pjt.message("请保证选中且只选中一条记录")
                    return;
                }
                var rows = viewModel.gridData.getSimpleData({
                    type: "select"
                });

                var rowData = rows[0];

                var url = getUrl + "?id=" + rowData.id;
                pjt.ajaxQueryData(url, null, function (data) {
                    //加入bpm按钮
                    viewModel.initBPMFromBill(rowData.id, viewModel);

                    viewModel.formData.clear();
                    viewModel.formData.setSimpleData(data);
                    // 把卡片页面变成不能编辑
                    $('#myForm').each(function (index, element) {
                        $(element).find('input[type!="radio"]').attr('disabled', true);
                        $(element).find('textarea').attr('disabled', true);
                    });
                    pjt.showDiv('#form-div');
                    pjt.hideDiv('#form-div-header');
                }, function (data) {
                    console.log("error:", data);
                })
            },
            //撤回工单
            recall: function () {
                var selectedData = viewModel.gridData.getSimpleData({ type: 'select' });
                pjt.ajaxSaveData(recallUrl, selectedData, function (data) {
                    pjt.message(data);
                }, function (data) {
                    pjt.message(data);
                })
            },
            //审批单据打开页面,这是从任务中心打开的
            initAuditPage: function (element, arg) {
                var app = u.createApp({
                    el: element,
                    model: viewModel
                });
                viewModel.initBpmFromTask(arg, viewModel);					//初始化BPM相关内容(添加审批操作头部和审批相关弹出框的代码片段)
                var url = getUrl + "?id=" + arg.id;
                pjt.ajaxQueryData(url, null, function (data) {
                    viewModel.formData.clear();
                    viewModel.formData.setSimpleData(data);
                    // 把卡片页面变成不能编辑
                    $('#myForm').each(function (index, element) {
                        $(element).find('input[type!="radio"]').attr('disabled', true);
                        $(element).find('textarea').attr('disabled', true);
                    });
                    pjt.showDiv('#form-div');
                    pjt.hideDiv('#form-div-header');
                }, function (data) {
                    alert(2);
                })
            }
        }
        return {
            template: template,
            init: init
        };
    });