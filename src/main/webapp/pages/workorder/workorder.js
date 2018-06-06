define(['text!./workorder.html',
		'cookieOperation',
		'/eiap-plus/pages/flow/bpmapproveref/bpmopenbill.js',
		"css!../../style/common.css",
		'css!./workorder.css',
		'../../config/sys_const.js',
		"../../utils/utils.js",
		"../../utils/ajax.js",
		"../../utils/tips.js",
		"./viewModel.js",
		'uiReferComp','uiNewReferComp','refer'], function (template, cookie, bpmopenbill) {
        var ctx, listRowUrl, saveRowUrl, delRowUrl, getUrl, element;

        function init(element, cookie) {
            element = element;
            $(element).html(template);
            ctx = cookie.appCtx + "/example_workorder";
            listRowUrl = ctx + "/list"; 					//列表查询URL
            saveRowUrl = ctx + "/batchSave"; 				//新增和修改URL， 有id为修改 无id为新增
            delRowUrl = ctx + "/delete"; 					//刪除URL
            getUrl = ctx + "/get",
            window.csrfDefense();							//跨站请求伪造防御
            $(element).html(template);
            
            //合并bpm model 和 viewModel
            viewModel = $.extend({}, viewModel, bpmopenbill.model); //扩展viewModel
            if(cookie && cookie.vtype && cookie.vtype=='bpm'){
            	viewModel.flowEvent.initAuditPage(element, cookie);
            }else{
                viewModel.event.formDivShow(false);
                viewModel.event.pageinit(element);
            }
        }
        
        //列表事件
        viewModel.gridEvent = {
        	//行双击事件
    		dbClickRow:function(gridObj){
    			viewModel.flowEvent.doView(gridObj.rowObj.value);
    		},
    		convertName:function(obj){
    			var sourcelist = eval("viewModel."+obj.gridCompColumn.options.source);
    			for(var i=0; i<sourcelist.length; i++){
    				if(sourcelist[i].value==obj.value){
    					console.log(sourcelist[i].name);
    					return sourcelist[i].name;
    				}
    			}
    		}

        }
        
        //流程事件定义
        viewModel.flowEvent = {
    		submitUrl : appCtx + '/example_workorder/submit',
        	recallUrl : appCtx + '/example_workorder/recall',
			auditUrl : appCtx + '/example_workorder/audit',
			
			entityId: "",
			
			//查看工作
			doView: function(rowData){
				viewModel.flowEvent.entityId = rowData.id;
				if(rowData){
                    $.ajax({
                        type: 'POST',
                        url: getUrl + "?id="+rowData.id,
                        contentType: 'application/json;charset=utf-8',
                        success: function(result){
                            if (result) {
                                if (result.success == 'success') {
                                	if (result.detailMsg.data) {
                                        viewModel.formStatus = _CONST.FORM_STATUS_VIEW;
                                        //表单数据
                                        var curFormData =[result.detailMsg.data];
                                        viewModel.formData.clear();
                                        viewModel.formData.setSimpleData(curFormData);

                                        // 把卡片页面变成不能编辑
                                        $('#form-div-body').each(function(index,element){
                                        	$(element).find('input[type!="radio"]').attr('disabled',true);
                                        });
                                        
                                        //显示操作卡片
                                        viewModel.event.formDivShow(true);
                                        
                                        //加入bpm按钮
                                        viewModel.initBPMFromBill(viewModel.flowEvent.entityId, viewModel);
                                	}else {
                                        var msg = "";
                                        for (var key in result.message) {
                                            msg += result.message[key] + "<br/>";
                                        }
                                        u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                    }
                                } else {
                                    u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                                }
                        	}
                        }
                    });
            	}else{
                    u.messageDialog({msg: '请选择一条记录！', title: '提示信息', btnText: '确定'});
            	}    				
			},
    		
			//提交工作流
    		submit: function(){
    			var selectArray = viewModel.gridData.selectedIndices();
    			if (selectArray.length < 1) {
    				u.messageDialog({
    	            	msg: "请选择要提交的记录!",
    	            	title: "提示",
    	            	btnText: "OK"
    				});
    				return;
    			}

    	        if (selectArray.length > 1) {
    	        	u.messageDialog({
    	            	msg: "一次只能提交一条记录，请选择要提交的记录!",
    	            	title: "提示",
    	            	btnText: "OK"
    	        	});
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
    	        $.ajax({
                    type: "get",
                    url: checkUrl,
                    contentType: "application/json;charset=utf-8",
                    data: {},
                    success: function(result){
	        	        	if (result) {
	        	            	if (result.success == "success") {
	        	            		var data = result.detailMsg.data;
	        	            		if (data == null) {
		        	                    u.messageDialog({
		        	                    	msg: "此数据在“资源分配”中未分配流程！",
		        	                    	title: "操作有误",
		        	                    	btnText: "确定"
		        	                    });
		        	            	} else {
		        	                	var processDefineCode = data.res_code;
		        	                	viewModel.flowEvent.submitBPMByProcessDefineCode(selectedData, processDefineCode);
	        	            		}
	        	                } else {
	        	                	u.messageDialog({
	        	                		msg: data.detailMsg.msg,
	        	                		title: "提示",
	        	                		btnText: "OK"
	        	                	});
	        	                }
	        	        	} else {
	        	            	u.messageDialog({
	        	                	msg: "无返回数据",
	        	                	title: "操作提示",
	        	                	btnText: "确定"
	        	            	});
	        	        	}
                    	}
                  });
    		},
            submitBPMByProcessDefineCode: function(selectedData, processDefineCode) {
            	$.ajax({
                		type: "post",
                		url:this.submitUrl + "?processDefineCode=" + processDefineCode,
                		contentType: "application/json;charset=utf-8",
                		data: JSON.stringify(selectedData),
                		success: function(result) {
                					if (result) {
                						if (result.success == "success") {
                							message("流程启动成功");
                							viewModel.event.initGridDataList();
                						} else {
                							u.messageDialog({
                								msg: result.message,
                								title: "操作提示",
                								btnText: "确定"
                							});
                						}
                					} else {
                						u.messageDialog({
                							msg: "无返回数据",
                							title: "操作提示",
                							btnText: "确定"
                						});
                					}
                		}
            	});
            },
    		
            //工作流撤回
    		recall: function(){
    			var selectedData = viewModel.gridData.getSimpleData({type: 'select'});
    			$.ajax({
                	type: "post",
                    url: viewModel.flowEvent.recallUrl,
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(selectedData),
                    success: function (result) {
                        if (result) {
    						if (result.success == "success") {
                            	message("流程收回成功");
                            	viewModel.event.initGridDataList();
    						} else {
                                u.messageDialog({msg: result.message, title: '操作提示', btnText: '确定'});
    						}
                        } else {
                            u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                        }
                    }
    			});
    		},
    		
    		//审批单据打开页面
    		initAuditPage: function(element, arg) {
                var app = u.createApp({
                    el: element,
                    model: viewModel
                });

                viewModel.initBpmFromTask(arg, viewModel);					//初始化BPM相关内容(添加审批操作头部和审批相关弹出框的代码片段)
            	
                var jsonData = { id: arg.id };
                $.ajax({
                	type: "post",
                	url: getUrl+"?id="+arg.id,
                	datatype: "text",
                	data: JSON.stringify(jsonData),
                	contentType: "application/json;charset=utf-8",
                	success: function(resultData) {
                		if (resultData) {
                			if (resultData.success == "success") {
                				if (resultData.detailMsg.data) {
                					
                                    //表单数据
                                    var curFormData =[resultData.detailMsg.data];
                                    viewModel.formData.clear();
                                    viewModel.formData.setSimpleData(curFormData);

                                    // 把卡片页面变成不能编辑
                                    $('#form-div-body').each(function(index,element){
                                    	$(element).find('input[type!="radio"]').attr('disabled',true);
                                    });
                                    
                                    //显示操作卡片
                                    viewModel.event.formDivShow(true);
                                    $("#form-div-header").hide();
                				} else {
                					u.messageDialog({
                						msg: "后台返回数据格式有误，请联系管理员",
                						title: "数据错误",
                						btnText: "确定"
                					});
                				}
                			} else {
                				u.messageDialog({
                					msg: resultData.message,
                					title: "请求错误",
                					btnText: "确定"
                				});
                			}
                			//viewModel.event.pageChange();
                			//viewModel.event.sizeChange();
                		} else {
                			u.messageDialog({
                				msg: "后台返回数据格式有误，请联系管理员",
                				title: "数据错误",
                				btnText: "确定"
                			});
                		}
                	},
                	error: function(er) {
                		u.messageDialog({ msg: er, title: "请求错误", btnText: "确定" });
                	}
                });
    		}
    	}
        
        viewModel.event = {
            pageinit: function (element) {
                viewModel.app = u.createApp({
                    el: element,
                    model: viewModel
                });
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
                $(".search-enter").keydown(function (e) {
                    if (e.keyCode == 13) {
                        $("#user-action-search").trigger("click");
                        u.stopviewModel.event(e);
                    }
                });
            },
            //换页
            pageChange: function () {
                viewModel.event.comps.on("pageChange", function (pageIndex) {
                    viewModel.draw = pageIndex + 1;
                    viewModel.event.initGridDataList();
                });
            },
            //换每页显示数量
            sizeChange: function (size) {
                viewModel.event.comps.on("sizeChange", function (arg) {
                    viewModel.pageSize = parseInt(arg);
                    viewModel.draw = 1;
                    viewModel.event.initGridDataList();
                });
            },
            //grid初始化
            initGridDataList: function () {
                var jsonData = {
                    pageIndex: viewModel.draw - 1,
                    pageSize: viewModel.pageSize,
                    sortField: "lastModified",
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
                viewModel.event.formDivShow(true);
            },

            //保存按钮点击
            saveClick: function () {
                if (!viewModel.app.compsValidate($(element).find("#form-div-body")[0])) {
                    u.messageDialog(mustTips);
                    return;
                }
                viewModel.event.addNewData();
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
                            viewModel.event.formDivShow(false);
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

            //取消按钮点击
            cancelClick: function () {
                viewModel.event.formDivShow(false);
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
                viewModel.event.initGridDataList();
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
                    viewModel.event.del(seldatas);
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
                        viewModel.event.del(seldatas);
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
            //清除 datatable的数据
            clearDt: function (dt) {
                dt.clear();
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
                let a = $("#gridData_content_tbody").find("tr")[num];
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
                    "data-bind=click:viewModel.event.delRow.bind($data," + dataTableRowId + ")";
                var editfun =
                    "data-bind=click:viewModel.event.btnAddClicked.bind($data," +
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
