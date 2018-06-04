define(['text!./order.html',
    "css!../../style/common.css",
    './meta.js',
    'css!./order.css',
    'cookieOperation',
    '../../config/sys_const.js',
    'css!../../style/style.css',
    'css!../../style/widget.css',
    'css!../../style/currency.css',
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",
    'uiReferComp',
    'uiNewReferComp',
    'refer'], function (template, cookie) {

        //开始初始页面基础数据
        var init = function (element, arg) {
            var appCtx = arg.appCtx;
            var viewModel = {
                draw: 1,												//页数(第几页)
                pageSize: 10,
                listUrl: appCtx + '/demo_order/list',
                saveUrl: appCtx + "/demo_order/save",
                deleteUrl: appCtx + "/demo_order/delete",
                getUrl: appCtx + "/demo_order/get",
                subGridListUrl: appCtx + "/demo_order_detail/list",
                subGridDeleteUrl: appCtx + "/demo_order_detail/delete",
                //根据单据主键获得单据
                formStatus: _CONST.FORM_STATUS_ADD,
                comboData:[{name:'新订单',value:'01'},{name:'已完成',value:'02'},{name:'已取消',value:'03'}],
                ygdemo_searchFormDa: new u.DataTable(searchData),
                gridData: new u.DataTable(viewModel),
                formData: new u.DataTable(viewModel),
                subGridData: new u.DataTable(meta_sub),
                event: {
                    //列表初始化
                    initDataGrid: function () {
                        var jsonData = {
                            pageIndex: viewModel.draw - 1,
                            pageSize: viewModel.pageSize,
                            sortField: "ts",
                            sortDirection: "desc"
                        };
                        var searchObj = viewModel.gridData.params;
                        for (var key in searchObj) {
                            if (searchObj[key] && searchObj[key] != null) {
                                jsonData['search_' + key] = encodeURI(removeSpace(searchObj[key]));
                            }
                        }

                        $.ajax({
                            type: 'get',
                            url: viewModel.listUrl,
                            datatype: 'json',
                            data: jsonData,
                            contentType: 'application/json;charset=utf-8',
                            success: function (result) {
                                if (result) {
                                    if (result.success == 'success') {
                                        if (result.detailMsg.data) {
                                            if (result.detailMsg.data.content.length) {
                                                $('#noData').hide();
                                            } else {
                                                $('#noData').show();
                                            }

                                            var totalCount = result.detailMsg.data.totalElements;
                                            var totalPage = result.detailMsg.data.totalPages;
                                            //viewModel.event.comps.update({
                                            viewModel.comps.update({
                                                totalPages: totalPage,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.draw,
                                                totalCount: totalCount
                                            });
                                            viewModel.event.clearDa(viewModel.gridData);
                                            viewModel.gridData.setSimpleData(result.detailMsg.data.content, {
                                                unSelect: true
                                            });
                                            window.setTimeout(function () {
                                                viewModel.event.initTableHeight();
                                            }, 500);
                                        }
                                    } else {
                                        $('#noData').show();
                                        var msg = "";
                                        for (var key in result.detailMsg) {
                                            msg += result.detailMsg[key] + "<br/>";
                                        }
                                        u.messageDialog({ msg: msg, title: '请求错误', btnText: '确定' });
                                    }
                                } else {
                                    $('#noData').show();
                                    u.messageDialog({ msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定' });
                                }
                            }
                        });
                    },

                    //新增记录
                    addClick: function () {
                        divShow('form-div');
                        viewModel.formData.createEmptyRow();
                    },

                    //保存
                    saveClick: function () {
                        if (!app.compsValidate($(element).find("#form-div-body")[0])) {
                            u.messageDialog(mustTips);
                            return;
                        }
                        viewModel.event.addNewData();
                    },
                    //新增数据
                    addNewData: function () {
                        var data = viewModel.formData.getSimpleData();
                        var subData = viewModel.subGridData.getSimpleData();
                        var jsonData = data[0];
                        jsonData.orderDetails = subData;
                        $ajax(
                            viewModel.saveUrl,
                            JSON.stringify(jsonData),
                            function (res) {
                                if (res && res.success == "success") {
                                    viewModel.event.initDataGrid();
                                    divHide("form-div");
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


                    //查询条件区--操作事件、方法
                    //搜索
                    search: function () {
                        viewModel.draw = 1;
                        viewModel.gridData.clear();
                        var queryParams = {};
                        $(".form-search").find(".input_search").each(function () {
                            queryParams[this.name] = removeSpace(this.value);
                        });
                        viewModel.gridData.addParams(queryParams);
                        viewModel.event.initDataGrid();
                    },

                    //清空条件
                    cleanSearch: function () {
                        $(element).find(".form-search").find("input").val("");
                        viewModel.event.initDataGrid();
                    },


                    //主表行——双击事件
                    dbClickRow: function (gridObj) {
                        viewModel.event.doEdit(gridObj.rowObj.value);
                    },

                    //刪除操作
                    deleteClick: function () {
                        let num = viewModel.gridData.selectedIndices();
                        if (num.length > 0) {
                            var selectDatas = viewModel.gridData.getSimpleData({
                                "type": 'select'
                            });
                            viewModel.event.deleteFunc(selectDatas);
                        }
                    },

                    //刪除方法——调用后台服务
                    deleteFunc: function (datas) {
                        var ids = [];
                        for (var i = 0; i < datas.length; i++) {
                            ids.push(datas[i].id);
                        }
                        $.ajax({
                            type: "post",
                            url: viewModel.deleteUrl,
                            datatype: "json",
                            data: JSON.stringify(ids),
                            contentType: "application/json;charset=utf-8",
                            success: function (result) {
                                if (result) {
                                    if (result.success == "success") {
                                        viewModel.event.initDataGrid();
                                    } else {
                                        var msg = "";
                                        for (var key in result.detailMsg) {
                                            msg += result.detailMsg[key] + "<br/>";
                                        }
                                        u.messageDialog({
                                            msg: msg,
                                            title: "请求错误",
                                            btnText: "确定"
                                        });
                                    }
                                } else {
                                    u.messageDialog({
                                        msg: "后台返回数据格式有误，请联系管理员",
                                        title: "数据错误",
                                        btnText: "确定"
                                    });
                                }
                            }
                        });
                    },

                    //编辑信息
                    doEdit: function (rowData) {
                        if (rowData) {
                            var id = rowData.id;
                            var jsonData = {
                                pageIndex: 0,
                                pageSize: viewModel.pageSize,
                                sortField: "ts",
                                sortDirection: "asc"
                            };
                            jsonData['search_orderId'] = id;
                            $.ajax({
                                type: 'GET',
                                url: viewModel.getUrl,
                                datatype: 'json',
                                data: jsonData,
                                contentType: 'application/json;charset=utf-8',
                                success: function (result) {
                                    if (result) {
                                        if (result.success == 'success') {
                                            if (result.detailMsg.data) {
                                                viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                                                //表单数据
                                        
                                                var curFormData = [result.detailMsg.data.data];

                                                console.log("curFormData:",curFormData);
                                                // viewModel.event.userCardBtn();
                                                viewModel.formData.clear();
                                       
                                                viewModel.formData.setSimpleData(curFormData);

                                                //子表数据
                                                if (result.detailMsg.data.subPage.content.length) {
                                                    $('#childNoData').hide();
                                                } else {
                                                    $('#childNoData').show();
                                                };
                                        
                                                viewModel.subGridData.removeAllRows();
                                                viewModel.subGridData.clear();
                                           
                                                var subPage = result.detailMsg.data.subPage;
                                                viewModel.subGridData.setSimpleData(subPage.content, { unSelect: true });

                                                //显示操作卡片

                                                divShow("form-div");
                                                document.getElementById("formTitle").innerHTML="编辑订单";


                                                viewModel.child_card_pcomp.update({ 				//卡片页子表的分页信息
                                                    totalPages: subPage.totalPages,
                                                    pageSize: viewModel.pageSize,
                                                    currentPage: viewModel.childdraw,
                                                    totalCount: subPage.totalElements
                                                });

                                                if (subPage.totalElements > viewModel.pageSize) {	//根据总条数，来判断是否显示子表的分页层
                                                    $('#child_card_pagination').show();
                                                } else {
                                                    $('#child_card_pagination').hide();
                                                }
                                                viewModel.event.initTableHeight();
                                            } else {
                                                $('#childNoData').show();
                                                var msg = "";
                                                for (var key in res.message) {
                                                    msg += res.message[key] + "<br/>";
                                                }
                                                u.messageDialog({ msg: msg, title: '请求错误', btnText: '确定' });
                                            }
                                        } else {
                                            $('#childNoData').show();
                                            u.messageDialog({ msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定' });
                                        }
                                    }
                                }
                            });
                        } else {
                            u.messageDialog({ msg: '请选择一条记录！', title: '提示信息', btnText: '确定' });
                        }
                    },

                    /*** 新增/修改页面操作事件、方法 ***/
                    //子表操作
                    //子表——新增行
                    addRow4SubGrid: function () {
                        viewModel.subGridData.createEmptyRow();
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

                    //任务分解子表删除
                    subDeleteClick: function (rowId, rowIndex, e) {
                        if (rowId && rowId != -1) {
                            var datatableRow = viewModel.subGridData.getRowByRowId(rowId);
                            //修改操作
                            var rowData = datatableRow.getSimpleData();
                            viewModel.event.doDelete4SubGrid(rowData, rowIndex);
                        }
                    },

                    //子表删除方法
                    doDelete4SubGrid: function (data, rowIndex) {
                        if (!data) {
                            u.messageDialog({
                                msg: "请选择要删除的行!",
                                title: "提示",
                                btnText: "OK"
                            });
                        } else {
                            u.confirmDialog({
                                msg: '<div class="pull-left col-padding u-msg-content-center" >' +
                                    '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                                title: ' ',
                                onOk: function () {
                                    var ids = [];
                                    if (data.id == null || data.id == "") {
                                        return;
                                    } else {
                                        ids = [data.id];
                                    }

                                    $.ajax({
                                        type: "post",
                                        url: viewModel.subGridDeleteUrl,
                                        contentType: 'application/json;charset=utf-8',
                                        data: JSON.stringify(ids),
                                        success: function (result) {
                                            if (result) {
                                                if (result.success == 'success') {
                                                    viewModel.subGridData.removeRows(rowIndex);
                                                } else {
                                                    u.messageDialog({ msg: result.message, title: '操作提示', btnText: '确定' });
                                                }
                                            } else {
                                                u.messageDialog({ msg: '无返回数据', title: '操作提示', btnText: '确定' });
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    },

                    //子表行——悬浮事件
                    rowHover4SubGrid: function (obj) {
                        $(".editTable").hide();
                        var num = obj["rowIndex"];
                        var a = $("#subDataGrid_content_tbody").find("tr")[num];
                        var ele = a.getElementsByClassName("editTable");
                        ele[0].style.display = "block";
                    },

                    /**********************************************************************/
                    goBack: function () {
                        divHide("form-div");
                    },

                    open: function () {
                        if ($('#openIcon').hasClass('uf-arrow-right')) {
                            $('#openIcon').removeClass('uf-arrow-right').addClass('uf-arrow-down');
                        } else {
                            $('#openIcon').removeClass('uf-arrow-down').addClass('uf-arrow-right');
                        }
                    },



                    //判断对象的值是否为空
                    isEmpty: function (obj) {
                        if (obj.value == undefined || obj.value == '' || obj.value.length == 0) {
                            return true;
                        } else {
                            return false;
                        }
                    },

                    //清除 datatable的数据
                    clearDa: function (da) {
                        da.removeAllRows();
                        da.clear();
                    },

                    pageChange: function () {
                        viewModel.comps.on('pageChange', function (pageIndex) {
                            viewModel.draw = pageIndex + 1;
                            viewModel.event.initDataGrid();
                        });
                    },
                    //end pageChange

                    sizeChange: function () {
                        viewModel.comps.on('sizeChange', function (arg) {
                            //数据库分页
                            viewModel.pageSize = parseInt(arg);
                            viewModel.draw = 1;
                            viewModel.event.initDataGrid();
                        });

                        viewModel.child_card_pcomp.on('sizeChange', function (arg) {
                            viewModel.pageSize = parseInt(arg);
                            viewModel.childdraw = 1;
                            viewModel.event.getUserJobList();
                        });
                    },
                    //end sizeChange

                    /**子表列表 */
                    initTableHeight: function () {
                        var Total = $('.duban').height();
                        var topPart = $(".topPart").height();
                        var bottom = $("#pagination").height() + 20;
                        var hh = $('#dubanMainGrid_header').height();
                        if (!hh || hh == null) {
                            hh = 33;
                        }
                        var h = Total - topPart - bottom - hh;
                        $("#dubanMainGrid_content").css("max-height", h);
                        $("#addPage").css("max-height", Total - topPart - 10);
                    },
                }, // end  event


                /*************** 列表操作 ******************/
                //定义列表——操作列内容
                optFunc: function (obj) {
                    var rowId = obj.row.value["$_#_@_id"];
                    var editFunc = "data-bind=click:doEditRow.bind($data," + rowId + ")";
                    var delFunc = "data-bind=click:doDeleteRow.bind($data," + rowId + ")";
                    obj.element.innerHTML = '<div class="editTable">' +
                        '<button class="u-button u-button-border"title="修改" ' + editFunc + ">修改</button>" +
                        '<button class="u-button u-button-border" title="删除" ' + delFunc + ">删除</button></div>";
                    ko.applyBindings(viewModel, obj.element);
                },

                //行操作——修改
                doEditRow: function (rowId, s, e) {
                    var self = this;
                    viewModel.rowId = rowId;
                    if (rowId && rowId != -1) {				//修改操作
                        var datatableRow = viewModel.gridData.getRowByRowId(rowId);
                        var currentData = datatableRow.getSimpleData();
                        viewModel.event.doEdit(currentData);
                    } else {								//添加操作
                        viewModel.formDataTable.removeAllRows();
                        viewModel.formDataTable.createEmptyRow();
                    }
                },

                //行操作——删除
                doDeleteRow: function (rowId) {
                    var dataGridRow = viewModel.gridData.getRowByRowId(rowId);
                    u.confirmDialog({
                        msg: '<div class="pull-left col-padding u-msg-content-center" >' +
                            '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                        title: "删除确认提示",
                        onOk: function () {
                            var currentData = dataGridRow.getSimpleData();
                            viewModel.event.deleteFunc([currentData]);
                        }
                    });
                }
            };
            //end viewModel


            $(element).html(template);
            ko.cleanNode(element);
            window.csrfDefense();									//跨站请求伪造防御
            viewModel.ygdemo_searchFormDa.createEmptyRow();

            var app = u.createApp({
                el: element,
                model: viewModel
            });

            var paginationDiv = $(element).find('#pagination')[0];
            viewModel.comps = new u.pagination({ el: paginationDiv, jumppage: true });

            viewModel.child_card_pcomp = new u.pagination({ el: $(element).find('#child_card_pagination')[0], jumppage: true });
            viewModel.childdraw = 1;

            viewModel.event.initDataGrid();
            viewModel.event.pageChange();
            viewModel.event.sizeChange();



            /*
             * 参照滚动条
             */
            $("#user-mdlayout").scroll(function () {
                $("#autocompdiv").css("display", 'none');
            });
            $("#addPage").scroll(function () {
                $("#autocompdiv").css("display", 'none');
            });



        }
        //end init




        return {
            'model': init.viewModel,
            'template': template,
            init: function (param1, param2) {
                if (typeof (param1) == "string") {
                    var arg = {};
                    arg.appCtx = '/example';
                    init(param2, arg);
                } else {
                    init(param1, param2);
                }
            }
        }
    });