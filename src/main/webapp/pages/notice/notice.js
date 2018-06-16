define([
	"text!./notice.html",	//第一个一定要是html界面，否则会出现白屏不显示
	'cookieOperation',
	'/eiap-plus/pages/flow/bpmapproveref/bpmopenbill.js',
	"css!./notice.css",
	"./viewModel.js",
	"css!../../style/common.css",
	"../../config/sys_const.js",
	"../../utils/utils.js",
	"../../utils/tips.js",
	"../../utils/pjt-common.js",
	"uiReferComp",
	"refer",
	'/iuap-saas-filesystem-service/resources/js/ajaxfileupload.js',
	'/iuap-saas-filesystem-service/resources/js/ossupload.js',
	'interfaceFileImpl',
	"../../ueditor/ueditor.config.js",
	"../../ueditor/ueditor.all.js"
], function (html, cookie, bpmopenbill) {
	var listRowUrl, saveRowUrl, delRowUrl, element;
	//页面初始化
	function init(element, cookie) {
		element = element;
		$(element).html(html);
		
		listRowUrl = "/exnoticeweb/list"; //列表查询URL
		saveRowUrl = "/exnoticeweb/save"; //新增和修改URL， 有id为修改 无id为新增
		delRowUrl = "/exnoticeweb/delete"; //刪除URL
		
		viewModel = $.extend({}, viewModel, bpmopenbill.model);
		
		viewModel.event.pageInit(element, cookie);
		
		pjt.pageCmpStyleInit();
	};
	
	//页面事件对象
	viewModel.event = {
			//初始化
			pageInit: function (element, cookie) {
				//如果模块要带附件，在初始化方法里，app没有创建之间进行附件组件创建。
				//传递参数，当前页面的viewModel,当前页面首层DIV,附件管理组件依附的tab
				pjt.createAttachment(viewModel,$('#u-mdlayout'),$('#tab-pills-panel-2'));
				
				viewModel.app = u.createApp({
					el: element,
					model: viewModel
				});
				viewModel.md = document.querySelector('#u-mdlayout');
				
				//清除主表格数据
				viewModel.mainGridData.clear();
				//设置表格每页面数据量
				viewModel.mainGridData.pageSize(20);
				
				viewModel.event.queryData();
				
				viewModel.condition.clear();
				viewModel.condition.createEmptyRow();
				viewModel.condition.setRowSelect(0);
				
				//设置元素只读
				pjt.elementReadOnly(['noticeCode']);
				
				pjt.loadProcess(viewModel,cookie,function(id){
					UE.delEditor("informContent");
	    			var ue = UE.getEditor('informContent',{
		    			initialFrameWidth: '100%',
		    			initialFrameHeight: '350',
		    			scaleEnabled:true
		    		});
	    			var queryData = {};
		        	queryData["pkNotice"] = id;
		        	
		        	$('#saveBtn').hide();
		        	$('#editorBtn').hide();
		        	
	    			pjt.ajaxQueryData('/exnoticeweb/singlepk',queryData,function(result){
			        	var data = result.data;
						if(data!=null){
							viewModel.infoData.setSimpleData(data);
							
							var row = viewModel.infoData.getCurrentRow();
			    			ue.ready(function() {
			    				var content = row.getValue('noticeContent');
				    		    ue.setContent(pjt.htmlDecode(content));
				    		});
						}
			        });
				});
			},
			
			
			//查询按钮点击
            search: function () {
                viewModel.mainGridData.clear();
                var conditions = viewModel.condition.getSimpleData();
                if(conditions!=null && conditions!=""){
                	viewModel.mainGridData.addParams(conditions[0]);
                }
                viewModel.event.queryData();
                
            },
            //清空查询条件
            cleanSearch: function () {
            	viewModel.condition.clear();
				viewModel.condition.createEmptyRow();
				viewModel.condition.setRowSelect(0);
            	viewModel.mainGridData.addParams(null);
            },
            
			//查询数据
			queryData: function(){
				var queryData = {};
	        	queryData["pageIndex"] = viewModel.mainGridData.pageIndex();
		        queryData["pageSize"] = viewModel.mainGridData.pageSize();
		        var searchinfo = viewModel.mainGridData.params;
                for (var key in searchinfo) {
                    if (searchinfo[key] && searchinfo[key] != null) {
                    	queryData[key] = encodeURI(removeSpace(searchinfo[key]));
                    }
                }
		        pjt.ajaxQueryData('/exnoticeweb/list',queryData,function(result){
		        	var data = result.data;
					if(data!=null){
						viewModel.mainGridData.setSimpleData(data.content,{unSelect:true});
						viewModel.mainGridData.totalPages(data.totalPages);
						viewModel.mainGridData.totalRow(data.totalElements);
					}
		        });
			},
			
			//添加数据
	        addData: function(){
	        	viewModel.infoData.clear();
				viewModel.infoData.createEmptyRow();
				viewModel.infoData.setRowSelect(0);
				
				//为了增加用户体验，在新建时候就可以上传附件，这时就需要前置主键，并且赋值给viewModel.businessPk，才能进行附件上传。
				viewModel.businessPk = pjt.newUuid();
				
				var row = viewModel.infoData.getCurrentRow();
	            row.setValue('pkNotice', viewModel.businessPk);
	            row.status = Row.STATUS.NEW;
	            
				UE.delEditor("informContent");
    			var ue = UE.getEditor('informContent',{
	    			initialFrameWidth: '100%',
	    			initialFrameHeight: '350',
	    			scaleEnabled:true
	    		});
    			ue.ready(function() {
	    		    ue.setContent("");
	    		});
    			$('#editorBtn').hide();    			
    			$('#form-div').show();
	        },
	        //编辑数据
	        editData: function(){
	        	//得到选择数据集合
	        	var currentData = viewModel.mainGridData.getSimpleData({type: 'select'});
	        	if(currentData!=null && currentData!=""){
	        		//编辑第一条选择数据
	        		viewModel.infoData.setSimpleData(currentData[0]);
	        		var row = viewModel.infoData.getCurrentRow();
		            row.status = Row.STATUS.UPDATE;
		            
					UE.delEditor("informContent");
    				var ue = UE.getEditor('informContent',{
		    			initialFrameWidth: '100%',
		    			initialFrameHeight: '350',
		    			scaleEnabled:true
		    		});
    				
    				ue.ready(function() {
		    		    //设置编辑器的内容
    					var content = row.getValue('noticeContent');
		    		    ue.setContent(pjt.htmlDecode(content));
		    		});
    				
    				//附件管理模块会依附viewModel的业务主键，所以在装载数据之前要设置主键
    				viewModel.businessPk = row.getValue('pkNotice');
    				//模块有附件的时候，在编辑和查阅的时候需要装载附件数据
    				pjt.attaLoadData(viewModel);
    				$('#editorBtn').hide();
    				$('#form-div').show();
	        	}else{
	        		pjt.messge("请选择要编辑的数据！");
	        	}
	        	
	        },
	        //删除数据
	        delData: function(){
	        	var currentData = viewModel.mainGridData.getSimpleData({type: 'select'});
	        	if(currentData!=null && currentData!=""){
	        		pjt.ajaxDelData('/exnoticeweb/del',currentData[0],function(result){
		        		pjt.messge(result.messger);
		        		viewModel.event.queryData();
			        });
	        	}else{
	        		pjt.messge("请选择要删除的数据！");
	        	}
	        },
	        //查阅方法
	        readData: function(){
	        	//得到选择数据集合
	        	var currentData = viewModel.mainGridData.getSimpleData({type: 'select'});
	        	if(currentData!=null && currentData!=""){
	        		//打开第一条选择的数据
	        		viewModel.infoData.setSimpleData(currentData[0]);
	        		var row = viewModel.infoData.getCurrentRow();
		            row.status = Row.STATUS.NORMAL;
	        		
	        		UE.delEditor("informContent");
    				var ue = UE.getEditor('informContent',{
		    			initialFrameWidth: '100%',
		    			initialFrameHeight: '350',
		    			scaleEnabled:true,
		    			readonly:true
		    		});
    				
    				ue.ready(function() {
		    		    //设置编辑器的内容
    					var content = row.getValue('noticeContent');
		    		    ue.setContent(pjt.htmlDecode(content));
		    		});
    				
    				var row = viewModel.infoData.getCurrentRow();
                    row.setValue('status', 0);
					
					$("#addPage").find("input").each(function () {
						$("#"+this.id).attr("readOnly",'readOnly');
	                });
					
					//附件管理模块会依附viewModel的业务主键，所以在装载数据之前要设置主键
    				viewModel.businessPk = row.getValue('pkNotice');
    				//模块有附件的时候，在编辑和查阅的时候需要装载附件数据
    				pjt.attaLoadData(viewModel);
					
    				$('#saveBtn').hide();
    				$('#form-div').show();
	        	}else{
	        		pjt.messge("请选择要查阅的数据！");
	        	}
	        },
	        //持久化数据
	        saveOrUpdateRow: function(){
	        	var row = viewModel.infoData.getCurrentRow();
	        	var ue = UE.getEditor('informContent');
	        	
	        	var content= ue.getContent();
	        	
	        	row.setValue('noticeContent',pjt.htmlEncode(content));
	        	
	        	var url = '';
	        	if(row.status==Row.STATUS.NEW){
	        		url = '/exnoticeweb/save';
	        	}else if(row.status==Row.STATUS.UPDATE){
	        		url = '/exnoticeweb/update';
	        	}else {
	        		pjt.messge('查阅单据不能进行保存操作！');
	        		return;
	        	}
	        	
	        	pjt.ajaxSaveData(url,row.getSimpleData(),function(result){
	        		pjt.messge(result.messger);
	        		UE.delEditor("informContent");
		        	viewModel.infoData.clear();
		        	viewModel.md['u.MDLayout'].dBack();
		        	viewModel.event.queryData();
		        });
	        },
	        
	        //关闭数据详细页
	        closeInfo: function(){
	        	UE.delEditor("informContent");
	        	viewModel.infoData.clear();
	        	$('#form-div').hide();
	        },
	        
	        
	        //查询界面表格分页的页面改变方法
	        pageChange: function(index){
	        	viewModel.mainGridData.pageIndex(index);
	        	viewModel.event.queryData();
			},
			
			//查询界面表格分页改变显示页大小方法
			sizeChange: function(size){
				viewModel.mainGridData.pageSize(size);
				viewModel.mainGridData.pageIndex(0);
				viewModel.event.queryData();
			},
			//下拉框表格组件内容回显函数
			statusGrid: function (obj) {
                var showValue = '';
                if(obj.value === '0') {
                    showValue = '正常';
                }else if(obj.value === '1'){
                    showValue = '紧急';
                }
                obj.element.innerHTML = showValue;
            },
            //打印方法
            printData: function(){
            	$.ajax({
					type: 'GET',
					url: '/eiap-plus/appResAllocate/queryPrintTemplateAllocate?funccode=01notice&nodekey=exnotice',
					datatype: 'json',
					contentType: 'application/json;charset=utf-8',
					success: function (result) {
						if (result) {
							if (result.success == 'success') {
								var templateCode = result.detailMsg.data.res_code;
								var selectRows = viewModel.mainGridData.getSelectedRows();
								if(selectRows!=null && selectRows!=""){
									var pkNotice = selectRows[0].getSimpleData().pkNotice;
									//调用打印
									pjt.printTemplate(templateCode,'/exnoticeweb/printData',pkNotice);
								}else{
									pjt.messge('请选择一条需要打印的数据！');
								}
							} else {
								pjt.messge(result.detailMsg.msg);
							}
						} else {
							pjt.messge('打印服务无响应，请联系管理员！');
						}
					}
				});
            },
            
            //excel导出方法
            expData: function(){
                
                var row = viewModel.mainGridData.getSelectedRows();
                if (row == null || row.length == 0) {
                	pjt.messge('请选择要导出的数据！');
                    return;
                }
                
                var pks = ""
                for (var i = 0; i < row.length; i++) {
                    var pkNotice = row[i].getValue("pkNotice");
                    pks = pkNotice + "," + pks;
                }
                
                var form = pjt.createExpForm($('#u-mdlayout'),"/exnoticeweb/expdata",pks);
                form.submit();
            },
            
            //excel模板下载方法
            downloadExcelTpl: function(){
            	var form = pjt.createExpForm($('#u-mdlayout'),"/exnoticeweb/downloadexceltpl",null);
                form.submit();
            },
            //excel数据导入方法
            impData: function(){
            	pjt.excelDataImp($('#u-mdlayout'),"/exnoticeweb/impdata"); 
            },
            
            //提交流程
            submitFlow: function(){
            	var currentData = viewModel.infoData.getCurrentRow().getSimpleData();
            	pjt.submitProcess(currentData,"/example/exnoticeweb/submitflow","01notice","exnotice");
            }
	};
	return {
    	template: html,
    	init: init
	};
});