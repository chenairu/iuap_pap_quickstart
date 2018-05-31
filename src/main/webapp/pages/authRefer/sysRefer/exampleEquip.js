define(["text!./exampleEquip.html","cookieOperation","./meta.js",
        "../../../config/sever.js",
        "../../../config/sys_const.js",
        "css!./sysRefer.css",
        "css!../../../style/style.css",
        "css!../../../style/widget.css", "css!../../../style/currency.css",
        'uiReferComp', 'uiNewReferComp', 'refer'],

    function (html) {
        var init = function (element,cookie) {
            var ctx = cookie.appCtx + "/exampleEquip";
            var viewModel = {
                draw: 1,
                //页数(第几页)
                pageSize: 5,

                listRowUrl: ctx + "/list",					//列表查询URL
                insertRowUrl: ctx + "/insert",					//新增和修改URL， 有id为修改 无id为新增
                updateRowUrl:ctx+"/update",
                delRowUrl: ctx + "/delete",				   //刪除URL

                gridData: new u.DataTable(meta),
                formData: new u.DataTable(meta),

                event: {
                    //新增或修改的保存或取消按钮
                    saveClick: function () {
                        if (!viewModel.app.compsValidate($(element).find("#dialog_content")[0])) {
                            u.messageDialog({
                                msg: "请检查必输项(*)!",
                                title: "提示",
                                btnText: "OK"
                            });
                            return;
                        }
                        viewModel.event.addNewData();
                        viewModel.dialog.close();
                    },
                    cancelClick: function () {
                        viewModel.dialog.close();
                    },

                    //清除 datatable的数据
                    clearDt: function (dt) {
                        dt.removeAllRows();
                        dt.clear();
                    },
                    //返回
                    goBack: function () {
                        window.history.go(- 1);
                        return false;
                    },

                    initGridDataList: function () {
                        var jsonData = {
                            pageIndex: viewModel.draw - 1,
                            pageSize: viewModel.pageSize,
                            // sortField: "create_time",
                            // sortDirection: "desc"
                        };

                        var searchinfo = viewModel.gridData.params;
                        for (var key in searchinfo) {
                            if (searchinfo[key] && searchinfo[key] != null) {
                                jsonData[key] = encodeURI(removeSpace(searchinfo[key]));
                            }
                        }

                        $.ajax({
                            type: "get",
                            url: viewModel.listRowUrl,
                            datatype: "json",
                            data: jsonData,
                            contentType: "application/json;charset=utf-8",
                            success: function (res) {
                                if (res) {
                                    if (res.success == "success") {
                                        if (res.detailMsg.data) {
                                            var totalCount = res.detailMsg.data.totalElements;
                                            var totalPage = res.detailMsg.data.totalPages;
                                            viewModel.event.comps.update({
                                                totalPages: totalPage,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.draw,
                                                totalCount: totalCount
                                            });
                                            viewModel.event.clearDt(viewModel.gridData);
                                            viewModel.gridData.setSimpleData(res.detailMsg.data.content, {
                                                unSelect: true
                                            });
                                        }
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

                    //分页组件初始化
                    pageinit: function () {
                        viewModel.app = u.createApp({
                            el: element,
                            model: viewModel
                        });
                        //分页初始化
                        var paginationDiv = $(element).find("#pagination")[0];

                        this.comps = new u.pagination({
                            el: paginationDiv,
                            jumppage: true
                        });
                        this.comps.update({
                            pageSize: 5
                        }); //默认每页显示5条数据
                        this.initGridDataList();
                        viewModel.event.pageChange();
                        viewModel.event.sizeChange();
                        $(".search-enter").keydown(function (e) {
                            if (e.keyCode == 13) {
                                $("#user-action-search").trigger("click");
                                u.stopEvent(e);
                            }
                        });
                    },
                    pageChange: function () {
                        viewModel.event.comps.on("pageChange",
                            function (pageIndex) {
                                viewModel.draw = pageIndex + 1;
                                viewModel.event.initGridDataList();
                            });
                    },
                    sizeChange: function (size) {
                        viewModel.event.comps.on("sizeChange",
                            function (arg) {
                                viewModel.pageSize = parseInt(arg);
                                viewModel.draw = 1;
                                viewModel.event.initGridDataList();
                            });
                    },

                    //删除操作 TODO: 冲突的方法
                    dels: function (rowId) {
                        var datatableRow = viewModel.listDataTable.getRowByRowId(rowId);
                        //请求后端删除对应的数据；
                        // index为数据下标
                        viewModel.gridData.removeRow(datatableRow);
                    },
                    search: function () {
                        viewModel.gridData.clear();
                        var queryData = {};
                        $(".form-search").find(".input_search").each(function () {
                            queryData[this.name] = removeSpace(this.value);
                        });
                        viewModel.gridData.addParams(queryData);
                        viewModel.event.initGridDataList();
                    },
                    cleanSearch: function () {
                        $(element).find(".form-search").find("input").val("");
                    },

                    getRowData: function (rows) {
                        //rows 表示行数据对象
                        var rowsdata = [];
                        for (var i = 0; i < rows.length; i++) {
                            var d = rows[i].getSimpleData();
                            rowsdata.push(d);
                        }
                        return rowsdata;
                    },

                    del: function (data) { // flag = 0 单条删除 1多条删除
                        var arr = [];
                        for (var i = 0; i < data.length; i++) {
                            arr.push({
                                "id": data[i].id
                            });
                        }
                        $ajax(viewModel.delRowUrl, JSON.stringify(arr), viewModel.event.initGridDataList(), viewModel.event.showErr, "DELETE", "json");
                    },
                    delRows: function (data) {
                        let num = viewModel.gridData.selectedIndices();
                        if (num.length > 1) {
                            // 获取所有选中的数据
                            var seldatas = viewModel.gridData.getSimpleData({
                                "type": 'select'
                            });
                            viewModel.event.del(seldatas);
                        }
                    },
                    delRow: function (data, index) {
                        u.confirmDialog({
                            msg: '<div class="pull-left col-padding u-msg-content-center" >' + '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                            title: "",
                            onOk: function () {
                                var seldatas = viewModel.gridData.getSimpleData({
                                    "type": 'select'
                                });
                                viewModel.event.del(seldatas);
                            }
                        });
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

                    //选中列表行数据,【多条】行数删除是否可用
                    selectRow: function () {
                        let num = viewModel.gridData.selectedIndices();
                        if (num.length > 1) {
                            $("#user-action-del").attr("disabled", false).removeClass("disable");
                        } else {
                            $("#user-action-del").attr("disabled", true).addClass("disable");
                        }
                    },

                    //列表行悬停
                    rowHoverHandel: function (obj) {
                        $(".editTable").hide();
                        let num = obj["rowIndex"];
                        let a = $("#grid_dicttype_content_tbody").find("tr")[num];
                        let ele = a.getElementsByClassName("editTable");
                        ele[0].style.display = "block";
                    },

                    //鼠标离开事件
                    mouseoutFunc: function () {
                        $(".editTable").hide();
                    },

                    //code转name
                    renderName: function (obj) {
                        if (obj["value"] && obj["value"].length > 0) {
                            var dataTableRowId = obj.row.value["$_#_@_id"];
                            var infoFun = "data-bind=click:event.showInfo.bind($data," + dataTableRowId + ")";
                            obj.element.innerHTML = "<div " + infoFun + ">" + obj["value"] + "</div>";
                            ko.applyBindings(viewModel, obj.element);
                        }
                    },
                    showErr:function(res){
                        u.messageDialog({
                            msg: "操作失败",
                            title: "请求错误",
                            btnText: "确定"
                        });
                    },
                    //提示框
                    showInfo: function (obj) {
                        viewModel.md.dGo("addPage");
                    },
                    //新增数据
                    addNewData: function () {
                        viewModel.formData.setRowSelect(0);
                        var data = viewModel.formData.getSimpleData()[0];
                        for (var key in data) {
                            if (key === "operate") {
                                delete data.operate;
                            }
                        }

                        var jsonData = viewModel.event.genDataList(data);
                        if(data.id == null){
                            $ajax(viewModel.insertRowUrl, JSON.stringify(jsonData[0]), viewModel.event.initGridDataList(), viewModel.event.showErr, "POST", "json");
                        }else{
                            $ajax(viewModel.updateRowUrl, JSON.stringify(jsonData[0]), viewModel.event.initGridDataList(), viewModel.event.showErr, "PUT", "json");
                        }

                    },

                    //组装list
                    genDataList: function (data) {
                        var datalist = [];
                        datalist.push(data);
                        return datalist;
                    }

                },

                //列表行内操作-按钮定义
                optFun: function (obj) {
                    var dataTableRowId = obj.row.value["$_#_@_id"];
                    var delfun = "data-bind=click:event.delRow.bind($data," + dataTableRowId + ")";
                    var editfun = "data-bind=click:beforeEdit.bind($data," + dataTableRowId + ")";
                    obj.element.innerHTML = '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="修改"' + editfun + ">修改</button>" + '<button class="u-button u-button-border" title="删除" ' + delfun + ">删除</button></div>";
                    ko.applyBindings(viewModel, obj.element);
                },

                //编辑按钮绑定方法
                beforeEdit: function (rowId, s, e) {
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
                        var r = viewModel.formData.createEmptyRow();

                        // var combo1Obj = document.getElementById("province")['u.Combo'];
                        //
                        // combo1Obj.selectItem(0);
                        // var data = initUserId("city");
                        // console.log(data);
                        // viewModel.formData.setSimpleData({city:data})
                    }

                    //显示模态框，如果模态框不存在创建模态框，存在则直接显示
                    if (!viewModel.dialog) {
                        viewModel.dialog = u.dialog({
                            id: "testDialg",
                            content: "#dialog_content",
                            hasCloseMenu: true,
                            width: "80%;",
                            height: "500px"
                        });
                        $(".u-msg-dialog").css("width", "80%");
                    } else {
                        viewModel.dialog.show();
                    }
                    u.stopEvent(e);
                },

            };
            //参照联动
            viewModel.formData.on("orgId.valueChange",function (e) {
                var jsonData = {};
                jsonData["pk_org"] = viewModel.formData.getCurrentRow().getSimpleData().orgId;
                console.log(jsonData);
                $("#equip_dept").attr("data-refparam",JSON.stringify(jsonData));
            });
            //加载Html页面
            $(element).html(html);
            viewModel.event.pageinit();


            //搜索导航（查询、筛选）展开/收起
            var toggleBtn = document.querySelector("#condition-toggle");
            u.on(toggleBtn, "click",
                function () {
                    var conditionRow = document.querySelector("#condition-row");
                    var toggleIcon = this.querySelector("i");
                    if (u.hasClass(conditionRow, "b-searech-close")) {
                        u.removeClass(conditionRow, "b-searech-close").addClass(conditionRow, "b-searech-open");
                        u.removeClass(toggleIcon, "uf-arrow-up").addClass(toggleIcon, "uf-arrow-down");
                        this.querySelector("span").innerText = "展开";
                    } else {
                        u.removeClass(conditionRow, "b-searech-open").addClass(conditionRow, "b-searech-close");
                        u.removeClass(toggleIcon, "uf-arrow-down").addClass(toggleIcon, "uf-arrow-up");
                        this.querySelector("span").innerText = "收起";
                    }
                });


        };

        return {
            template: html,
            init: init
        };
    });