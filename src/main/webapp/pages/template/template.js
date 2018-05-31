define([
    "text!./template.html",
    "css!../../style/common.css",
    "css!./template.css",
    "../../config/sys_const.js",
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",
    "./viewModel.js",
    '/iuap-saas-filesystem-service/resources/js/ajaxfileupload.js',
    '/iuap-saas-filesystem-service/resources/js/ossupload.js',
    'interfaceFileImpl'
], function (html) {
    var ctx, listRowUrl, saveRowUrl, delRowUrl, element;
    function init(element, cookie) {
        element = element;
        $(element).html(html);
        ctx = cookie.appCtx + "/exampleTemplate";
        listRowUrl = ctx + "/list"; //列表查询URL
        saveRowUrl = ctx + "/save"; //新增和修改URL， 有id为修改 无id为新增
        delRowUrl = ctx + "/del"; //刪除URL
        event.pageinit(element);
    }
    event = {
        //初始化
        pageinit: function (element) {
            viewModel.app = u.createApp({
                el: element,
                model: viewModel
            });
            //分页初始化
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
        },
        // 分页
        pageChange: function () {
            event.comps.on("pageChange", function (pageIndex) {
                viewModel.draw = pageIndex + 1;
                event.initGridDataList();
            });
        },
        // 当前页显示数量变更
        sizeChange: function (size) {
            event.comps.on("sizeChange", function (arg) {
                viewModel.pageSize = parseInt(arg);
                viewModel.draw = 1;
                event.initGridDataList();
            });
        },

        // 初始化gird数据
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

        // 新增按钮
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
            //显示模态框，如果模态框不存在创建模态框，存在则直接显示
            if (!viewModel.dialog) {
                viewModel.dialog = u.dialog({
                    id: "formDialg",
                    content: "#dialogContent",
                    hasCloseMenu: true,
                    width: "500px",
                    height: "320px"
                });
            } else {
                viewModel.dialog.show();
            }
            u.stopEvent(e);
        },

        //save按钮
        saveClick: function () {
            if (!viewModel.app.compsValidate($(element).find("#dialogContent")[0])) {
                u.messageDialog(mustTips);
                return;
            }
            event.addNewData();
            viewModel.dialog.close();
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

        // 取消按钮
        cancelClick: function () {
            viewModel.dialog.close();
        },

        //清除 datatable的数据
        clearDt: function (dt) {
            dt.clear();
        },


        // 搜索
        search: function () {
            viewModel.gridData.clear();
            var queryData = {};
            $(".form-search")
                .find("input")
                .each(function () {
                    queryData[this.name] = removeSpace(this.value);
                });
            console.log("queryData:",queryData);
            viewModel.gridData.addParams(queryData);
            event.initGridDataList();
        },
        // 清除搜索
        cleanSearch: function () {
            $(".form-search")
                .find("input")
                .val("");
        },

        // 真正删除数据
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
        // 批量删除
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
        // 删除单行
        delRow: function (data, index) {
            console.log("data:", data);
            console.log("index:", index);

            u.confirmDialog({
                msg:
                    '<div class="pull-left col-padding u-msg-content-center" >' +
                    '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i>确认删除这些数据吗？</div>',
                title: "",
                onOk: function () {
                    var seldatas = viewModel.gridData.getSimpleData({
                        type: "select"
                    });
                    console.log("delete:", seldatas);
                    event.del(seldatas);
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
                $("#user-action-del")
                    .attr("disabled", false)
                    .removeClass("disable");
            } else {
                $("#user-action-del")
                    .attr("disabled", true)
                    .addClass("disable");
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
        //grid绑定事件
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

        //模板下载
        onTempletExcel: function () {
            var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctx + "/excelTemplateDownload");
            $('#user-mdlayout').append(form);  //将表单放置在web中
            var input2 = $('<input>');
            input2.attr('type', 'hidden');
            input2.attr('name', 'x-xsrf-token');
            input2.attr('value', window.x_xsrf_token);
            form.append(input2);
            form.submit();
        },
        uploadingshow: function () {
            $("#uploadingIMG").attr("src", "../example/static/uploading.svg");
            $("#uploadingIMG2").attr("src", "../example/static/uploading.svg");
            $("#uploadingMsg").html("文件上传中").addClass("uploading").removeClass("success").removeClass("fail");
            $("#uploadingMsg2").html("文件上传中").addClass("uploading").removeClass("success").removeClass("fail");
            $(".file-loding").show();
            $('.file-lodedPart').width(0);
            viewModel.loadingTimer = window.setInterval(function () {
                var loadingpart = $('.file-lodedPart');
                var width = loadingpart.width();
                if (width > 190) {
                    window.clearInterval(viewModel.loadingTimer);
                }
                loadingpart.width(width + 1);
            }, 100);
        },
        uploadingHide: function () {
            window.setTimeout(function () {
                $(".file-loding").hide();
                $('.file-lodedPart').width(0);
            }, 1000);
        },





        //任务信息导入
        onUploadFile: function () {
            window.md = u.dialog({ id: 'testDialg2', content: "#dialog_content", hasCloseMenu: true });
            $('.sub-list1-new').css('display', 'inline-block');
            $("#filenamediv2").html("");
            $("#uploadingIMG2").attr("src", "../example/static/beforeUpload.svg");
            $("#uploadingMsg2").html("").addClass("uploading").removeClass("fail").removeClass("success");
            $("#fileName").remove();
            $("#dialog_content").find(".choosefile").append('<input class="u-input" style="display: none"  type="file" name="fileName" id="fileName"/>');
            var demoInput = document.getElementById('fileName');
            demoInput.addEventListener('change', event.onUploadExcel);
        },

        onUploadExcel: function () {
            var filevalue = document.getElementById("fileName").files;

            console.log("filevalue:", filevalue);
            if (!filevalue || filevalue.length < 1) {
                var demoInput = document.getElementById('fileName');
                demoInput.removeEventListener('change', event.onUploadExcel);
                demoInput.addEventListener('change', event.onUploadExcel);
                return;
            } else {
                $("#filenamediv2").html(filevalue[0].name);
            }


            event.uploadingshow();
            $.ajaxFileUpload({
                url: ctx + "/excelDataImport",
                secureuri: false,
                fileElementId: 'fileName',
                dataType: 'JSON',//返回值
                //data:{id},
                success: function (data) {
                    $("#fileName").remove();
                    $("#dialog_content").find(".choosefile").append('<input class="u-input" style="display: none"  type="file" name="fileName" id="fileName"/>');
                    var demoInput = document.getElementById('fileName');
                    demoInput.removeEventListener('change', event.onUploadExcel);
                    demoInput.addEventListener('change', event.onUploadExcel);
                    window.clearInterval(viewModel.loadingTimer);
                    $('.file-lodedPart').width(200);
                    event.uploadingHide();
                    $("#uploadingIMG2").attr("src", "../example/static/success.svg");
                    $("#uploadingMsg2").html("导入成功").addClass("success").removeClass("uploading").removeClass("fail");
                    event.initGridDataList();
                    md.close();
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    $("#fileName").remove();
                    $("#dialog_content").find(".choosefile").append('<input class="u-input" style="display: none"  type="file" name="fileName" id="fileName"/>');
                    var demoInput = document.getElementById('fileName');
                    demoInput.removeEventListener('change', event.onUploadExcel);
                    demoInput.addEventListener('change', event.onUploadExcel);
                    $("#uploadingIMG2").attr("src", "../example/static/fail.svg");
                    $("#uploadingMsg2").html("导入失败" + data.message).addClass("fail").removeClass("uploading").removeClass("success");
                    window.clearInterval(viewModel.loadingTimer);
                }
            });
        },
        //导出
        onDownloadExcel: function () {
            var dats = [];
            var pks = ""
            var row = viewModel.gridData.getSelectedRows();
            if (row == null || row.length == 0) {
                u.messageDialog({ msg: "请选择要导出的数据", title: "提示", btnText: "确定" });
                return;
            }

            if (row != null && row.length != 0) {
                for (var i = 0; i < row.length; i++) {
                    var pkItem = row[i].getValue("id");
                    dats.push(row[i].getSimpleData());
                    if (pks.length == 0) {
                        pks = pkItem;
                    } else {
                        pks = pks + "," + pkItem;
                    }
                }
            }

            var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', ctx + "/excelDataExport");
            $('#user-mdlayout').append(form);  //将表单放置在web中
            var input1 = $('<input>');
            input1.attr('type', 'hidden');
            input1.attr('name', 'ids');
            input1.attr('value', pks);
            form.append(input1);   //将查询参数控件提交到表单上
            var input2 = $('<input>');
            input2.attr('type', 'hidden');
            input2.attr('name', 'x-xsrf-token');
            input2.attr('value', window.x_xsrf_token);
            form.append(input2);
            form.submit();
        }


    };
    return {
        template: html,
        init: init
    };
});
