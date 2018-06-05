define(['text!./exampleTree.html',"require","dialogmin",
    "css!../../style/common.css",
    "css!./exampleTree.css",
    "../../config/sys_const.js",
    "../../utils/utils.js",
    "../../utils/ajax.js",
    "../../utils/tips.js",
    "./viewModel.js"], function (template,require) {
        var ctx, listRowUrl, saveRowUrl, delRowUrl, getUrl, element;
        var dialogmin = require("dialogmin")
        function init(element, cookie) {
            element = element;
            //$(element).html(template);
            ctx = cookie.appCtx + "/exampleTree";
            listRowUrl = ctx + "/list"; //列表查询URL
            saveRowUrl = ctx + "/save"; //新增和修改URL， 有id为修改 无id为新增
            delRowUrl = ctx + "/delete"; //刪除URL
            getUrl = ctx + "/get",
            window.csrfDefense();									//跨站请求伪造防御
            $(element).html(template);

            viewModel.event.pageinit(element);
        }
    var viewModel = {
        draw: 1,
        pageSize: 5,
        gridData: new u.DataTable(meta),
        formData: new u.DataTable(meta),
        treeSetting: {
            view: {
                showLine: false,
                selectedMulti: false
            },
            async:{
                enable:true,
                url:"",
                contentType: "application/json",
                autoParam:["id"]
            },
            data: {
                simpleData: {
                    enable:true,
                    idKey: "id",
                    pIdKey: "pid",
                    rootPId: "ROOT111"
                }
            },
            callback: {
                onClick: function(e, id, node) {
                    viewModel.event.getChildren(e,id,node);
                }
            }
        }
    };
        viewModel.event = {
            
            pageinit: function (element) {
                treeid = [];
                viewModel.app = u.createApp({
                    el: element,
                    model: viewModel
                });
            viewModel.event.loadTree();
            },
            getChildren :function(e,id,treeNode){
                var treeObj = $.fn.zTree.getZTreeObj(id);
                var node = treeObj.getNodeByTId(treeNode.tId);
                if(node.children == null || node.children == "undefined"){
                    $.ajax({
                        type: "get",
                        url: listRowUrl,
                        data:{
                            node:treeNode.id
                        },
                        dataType: "json",
                        success: function(res) {
                            if (res) {
                                if (res.success == "success") {
                                    if (res.detailMsg.data&&res.detailMsg.data.size>0) {
                                        newNode = treeObj.addNodes(node, res.detailMsg.data.content);
                                        // $("#tree2")[0]["u-viewModel"].tree.expandAll(true);
                                    }else{
                                        //dialogmin("没有啦!","icon-tishi");
                                        dialogmin("没有啦!","tip-suc");
                                    }
                                }
                            }
                        }
                });
                }
                },
            loadTree: function(node) {
                var data = "root"

                $.ajax({
                    type: "get",
                    url: listRowUrl,
                    data:{
                        node:data
                    },
                    dataType: "json",
                    success: function(res) {
                        if (res) {
                            if (res.success == "success") {
                                if (res.detailMsg.data) {
                                    viewModel.formData.removeAllRows();
                                    viewModel.formData.clear();
                                    viewModel.formData.setSimpleData(res.detailMsg.data.content, {
                                        unSelect: true
                                    });
                                   // $("#tree2")[0]["u-viewModel"].tree.expandAll(true);
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
                    },
                    error: function(er) {
                        u.messageDialog({
                            msg: "请求超时",
                            title: "请求错误",
                            btnText: "确定"
                        });
                    }
                });

            }
           

        }

        return {
            template: template,
            init: init
        };
    });