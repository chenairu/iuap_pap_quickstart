define(["../../config/sys_const.js",
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",], function () {
        var metaTelbook = {
            meta: {
                id: {
                    type: "string"
                },
                peoname: {
                    type: "string",
                    required: true,
                    notipFlag: true,
                    hasSuccess: true,
                    nullMsg: "姓名不能为空!"
                },
                peocode: {
                    type: "string",
                    required: true,
                    notipFlag: true,
                    hasSuccess: true,
                    nullMsg: "员工编号不能为空!"
                },
                institid: {
                    type: "string"
                },
                institname: {
                    type: "string"
                },
                worktel: {
                    type: "string",
                    regExp: /^1[34578]\d{9}$/,
                    notipFlag: true,
                    hasSuccess: true,
                    errorMsg: "手机号码格式不对。"
                },
                email: {
                    type: "string",
                    regExp: /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/,
                    notipFlag: true,
                    hasSuccess: true,
                    errorMsg: "邮箱格式不对。"
                },
                tel: {
                    type: "string"
                },
                office: {
                    type: "string"
                },
                operate: {
                    type: "string"
                }
            }
        };
        var tableListUrl = appCtx + "/telBook/list";
        var tableDelUrl = appCtx + "/telBook/del/";
        var tableSaveUrl = appCtx + "/telBook/save";
        var viewModel = {
            app: {},
            draw: 1,
            totalPage: 0,
            pageSize: 5,
            totalCount: 0,
            curInstitId: '',//当前选中组织机构Id
            curInstitName: '',//当前选中组织机构Name
            telbookdata: new u.DataTable(metaTelbook),
            telbookdatanew: new u.DataTable(metaTelbook),
            event: {
                //页面初始化
                pageInit: function () {
                    viewModel.app = u.createApp({
                        el: '#book',
                        model: viewModel
                    });
                    //分页初始化
                    var paginationDiv = $("#pagination")[0];
                    this.comps = new u.pagination({
                        el: paginationDiv,
                        jumppage: true
                    });
                    this.comps.update({
                        pageSize: 5
                    }); //默认每页显示5条数据
                    viewModel.event.pageChange();
                    viewModel.event.sizeChange();
                    //回车搜索
                    $(".input_enter").keydown(function (e) {
                        if (e.keyCode == 13) {
                            viewModel.event.searchClick();
                            u.stopEvent(e);
                        }
                    });
                    //监听树节点点击事件传递的自定义事件
                    $("#myLayout").bind("treeNodeClicked", function (event, par1, par2, par3) {
                        viewModel.curInstitId = par1;
                        viewModel.curInstitName = par2;
                        viewModel.event.loadTelbook(treeid);
                    });
                },

                // 加载人员信息
                loadTelbook: function (instit) {
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize,
                        sortField: "peocode",
                        sortDirection: "asc"
                    };
                    $("#search").each(
                        function () {
                            if (this.value == undefined
                                || this.value == ""
                                || this.value.length == 0) {
                                //不执行操作
                            } else {
                                jsonData["search_searchParam"] = this.value
                                    .replace(/(^\s*)|(\s*$)/g, ""); //去掉空格
                            }
                        });
                    if (instit) {
                        if (instit != "" || instit.length != 0) {
                            jsonData["institid"] = instit.join();
                        }
                    }
                    $ajax(
                        tableListUrl,
                        jsonData,
                        function (res) {
                            if (res) {
                                if (res.success == "success") {
                                    if (!res.detailMsg.data) {
                                        viewModel.totalCount = 0;
                                        viewModel.totalPage = 1;
                                        viewModel.event.comps
                                            .update({
                                                totalPages: viewModel.totalPage,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.draw,
                                                totalCount: viewModel.totalCount
                                            });
                                        viewModel.telbookdata.removeAllRows();
                                    } else {
                                        viewModel.totalCount = res.detailMsg.data.totalElements;
                                        viewModel.totalPage = res.detailMsg.data.totalPages;
                                        viewModel.event.comps
                                            .update({
                                                totalPages: viewModel.totalPage,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.draw,
                                                totalCount: viewModel.totalCount
                                            });
                                        viewModel.telbookdata
                                            .removeAllRows();
                                        viewModel.telbookdata
                                            .setSimpleData(
                                                res.detailMsg.data.content,
                                                {
                                                    unSelect: true
                                                });
                                    }
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
                                iuap.message("返回数据格式有误");
                            }
                        }, function () {
                            iuap.message("请求超时");
                        },
                        "get"
                    )
                },

                //分页相关
                pageChange: function () {
                    viewModel.event.comps.on("pageChange", function (
                        pageIndex) {
                        viewModel.draw = pageIndex + 1;
                        viewModel.event.loadTelbook(treeid);
                    });
                },
                // 改变每页数量
                sizeChange: function () {
                    viewModel.event.comps.on("sizeChange",
                        function (arg) {
                            viewModel.pageSize = parseInt(arg);
                            viewModel.draw = 1;
                            viewModel.event.loadTelbook(treeid);
                        });
                },

                // 新增人员信息按钮被点击
                addManClick: function () {
                    $("#dialog_content_man").find(".u-msg-title").html("<h4>新增人员</h4>");
                    viewModel.event.clearDt(viewModel.telbookdatanew);
                    if (viewModel.curInstitId != "") {
                        var newr = viewModel.telbookdatanew.createEmptyRow();
                        viewModel.telbookdatanew.setRowSelect(newr);
                        var newrow = viewModel.telbookdatanew.getSelectedRows()[0];
                        newrow.setValue("institid", viewModel.curInstitId);
                        newrow.setValue("institname", viewModel.curInstitName);
                        $("#add_peocode").removeAttr("readonly");
                        window.md = u.dialog({
                            id: "add_man",
                            content: "#dialog_content_man",
                            hasCloseMenu: true
                        });
                        $('.u-msg-dialog').css('width', '800px');
                    } else {
                        iuap.message("请选择部门");
                    }
                },

                // 修改人员信息按钮被点击
                editManClick: function (rowId) {
                    if (!rowId || rowId === -1) {
                        return;
                    }
                    $("#dialog_content_man").find(".u-msg-title").html(
                        "<h4>编辑人员</h4>");
                    viewModel.event.clearDt(viewModel.telbookdatanew);
                    var row = viewModel.telbookdata.getRowByRowId(rowId);
                    if (row) {
                        viewModel.telbookdatanew.setSimpleData(row.getSimpleData());
                        $("#add_peocode").attr("readonly", "readonly");
                        window.md = u.dialog({
                            id: "edit_man",
                            content: "#dialog_content_man",
                            hasCloseMenu: true
                        });
                        $('.u-msg-dialog').css('width', '800px');
                    } else {
                        iuap.message("请选择要编辑的数据!");
                    }
                },

                // 删除人员信息按钮被点击
                delManClick: function (rowId) {
                    if (!rowId || rowId === -1) {
                        return;
                    }
                    var row = viewModel.telbookdata.getRowByRowId(rowId);
                    if (row) {
                        var data = row.getSimpleData();
                        u.confirmDialog({
                            msg: "是否删除" + data.peoname + "?",
                            title: "删除确认",
                            onOk: function () {
                                viewModel.event.delMan(data);
                                viewModel.telbookdata.removeRow(viewModel.telbookdata.getSelectedIndexs());
                            },
                            onCancel: function () {
                            }
                        });
                    } else {
                        iuap.message("请选择要删除的数据!");
                    }
                },

                // 新增或修改人员信息保存按钮被点击
                saveManClick: function () {
                    var data = viewModel.telbookdatanew.getSelectedRows()[0].getSimpleData();
                    if (!viewModel.app.compsValidate($("#add-form")[0])) {
                        return;
                    }
                    viewModel.event.saveMan(data);
                },

                // 新增或修改人员信息取消按钮被点击
                cancelManClick: function () {
                    md.close();
                },

                // 查询人员信息
                searchClick: function () {
                    viewModel.draw = 1;
                    viewModel.event.loadTelbook(treeid);
                },
                //更新和保存人员       
                saveMan: function (data) {
                    var list = viewModel.event.genDataList(data);
                    $.ajax({
                        type: "post",
                        url: tableSaveUrl,
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(list),
                        success: function (res) {
                            if (res) {
                                if (res.success == "success") {
                                    iuap.message("保存成功");
                                    viewModel.event.loadTelbook(treeid);
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
                                u.messageDialog({
                                    msg: "没有返回数据",
                                    title: "操作提示",
                                    btnText: "确定"
                                });
                                iuap.message("没有返回数据");
                            }
                        }
                    });
                },
                //删除人员
                delMan: function (data) {
                    var list = viewModel.event.genDataList(data);
                    $.ajax({
                        type: "post",
                        url: tableDelUrl,
                        dataType: "json",
                        contentType: "application/json",
                        data: JSON.stringify(list),
                        success: function (res) {
                            if (res.success == "success") {
                                u.messageDialog({
                                    msg: "删除成功！",
                                    btnText: "确定"
                                });
                                //md.close();
                            } else {
                                u.messageDialog({
                                    msg: "删除失败！",
                                    btnText: "确定"
                                });
                            }
                        }
                    });
                },

                //清除datatable数据
                clearDt: function (dt) {
                    dt.removeAllRows();
                },


                //组装list
                genDataList: function (data) {
                    var datalist = [];
                    datalist.push(data);
                    return datalist;
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
            }
        };
        return { viewModel }
    })