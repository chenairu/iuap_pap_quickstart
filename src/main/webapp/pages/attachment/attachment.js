define([
    "text!./attachment.html",
    "css!../../style/common.css",
    "css!./attachment.css",
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
    window.ctxfilemng = '/iuap-saas-filesystem-service/';

    function init(element, cookie) {
        element = element;
        $(element).html(html);
        ctx = cookie.appCtx + "/ExampleAttachment";
        listRowUrl = ctx + "/list"; //列表查询URL
        saveRowUrl = ctx + "/save"; //新增和修改URL， 有id为修改 无id为新增
        delRowUrl = ctx + "/delete"; //刪除URL
        viewModel.event.pageinit(element);

    }
    viewModel.event = {
        //初始化
        pageinit: function (element) {
            viewModel.app = u.createApp({
                el: element,
                model: viewModel
            });
            //分页初始化
            var paginationDiv = $(element).find("#pagination")[0];
            viewModel.event.comps = new u.pagination({
                el: paginationDiv,
                jumppage: true
            });
            viewModel.event.comps.update({
                pageSize: 5
            }); //默认每页显示5条数据
            viewModel.event.initGridDataList();
            viewModel.event.pageChange();
            viewModel.event.sizeChange();
        },
        // 分页
        pageChange: function () {
            viewModel.event.comps.on("pageChange", function (pageIndex) {
                viewModel.draw = pageIndex + 1;
                viewModel.event.initGridDataList();
            });
        },
        // 当前页显示数量变更
        sizeChange: function (size) {
            viewModel.event.comps.on("sizeChange", function (arg) {
                viewModel.pageSize = parseInt(arg);
                viewModel.draw = 1;
                viewModel.event.initGridDataList();
            });
        },

        // 初始化gird数据
        initGridDataList: function () {
            var jsonData = {
                pageIndex: viewModel.draw - 1,
                pageSize: viewModel.pageSize,
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
        },

        //save按钮
        saveClick: function () {
            if (!viewModel.app.compsValidate($(element).find("#dialogContent")[0])) {
                u.messageDialog(mustTips);
                return;
            }
            viewModel.event.addNewData();
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
            var jsonData = viewModel.event.genDataList(data);
            $ajax(
                saveRowUrl,
                JSON.stringify(jsonData),
                function (res) {
                    if (res && res.success == "success") {
                        viewModel.event.initGridDataList();
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
            $(".u-container-fluid")//注意这里需要用整个Search区域的来找
                .find("input")
                .each(function () {
                    queryData[this.name] = removeSpace(this.value);
                });
            viewModel.gridData.addParams(queryData);
            viewModel.event.initGridDataList();
        },
        // 清除搜索
        cleanSearch: function () {
            $(".u-container-fluid")
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
                        viewModel.event.initGridDataList();
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
                viewModel.event.del(seldatas);
            }
        },
        // 删除单行
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
            var delfun = "data-bind=click:viewModel.event.delRow.bind($data," + dataTableRowId + ")";
            var editfun = "data-bind=click:viewModel.event.btnAddClicked.bind($data," + dataTableRowId + ")";
            var openAttFun = "data-bind=click:viewModel.event.openAttachementList.bind($data," + dataTableRowId + ")";
            obj.element.innerHTML =
                '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="修改"' +
                editfun +
                ">修改</button>" +
                '<button class="u-button u-button-border" title="删除" ' +
                delfun +
                ">删除</button>" +
                '<button class="u-button u-button-border" title="附件" ' +
                openAttFun +
                ">附件</button></div>"

            ko.applyBindings(viewModel, obj.element);
        },


        openAttachementList: function (rowId, s, e) {
            viewModel.rowId = rowId;
            if (rowId && rowId != -1) {
                var datatableRow = viewModel.gridData.getRowByRowId(rowId);
                var currentData = datatableRow.getSimpleData();
                //显示模态框，如果模态框不存在创建模态框，存在则直接显示
                if (!viewModel.dialog1) {
                    viewModel.dialog1 = u.dialog({
                        id: 'attachmentListDialg',
                        content: "#dialogContent1",
                        hasCloseMenu: true,
                        width: "520px;"
                    });
                } else {
                    viewModel.dialog1.show();
                }
                viewModel.event.fileQuery(currentData.id);
            }
        },

        //打开附件上传界面
        onOpenUploadWin: function () {
            var currentData = viewModel.gridData.getSimpleData({
                type: "current"
            });
            if (currentData==null) {
                alert("还未选中行");
            } else {
                window.md = u.dialog({ id: 'testDialg3', content: "#dialog_uploadfile", hasCloseMenu: true });
                // $('.sub-list1-new').css('display', 'inline-block');
                $("#filenamediv").html("");
                $("#uploadingIMG").attr("src", "../example/static/beforeUpload.svg");
                $("#uploadingMsg").html("").addClass("uploading").removeClass("fail").removeClass("success");
                $("#uploadbatch_id").remove();
                $("#dialog_uploadfile").find(".choosefile").append('<input class="u-input" style="display:none;" type="file" name="addfile" id="uploadbatch_id"/>');
                var demoInput = document.getElementById('uploadbatch_id');
                demoInput.addEventListener('change', viewModel.event.onFileUpload);
            }
        },
        //关闭上传附件界面
        onCloseFileWindow: function (e) {
            md.close();
        },

        //上传附件
        onFileUpload: function () {
            var filevalue = document.getElementById("uploadbatch_id").files;
            if (!filevalue || filevalue.length < 1) {
                var demoInput = document.getElementById('uploadbatch_id');
                demoInput.removeEventListener('change', viewModel.event.onFileUpload);
                demoInput.addEventListener('change', viewModel.event.onFileUpload);
                return;
            } else {
                $("#filenamediv").html(filevalue[0].name);
                var rows = viewModel.fileData.getAllRows();
                if (viewModel.formStatus === _CONST.FORM_STATUS_ADD && rows.length > 0) {
                    var row = [];
                    for (var i = 0; i < rows.length; i++) {
                        var filename = rows[i].getSimpleData().filename;
                        if (filename === filevalue[0].name) {
                            $("#uploadingMsg").html("{" + filename + "}已经存在不能重复上传").addClass("fail").removeClass("uploading").removeClass("success");
                            return;
                        }
                    }
                }
            }


            var rows = viewModel.gridData.getSelectedRows();
            var fdata = rows[0].getSimpleData();
            var pk = fdata.id;//"attachementTest";//可以填写附件关联的表单的ID，这里测试写死。
            var par = {
                fileElementId: "uploadbatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么
                filepath: pk,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
                groupname: "demo",//【必填】分組名称,未来会提供树节点
                permission: "read", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
                url: true,          //【选填】是否返回附件的连接地址，并且会存储到数据库
                //thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
                cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
            }
            var f = new interface_file();
            f.filesystem_upload(par, viewModel.event.fileUploadCallback);
            viewModel.event.uploadingshow();
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

        //上传文件回传信息
        fileUploadCallback: function (data) {
            $("#uploadbatch_id").remove();
            $("#dialog_uploadfile").find(".choosefile").append('<input class="u-input" style="display:none;" type="file" name="addfile" id="uploadbatch_id"/>');
            var demoInput = document.getElementById('uploadbatch_id');
            demoInput.removeEventListener('change', viewModel.event.onFileUpload);
            demoInput.addEventListener('change', viewModel.event.onFileUpload);
            window.vm.onCloseLoading();
            if (1 == data.status) {//上传成功状态
                viewModel.fileData.addSimpleData(data.data);
                var count = viewModel.fileData.getSimpleData().length;
                $('#attachmentCount').html("共" + count + "个附件");
                window.clearInterval(viewModel.loadingTimer);
                $('.file-lodedPart').width(200);
                viewModel.event.uploadingHide();
                $("#uploadingIMG").attr("src", "../example/static/success.svg");
                $("#uploadingMsg").html("文件上传成功").addClass("success").removeClass("uploading").removeClass("fail");
                //成功逻辑处理
            } else {//error 或者加載js錯誤
                $("#uploadingIMG").attr("src", "../example/static/fail.svg");
                if (!data.message || data.message === "") {
                    $("#uploadingMsg").html("文件上传失败").addClass("fail").removeClass("uploading").removeClass("success");
                } else {
                    $("#uploadingMsg").html(data.message).addClass("fail").removeClass("uploading").removeClass("success");
                }
                window.clearInterval(viewModel.loadingTimer);
            }
        },

        fileQuery: function (pk) {
            var par = {
                //建议一定要有条件否则会返回所有值
                filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
                groupname: "demo",//【选填】[分組名称,未来会提供树节点]
                cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
            }
            var f = new interface_file();
            f.filesystem_query(par, viewModel.event.fileQueryCallBack);
        },

        fileQueryCallBack: function (data) {
            if (1 == data.status) {
                viewModel.fileData.setSimpleData(data.data);
                var count = viewModel.fileData.getSimpleData().length;
                $('#attachmentCount').html("共" + count + "个附件");
                if (data.data.length) {
                    $('#noDataFile').hide();
                } else {
                    $('#noDataFile').show();
                }
            } else {
                $('#noDataFile').show();
                //没有查询到数据，可以不用提醒
                if ("没有查询到相关数据" != data.message) {
                    u.messageDialog({ msg: "查询失败" + data.message, title: "提示", btnText: "OK" });
                } else {
                    viewModel.fileData.removeAllRows();
                }
            }
        },

        //下载
        fileDownload: function (rowId, rowIndex, e) {
            var row = viewModel.fileData.getSelectedRows();
            if (row == null || row.length == 0) {
                u.messageDialog({
                    msg: "请选择要下载的附件",
                    title: "提示",
                    btnText: "OK"
                });
                return;
            } else if (row.length > 1) {
                u.messageDialog({
                    msg: "每次只能下载一个附件",
                    title: "提示",
                    btnText: "OK"
                });
                return;
            }
            var pk = row[0].getValue("id");
            var form = $("<form>");   //定义一个form表单
            form.attr('style', 'display:none');   //在form表单中添加查询参数
            form.attr('target', '');
            form.attr('enctype', 'multipart/form-data');
            form.attr('method', 'post');
            form.attr('action', window.ctxfilemng + "file/download?permission=read&stream=false&id=" + pk);
            $('#user-mdlayout').append(form);  //将表单放置在web中
            form.submit();

        },

        //附件删除
        fileDelete: function (rowId) {
            var rows = viewModel.fileData.getSelectedRows();

            if (rows == null || rows.length == 0) {
                u.messageDialog({ msg: "请选择要删除的附件", title: "提示", btnText: "OK" });
                return
            } else if (rows.length > 1) {
                u.messageDialog({ msg: "每次只能删除一个附件", title: "提示", btnText: "OK" });
                return
            }
            u.confirmDialog({
                msg:
                    '<div class="pull-left col-padding u-msg-content-center" >' +
                    '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这个附件吗？</div>',
                title: '',
                onOk: function () {
                    var pk = rows[0].getValue("id");
                    var par = {
                        id: pk,//【必填】表的id
                        cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
                    }
                    var f = new interface_file();
                    f.filesystem_delete(par, viewModel.event.fileDeleteCallBack);
                }
            });
        },

        //附件删除回调
        fileDeleteCallBack: function (data) {
            if (1 == data.status) {//上传成功状态
                var rows = viewModel.gridData.getSelectedRows();
                var currentData = rows[0].getSimpleData();
                viewModel.event.fileQuery(currentData.id);
            } else {
                u.messageDialog({ msg: "删除失败" + data.message, title: "提示", btnText: "OK" });
            }
        },

        //查看
        fileView: function (rowId) {
            var rows = viewModel.fileData.getSelectedRows();
            if (rows == null || rows.length == 0) {
                u.messageDialog({ msg: "请选择要查看的附件", title: "提示", btnText: "OK" });
                return
            } else if (rows.length > 1) {
                u.messageDialog({ msg: "每次只能查看一个附件", title: "提示", btnText: "OK" });
                return
            }
            var url = rows[0].getValue("url");
            console.log("url:", url);
            if (url.indexOf("http://") >= 0 || url.indexOf("https://") >= 0) {
                parent.open(url);
            } else {
                parent.open("http://" + url);
            }
        }
    };


    window.vm = viewModel;
    //加载条
    window.vm.onLoading = function onLoading() {
        var centerContent = '<i class="fa fa-cloud u-loader-centerContent"></i>';
        var opt1 = {
            hasback: true,
            hasDesc: true,//是否含有加载内容描述
            centerContent: centerContent
        };
        u.showLoader(opt1);
    }
    //关闭加载条
    window.vm.onCloseLoading = function onCloseLoading() {
        u.hideLoader();
    }

    return {
        template: html,
        init: init
    };
});
