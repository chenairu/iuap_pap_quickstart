define(['text!./goods.html',
    "css!../../../style/common.css",
    'css!./goods.css',
    '../../../config/sys_const.js',
    "../../../utils/utils.js",
    "../../../utils/ajax.js",
    "../../../utils/tips.js",
    "./viewModel.js",
    'uiReferComp',
    'uiNewReferComp',
    'refer'], function (template, cookie, bpmopenbill) {
        var ctx, listRowUrl, saveRowUrl, delRowUrl, getUrl, element;
        function init(element, cookie) {
            element = element;
            $(element).html(template);
            ctx = cookie.appCtx + "/example_goods";
            listRowUrl = ctx + "/list"; //列表查询URL
            saveRowUrl = ctx + "/save"; //新增和修改URL， 有id为修改 无id为新增
            delRowUrl = ctx + "/delete"; //刪除URL
            getUrl = ctx + "/get",
            window.csrfDefense();									//跨站请求伪造防御
            $(element).html(template);
            event.formDivShow(false);
            event.pageinit(element);
        }
        event = {
            pageinit: function (element) {
                viewModel.app = u.createApp({
                    el: element,
                    model: viewModel
                });
                var paginationDiv = $(element).find("#pagination")[0];
                event.comps = new u.pagination({
                    el: paginationDiv,
                    jumppage: true
                });
                event.comps.update({
                    pageSize: 5
                }); //默认每页显示5条数据
                event.initGridDataList();
                event.pageChange();
                event.sizeChange();
                $(".search-enter").keydown(function (e) {
                    if (e.keyCode == 13) {
                        $("#user-action-search").trigger("click");
                        u.stopEvent(e);
                    }
                });
            },
            //换页
            pageChange: function () {
                event.comps.on("pageChange", function (pageIndex) {
                    viewModel.draw = pageIndex + 1;
                    event.initGridDataList();
                });
            },
            //换每页显示数量
            sizeChange: function (size) {
                event.comps.on("sizeChange", function (arg) {
                    viewModel.pageSize = parseInt(arg);
                    viewModel.draw = 1;
                    event.initGridDataList();
                });
            },
            //grid初始化
            initGridDataList: function () {
                var jsonData = {
                    pageIndex: viewModel.draw - 1,
                    pageSize: viewModel.pageSize,
                    sortField: "ts",
                    sortDirection: "desc"
                };

                var searchinfo = viewModel.gridData.params;
                for (var key in searchinfo) {
                    if (searchinfo[key] && searchinfo[key] != null) {
                        jsonData[key] = encodeURI(removeSpace(searchinfo[key]));
                    }
                }
                $ajax(
                    listRowUrl,
                    jsonData,
                    function (res) {
                        if ((res && res.success == "success", res.detailMsg.data)) {
                            var totalCount = res.detailMsg.data.totalElements;
                            var totalPage = res.detailMsg.data.totalPages;
                            event.comps.update({
                                totalPages: totalPage,
                                pageSize: viewModel.pageSize,
                                currentPage: viewModel.draw,
                                totalCount: totalCount
                            });
                            event.clearDt(viewModel.gridData);
                            viewModel.gridData.setSimpleData(res.detailMsg.data.content, {
                                unSelect: true
                            });
                        }
                    },
                    function () {
                        u.messageDialog(errTips);
                    },
                    "get"
                );
            },

            // 新增按钮点击
            btnAddClicked: function (rowId, s, e) {
                var self = this;
                viewModel.rowId = rowId;
                if (rowId && rowId != -1) {
                    var datatableRow = viewModel.gridData.getRowByRowId(rowId);
                    //修改操作
                    var currentData = datatableRow.getSimpleData();
                    viewModel.formData.setSimpleData(currentData);
                } else {
                    //添加操作
                    viewModel.formData.removeAllRows();
                    viewModel.formData.createEmptyRow();
                }
                event.formDivShow(true);
            },

            //保存按钮点击
            saveClick: function () {
                if (!viewModel.app.compsValidate($(element).find("#form-div-body")[0])) {
                    u.messageDialog(mustTips);
                    return;
                }
                event.addNewData();
            },

            //新增数据
            addNewData: function () {
                var data = viewModel.formData.getSimpleData()[0];
                for (var key in data) {
                    if (key === "operate") {
                        delete data.operate;
                    }
                }
                var jsonData = event.genDataList(data);
                $ajax(
                    saveRowUrl,
                    JSON.stringify(jsonData),
                    function (res) {
                        if (res && res.success == "success") {
                            event.initGridDataList();
                        } else {
                            u.messageDialog({
                                msg: res.message,
                                title: "请求错误",
                                btnText: "确定"
                            });
                        }
                    },
                    function (params) {
                        u.messageDialog(errTips);
                    }
                );
            },

            //取消按钮点击
            cancelClick: function () {
                event.formDivShow(false);
            },

            //查询按钮点击
            search: function () {
                viewModel.gridData.clear();
                var queryData = {};
                $(".form-search")
                    .find("input")
                    .each(function () {
                        queryData[this.name] = removeSpace(this.value);
                    });
                viewModel.gridData.addParams(queryData);
                event.initGridDataList();
            },
            //清空查询条件
            cleanSearch: function () {
                $(".form-search")
                    .find("input")
                    .val("");
            },


            //页面绑定的，判定多行删除是否可用
            selectRow: function () {
                let num = viewModel.gridData.selectedIndices();
                if (num.length > 1) {
                    $("#user-action-del")
                        .attr("disabled", false)
                        .removeClass("disable");
                } else {
                    $("#user-action-del")
                        .attr("disabled", true)
                        .addClass("disable");
                }
            },

            //删除多行
            delRows: function (data) {
                let num = viewModel.gridData.selectedIndices();
                if (num.length > 1) {
                    // 获取所有选中的数据
                    var seldatas = viewModel.gridData.getSimpleData({
                        type: "select"
                    });
                    event.del(seldatas);
                }
            },

            //删除单行
            delRow: function (data, index) {
                u.confirmDialog({
                    msg:
                        '<div class="pull-left col-padding u-msg-content-center" >' +
                        '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                    title: "",
                    onOk: function () {
                        var seldatas = viewModel.gridData.getSimpleData({
                            type: "select"
                        });
                        console.log("del:",seldatas);
                        event.del(seldatas);
                    }
                });
            },

            //真正删除逻辑
            del: function (data) {
                var arr = [];
                for (var i = 0; i < data.length; i++) {
                    arr.push({
                        id: data[i].id
                    });
                }
                $ajax(
                    delRowUrl,
                    JSON.stringify(arr),
                    function (res) {
                        if (res && res.success == "success") {
                            event.initGridDataList();
                        } else {
                            var msg = "";
                            for (var key in res.detailMsg) {
                                msg += res.detailMsg[key] + "<br/>";
                            }
                            u.messageDialog({
                                msg: msg,
                                title: "请求错误",
                                btnText: "确定"
                            });
                        }
                    },
                    function (params) {
                        u.messageDialog(errTips);
                    }
                );
            },
            //清除 datatable的数据
            clearDt: function (dt) {
                dt.clear();
            },
            showValue: function (obj) {
                var showValue = "";
                if (obj.value === "Y") {
                    showValue = "是";
                } else {
                    showValue = "否";
                }
                obj.element.innerHTML = showValue;
            },

            //code转name
            renderName: function (obj) {
                if (obj["value"] && obj["value"].length > 0) {
                    var dataTableRowId = obj.row.value["$_#_@_id"];
                    obj.element.innerHTML = "<div>" + obj["value"] + "</div>";
                    ko.applyBindings(viewModel, obj.element);
                }
            },

            //组装list
            genDataList: function (data) {
                var datalist = [];
                datalist.push(data);
                return datalist;
            },

            //列表行悬停（注意里面的grid_dicttype_content_tbody，这是前台grid_dicttype的扩展）
            rowHoverHandel: function (obj) {
                $(".editTable").hide();
                let num = obj["rowIndex"];
                let a = $("#dataGrid_content_tbody").find("tr")[num];
                let ele = a.getElementsByClassName("editTable");
                ele[0].style.display = "block";
            },

            //鼠标离开事件
            mouseoutFunc: function () {
                $(".editTable").hide();
            },

            //绑定grid操作行
            optFun: function (obj) {
                var dataTableRowId = obj.row.value["$_#_@_id"];
                var delfun =
                    "data-bind=click:event.delRow.bind($data," + dataTableRowId + ")";
                var editfun =
                    "data-bind=click:event.btnAddClicked.bind($data," +
                    dataTableRowId +
                    ")";
                obj.element.innerHTML =
                    '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="修改"' +
                    editfun +
                    ">修改</button>" +
                    '<button class="u-button u-button-border" title="删除" ' +
                    delfun +
                    ">删除</button></div>";
                ko.applyBindings(viewModel, obj.element);
            },

            formDivShow: function (flag) {
                if (flag) {
                    $('#form-div').show();
                } else {
                    $('#form-div').hide();
                }
            }

        }

        return {
            template: template,
            init: init
        };
    });