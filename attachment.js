//示例说明：本示例实现附件的上传、下载、删除、查看功能。

define(['text!./attachment.html',
    './meta.js',
    'css!./attachment.css',
    '../../config/sys_const.js',
    "../sever.js",
    '/iuap-saas-filesystem-service/resources/js/ajaxfileupload.js',
    '/iuap-saas-filesystem-service/resources/js/ossupload.js',
    'interfaceFileImpl'], function (template) {

        window.ctxfilemng = '/iuap-saas-filesystem-service/';

        var init = function (element, cookie) {
            var ctx = cookie.appCtx + "/ExampleAttachment";
            var viewModel = {
                draw: 1,
                pageSize: 5,
                listRowUrl: ctx + "/list",					//列表查询URL
                saveRowUrl: ctx + "/save",					//新增和修改URL， 有id为修改 无id为新增
                delRowUrl: ctx + "/delete",					//刪除URL
                gridData: new u.DataTable(meta),
                formData: new u.DataTable(meta),
                //附件信息
                fileData: new u.DataTable(fileDataMeta),
                event: {
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
                    },

                    initGridDataList: function () {
                        var jsonData = {
                            pageIndex: viewModel.draw - 1,
                            pageSize: viewModel.pageSize,
                        };
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

                    //清除 datatable的数据
                    clearDt: function (dt) {
                        dt.removeAllRows();
                        dt.clear();
                    },

                    //新增单据
                    addClick: function () {
                        $("#divForm").css("display", "inline");
                        viewModel.formData.removeAllRows();
                        viewModel.formData.createEmptyRow();


                        viewModel.fileData.removeAllRows();

                    },
                    //编辑单据
                    editClik: function () {
                        var rows = viewModel.gridData.getSelectedRows();
                        if (rows == null || rows.length == 0) {
                            u.messageDialog({
                                msg: "请选择编辑的记录",
                                title: "提示",
                                btnText: "OK"
                            });
                            return;
                        } else if (rows.length > 1) {
                            u.messageDialog({
                                msg: "每次只能编辑一行记录",
                                title: "提示",
                                btnText: "OK"
                            });
                            return;
                        }
                        $("#divForm").css("display", "inline");

                        var currentData = rows[0].getSimpleData();
                        viewModel.formData.setSimpleData(currentData);
                        viewModel.event.fileQuery(currentData.code);

                    },

                    backClick: function () {
                        $("#divForm").css("display", "none");
                    },
                    saveClick: function () {
                        viewModel.event.addRecord();
                    },
                    cancelClick: function () {
                        $("#divForm").css("display", "none");
                    },

                    //组装list
                    genDataList: function (data) {
                        var datalist = [];
                        datalist.push(data);
                        return datalist;
                    },

                    //新增数据
                    addRecord: function () {
                        var data = viewModel.formData.getSimpleData()[0];
                        var jsonData = viewModel.event.genDataList(data);
                        $.ajax({
                            type: "post",
                            url: viewModel.saveRowUrl,
                            datatype: "json",
                            data: JSON.stringify(jsonData),
                            contentType: "application/json;charset=utf-8",
                            success: function (res) {
                                if (res) {
                                    if (res.success == "success") {
                                        viewModel.event.initGridDataList();
                                    } else {
                                        var msg = res.message;
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

                    //打开附件上传界面
                    onOpenUploadWin: function () {
                        var data = viewModel.formData.getSimpleData()[0];
                        if (data == null || data.code == null) {
                            alert("单据编码不能为空");
                        } else {
                            window.md = u.dialog({ id: 'testDialg2', content: "#dialog_uploadfile2", hasCloseMenu: true });
                            $('.sub-list1-new').css('display', 'inline-block');
                            $("#filenamediv").html("");
                            $("#uploadingIMG").attr("src", "../example/static/beforeUpload.svg");
                            $("#uploadingMsg").html("").addClass("uploading").removeClass("fail").removeClass("success");
                            $("#uploadbatch_id").remove();
                            $("#dialog_uploadfile2").find(".choosefile").append('<input class="u-input" type="file" name="addfile" id="uploadbatch_id"/>');
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
                        var fdata = viewModel.formData.getSimpleData()[0];
                        var pk = fdata.code;//"attachementTest";//可以填写附件关联的表单的ID，这里测试写死。
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
                        $("#dialog_uploadfile2").find(".choosefile").append('<input class="u-input" type="file" name="addfile" id="uploadbatch_id"/>');
                        var demoInput = document.getElementById('uploadbatch_id');
                        demoInput.removeEventListener('change', viewModel.event.onFileUpload);
                        demoInput.addEventListener('change', viewModel.event.onFileUpload);
                        window.vm.onCloseLoading();
                        if (1 == data.status) {//上传成功状态

                            viewModel.fileData.addSimpleData(data.data);
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
                        if (1 == data.status) {//上传成功状态
                            viewModel.fileData.setSimpleData(data.data);
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

                    //下载附件
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
                            viewModel.event.fileQuery(currentData.code);

                            // viewModel.event.fileQuery();
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
                }
            };


            $(element).html(template);
            viewModel.event.pageinit();
            // viewModel.event.fileQuery();

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

        }  //end init

        return {
            'template': 'body',
            init: init
        }
    });
//end define
