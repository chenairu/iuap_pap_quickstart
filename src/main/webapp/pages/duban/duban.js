define(['text!./duban.html',
        'cookieOperation',
        '/eiap-plus/pages/flow/bpmapproveref/bpmopenbill.js',
        './meta.js',
        'css!./duban.css',
        '../../config/sys_const.js',
        'css!../../style/style.css',
        'css!../../style/widget.css',
        'css!../../style/currency.css',
        'uiReferComp',
        'uiNewReferComp',
        'refer',
        '/iuap-saas-filesystem-service/resources/js/ajaxfileupload.js',
        '/iuap-saas-filesystem-service/resources/js/ossupload.js',
        'interfaceFileImpl'], function (template, cookie, bpmopenbill) {

	window.ctxfilemng = '/iuap-saas-filesystem-service/';

	//开始初始页面基础数据
    var init =  function (element, arg) {
    	var appCtx = arg.appCtx;
        var viewModel = {
            draw: 1,//页数(第几页)
            pageSize: 10,
            loadingTimer:{},
            searchURL: appCtx + '/ygdemo_yw_info/list',
            addURL: appCtx + "/ygdemo_yw_info/add",
            updateURL: appCtx + "/ygdemo_yw_info/update",
            delURL: appCtx + "/ygdemo_yw_info/delBatch",

            //审批流添加功能
            submiturl:appCtx + '/ygdemo_yw_info/submit',
            unsubmiturl:appCtx + '/ygdemo_yw_info/unsubmit',
            //根据单据主键获得单据
            getUrl:appCtx + '/ygdemo_yw_info/getvo',

            formStatus: _CONST.FORM_STATUS_ADD,

            ygdemo_searchFormDa:new u.DataTable(searchData),
            ygdemo_yw_infoDa: new u.DataTable(meta),
            ygdemo_yw_infoFormDa: new u.DataTable(meta),

            ygdemo_yw_info_ly_code:[{value:'1', name:'领导交办'},{value:'2', name:'会议纪要'},{value:'3', name:'其他'}],
			ygdemo_yw_info_zy_cd:[{value:'1', name:'重要'},{value:'2', name:'一般'}],
			ygdemo_yw_info_kpi_flag:[{value:'0', name:'否'},{value:'1', name:'是'}],
			ygdemo_yw_info_kpi_level:[{value:'1', name:'一级'},{value:'2', name:'二级'}],
			ygdemo_yw_info_state:[{value:'0', name:'待确认'},{value:'1', name:'执行中'},{value:'2', name:'已办结'},{value:'3', name:'终止'}],

            ygdemo_yw_subDa: new u.DataTable(meta_sub),
            ygdemo_yw_subFormDa: new u.DataTable(meta_sub),

            //附件信息
    		fileData: new u.DataTable({
    			meta: {
    				id: {type: 'string'},//主键
    				filepath: {type: 'string'},//文件名称
    				filesize: {type: 'string'},//文件大小
    				filename: {type: 'string'},//文件名称
    				uploadtime: {type: 'string'},//上传时间
    				groupname: {type: 'string'},//
    				url: {type: 'string'}//URL
    			}
            }),

            event: {
                openaddPage:function(rowdata){
                    if(rowdata){
                        var userId = rowdata.id;
                        var jsonData = {
                            pageIndex: 0,
                            pageSize: viewModel.pageSize,
                            sortField: "ts",
                            sortDirection: "asc"
                        };
                        jsonData['search_fk_id_ygdemo_yw_sub'] = userId;
                        $.ajax({
                            type: 'GET',
                            url: appCtx + '/ygdemo_yw_sub/list',
                            datatype: 'json',
                            data: jsonData,
                            contentType: 'application/json;charset=utf-8',
                            success: function (res) {
                                if (res) {
                                    if (res.success == 'success') {
                                        if (res.detailMsg.data) {
                                            if(res.detailMsg.data.content.length){
                                                $('#childNoData').hide();
                                                // $('#child_list_pagination').show();
                                            }else{
                                                $('#childNoData').show();
                                                // $('#child_list_pagination').hide();
                                            }
                                            viewModel.formStatus = _CONST.FORM_STATUS_EDIT
                                            viewModel.ygdemo_yw_subDa.removeAllRows();
                                            viewModel.ygdemo_yw_subDa.clear();
                                            viewModel.ygdemo_yw_subDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});

                                            viewModel.ygdemo_yw_subFormDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                            var totleCount = res.detailMsg.data.totalElements;
                                            var totlePage = res.detailMsg.data.totalPages;

                                            //获取选取行数据
                                            var rowdataArr =[rowdata];
                                            viewModel.event.userCardBtn();
                                            //获取选取行数据
                                            viewModel.ygdemo_yw_infoFormDa.clear();
                                            viewModel.ygdemo_yw_subFormDa.clear();
                                            viewModel.ygdemo_yw_infoFormDa.setSimpleData(rowdataArr);
                                            viewModel.ygdemo_yw_subFormDa.setSimpleData(viewModel.ygdemo_yw_subDa.getSimpleData(), {unSelect: true});

                                            viewModel.event.fileQuery();
                                            //显示操作卡片
                                            viewModel.md.dGo('addPage');

                                            $('#addPage').each(function(index,element){
                                                $(element).find('input[type!="radio"]').attr('disabled',false);
                                            });

                                            viewModel.child_card_pcomp.update({ //卡片页子表的分页信息
                                                totalPages: totlePage,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.childdraw,
                                                totalCount: totleCount
                                            });

                                            if(totleCount > viewModel.pageSize ){//根据总条数，来判断是否显示子表的分页层
                                                $('#child_card_pagination').show();
                                            }else{
                                                $('#child_card_pagination').hide();
                                            }
                                            viewModel.event.initTableHeight();
                                        }
                                    } else {
                                        $('#childNoData').show();
                                        var msg = "";
                                        for (var key in res.message) {
                                            msg += res.message[key] + "<br/>";
                                        }
                                        u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                    }
                                } else {
                                    $('#childNoData').show();
                                    u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                                }
                            }
                        });
                    }else{}

                },

                dbClickRow:function(gridObj){
                    viewModel.event.openaddPage(gridObj.rowObj.value);
                },

                //added by xuxx 单据需要支持流程，增加方法 --begin
	        	initPage:function(id){//初始化列表
					var postData={
						draw:viewModel.draw,
						start:0,
						length:viewModel.pageSize,
						order:{},
						search:{},
						searchconfirm:{},
						id:id
					};

					var jsonData = {'id': id};

					$.ajax({
						type:'post',
						url : viewModel.getUrl,
						datatype:'text',
						data:JSON.stringify(jsonData),
						contentType: 'application/json;charset=utf-8',
						success: function (res) {
							if(res){
								if( res.success =='success'){
									if(res.detailMsg.data){
										var element = document.getElementById('pagination');
										viewModel.ygdemo_yw_infoFormDa.setSimpleData(res.detailMsg.data);
									}else{
										u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
									}
								}else{
									u.messageDialog({msg:res.message,title:'请求错误',btnText:'确定'});
								}
								viewModel.event.pageChange();
								viewModel.event.sizeChange();
							}else{
								u.messageDialog({msg:'后台返回数据格式有误，请联系管理员',title:'数据错误',btnText:'确定'});
							}
						},
						error:function(er){
							u.messageDialog({msg:er,title:'请求错误',btnText:'确定'});
						}
					});
				},

				//added by xuxx 单据需要支持流程，增加方法 --end
				goBack:function(){
                   window.history.go(-1);
                   return false;
               },

                open: function () {
                if ($('#openIcon').hasClass('uf-arrow-right')) {
                    $('#openIcon').removeClass('uf-arrow-right').addClass('uf-arrow-down');
                } else {
                    $('#openIcon').removeClass('uf-arrow-down').addClass('uf-arrow-right');
                }
            },


                //修改最外层框架按钮组的显示与隐藏
                userListBtn: function () {  //显示user_list_button_2
                    $('#user_list_button_2').parent('.u-mdlayout-btn').removeClass('hide');
                    $('.form-search').removeClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').addClass('hide');

                },

                userCardBtn: function () {   //显示user_card_button
                    $('#user_list_button_2').parent('.u-mdlayout-btn').addClass('hide');
                    $('.form-search').addClass('hide');
                    $('#user_card_button').parent('.u-mdlayout-btn').removeClass('hide');
                    $("#save").show();
                    $('#papList').hide();
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

                //加载初始列表
                initUerList: function () {
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "desc"
                    };
                    var user = viewModel.ygdemo_searchFormDa.getSimpleData();
                    var searchObj = user[0];
                    for(var key in searchObj){
                        if(searchObj[key] && searchObj[key] != null ){
                            jsonData['search_' + key] = encodeURI(removeSpace(searchObj[key]));
                        }
                    }

                    $.ajax({
                        type: 'get',
                        url: viewModel.searchURL,
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        if (res.detailMsg.data.content.length) {
                                            $('#noData').hide();
                                        }else{
                                            $('#noData').show();
                                        }
                                        var totleCount = res.detailMsg.data.totalElements,
                                         totlePage = res.detailMsg.data.totalPages,
                                         pageSize = viewModel.pageSize,
                                         draw = viewModel.draw;

                                        viewModel.comps.update({
                                            totalPages: totlePage,
                                            pageSize: pageSize,
                                            currentPage: draw,
                                            totalCount: totleCount
                                        });

                                        // viewModel.event.clearDa(viewModel.ygdemo_yw_infoDa);
                                        // viewModel.event.clearDa(viewModel.ygdemo_yw_subDa);
                                        viewModel.ygdemo_yw_infoDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        window.setTimeout(function(){
                                            viewModel.event.initTableHeight();
                                        },500);
                                    }
                                } else {
                                    $('#noData').show();
                                    var msg = "";
                                    for (var key in res.detailMsg) {
                                        msg += res.detailMsg[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                $('#noData').show();
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        }
                    });
                    //end ajax
                },

                pageChange: function () {
                    viewModel.comps.on('pageChange', function (pageIndex) {
                        viewModel.draw = pageIndex + 1;
                        viewModel.event.initUerList();
                    });

                    // viewModel.child_list_pcomp.on('pageChange', function (pageIndex) {
                    // 	viewModel.childdraw = pageIndex + 1;
                    // 	viewModel.event.getUserJobList();
                    // });
                },

                //end pageChange
                sizeChange: function () {
                    viewModel.comps.on('sizeChange', function (arg) {
                        //数据库分页
                        viewModel.pageSize = parseInt(arg);
                        viewModel.draw = 1;
                        viewModel.event.initUerList();
                    });

                    // viewModel.child_list_pcomp.on('sizeChange', function (arg) {
                    // 	//数据库分页
                    // 	viewModel.pageSize = parseInt(arg);
                    // 	viewModel.childdraw = 1;
                    // 	viewModel.event.getUserJobList();
                    // });

                    viewModel.child_card_pcomp.on('sizeChange', function (arg) {
                    	viewModel.pageSize = parseInt(arg);
                    	viewModel.childdraw = 1;
                    	viewModel.event.getUserJobList();
                    });
                },

                //end sizeChange
                search: function () {
                    viewModel.draw = 1;
                    viewModel.event.initUerList();
                },

                cleanSearch: function () {
                    // $(element).find('.form-search').find('input').val('');
                    viewModel.ygdemo_searchFormDa.clear();
                    viewModel.ygdemo_searchFormDa.createEmptyRow();
                    viewModel.event.initUerList();
                },

                rowClick: function (row, e) {
                    var ri = e.target.parentNode.getAttribute('rowindex')

                    if (ri != null) {
                        viewModel.ygdemo_yw_infoDa.setRowFocus(parseInt(ri));
                        viewModel.ygdemo_yw_infoDa.setRowSelect(parseInt(ri));
                    }

                    viewModel.ygdemo_yw_infoFormDa.setSimpleData(viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'}));
                    var userId = viewModel.ygdemo_yw_infoFormDa.getValue("id");

                    if (userId == null || userId == "") {
                        viewModel.ygdemo_yw_subDa.removeAllRows();
                        viewModel.ygdemo_yw_subDa.clear();
                    } else {
                        viewModel.event.getUserJobList();
                    }
                },

                //以下用于check checkbox
                afterAdd: function (element, index, data) {
                    if (element.nodeType === 1) {
                        u.compMgr.updateComp(element);
                    }
                },

                listBack: function () {

                    //新增单据不保存则删除附件
                    var  rows = viewModel.fileData.getAllRows();
                    if(viewModel.formStatus === _CONST.FORM_STATUS_ADD &&rows.length>0){
                        var row=[];
                        for(var i=0;i<rows.length; i++) {
                            var id = rows[i].getSimpleData().id;
                            row.push(id);
                        }
                        for (var i = 0; i < row.length; i++) {
                            var pk = row[i];
                            var par = {
                                id: pk,//【必填】表的id
                                cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
                            }
                            var f = new interface_file();
                            f.filesystem_delete(par, function () {
                                
                            });
                        }
                    }
                    //只显示新增编辑删除按钮
                    viewModel.event.userListBtn();
                    viewModel.md.dBack();
                    viewModel.event.initUerList();
                    viewModel.fileData.clear();
//                    viewModel.buttonShowGroup['print'](false);
//                     $('#child_list_pagination').hide(); //隐藏子表的分页层
                    $('#papList').show();
                },

                addClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_ADD;
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    viewModel.event.clearDa(viewModel.ygdemo_yw_infoFormDa);
                    viewModel.ygdemo_yw_infoFormDa.createEmptyRow();
                    viewModel.ygdemo_yw_infoFormDa.setRowSelect(0);
                    viewModel.event.clearDa(viewModel.ygdemo_yw_subFormDa);
                    //设置业务操作逻辑
                    var row = viewModel.ygdemo_yw_infoFormDa.getCurrentRow();
                    //显示操作卡片
                    viewModel.ygdemo_yw_infoFormDa.setValue('state','0');
                    viewModel.md.dGo('addPage');
                    $('#addPage').each(function(index,element){
                        $(element).find('input[type!="radio"]').attr('disabled',false);
                        // $(element).find('input[type!="radio"]').attr('readonly',false);
                    });
                    //防止多次HTML不为空
                    $("#bpmhead").html("");
                    viewModel.event.initTableHeight();
                },

                editClick: function () {
                    viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
//
                    var selectArray = viewModel.ygdemo_yw_infoDa.selectedIndices();


                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    if (selectArray.length > 1) {
                        u.messageDialog({
                            msg: "一次只能编辑一条记录，请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    //获取选取行数据
                    viewModel.ygdemo_yw_infoDa.setRowSelect(selectArray);
                    viewModel.ygdemo_yw_infoFormDa.clear();
                    viewModel.ygdemo_yw_subFormDa.clear();
                    viewModel.ygdemo_yw_infoFormDa.setSimpleData(viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'}));
                    viewModel.ygdemo_yw_subFormDa.setSimpleData(viewModel.ygdemo_yw_subDa.getSimpleData(), {unSelect: true});

                    viewModel.event.fileQuery();
                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                    $('#addPage').each(function(index,element){
                    	$(element).find('input[type!="radio"]').attr('disabled',false);
                    });
                },

                saveClick: function () {
                    // compsValidate是验证输入格式。
                    if (! app.compsValidate($(element).find('#user-form')[0])) {
                    	u.messageDialog({
                            msg: "请检查必输项(*)!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }

                    var user = viewModel.ygdemo_yw_infoFormDa.getSimpleData();
                    var userJob = viewModel.ygdemo_yw_subFormDa.getSimpleData();
                    var jsondata =user[0];
                    jsondata.id_ygdemo_yw_sub = userJob;
                    var sendurl = viewModel.addURL;

                    if (viewModel.formStatus === _CONST.FORM_STATUS_EDIT) {
                        sendurl = viewModel.updateURL;
                    }

                    $.ajax({
                        type: "post",
                        url: sendurl,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsondata),//将对象序列化成JSON字符串
                        success: function (res) {
                            if (res) {
                                $('#papList').show();
                                if (res.success == 'success') {

                                    var  rows = viewModel.fileData.getAllRows();
                                    if(viewModel.formStatus === _CONST.FORM_STATUS_ADD &&rows.length>0){
                                        var filePath = res.detailMsg.data.id;
                                        viewModel.event.updateFiles(filePath,rows);
                                    }else{
                                        message("操作成功");

                                        viewModel.event.initUerList();
                                        viewModel.event.userListBtn();
                                        viewModel.md.dBack();
                                    }

                                } else {
                                    var msg = "";
                                    if (res.success == 'fail_global') {
                                        msg = res.message;
                                    } else {
                                        for (var key in res.detailMsg) {
                                            msg += res.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    //message("<i class=\"fa fa-check-circle margin-r-5\"></i>"+msg,"error");
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                            	message("<i class=\"fa fa-check-circle margin-r-5\"></i>没有返回数据","error");
                                //u.messageDialog({msg: '没有返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                    });
                },
                updateFiles:function (filePath,rows) {
                    var fileIDs=[];
                    for(var i=0;i<rows.length; i++) {
                    var id = rows[i].getSimpleData().id;
                        fileIDs.push(id);
                    }
                    var fileIDstr = fileIDs.join();
                    var par = {
                        filePath: filePath,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
                        groupname: "ygdemo",//【必填】分組名称,未来会提供树节点
                        fileIDs:fileIDstr,
                        cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
                    }
                    var f = new interface_file();
                    f.filesystem_updateBatch(par,function () {
                        message("操作成功");
                        viewModel.event.initUerList();
                        viewModel.event.userListBtn();
                        viewModel.md.dBack();
                    });

                },
                /**删除选中行*/
                delRow: function () {
                    var selectArray = viewModel.ygdemo_yw_infoDa.selectedIndices();

                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }

                    u.confirmDialog({
                        msg:
                        '<div class="pull-left col-padding u-msg-content-center" >' +
                        '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                        // '<div class="pull-left col-padding" >' +
                        // '<i class="fa fa-exclamation-circle margin-r-5 fa-3x orange" style="vertical-align:middle"></i>确认删除这些数据数据吗？</div>',
                        title: ' ',
                        onOk: function () {

                            viewModel.event.delConfirm();
                        }
                    });
                },

                /**确认删除*/
                delConfirm: function (data) {
                    var jsonDel = viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'});
                    $.ajax({
                        type: "post",
                        url: viewModel.delURL,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsonDel),
                        success: function (res) {
                        	if (res) {
                                if (res.success == 'success') {
                                    /*u.showMessage({
                                        msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                        position: "center"
                                    })*/
                                    viewModel.event.initUerList();
                                } else {
                                    u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }

                    });
                },

                beforeSubmit:function(){
                	$.ajax({
                        type : 'get',
                        url : '/eiap-plus/appResAllocateRelate/getEnableBpm?funcCode=' + node.funcCode,
                        dataType : 'json',
                        async : true,
                        contentType : "application/json ; charset=utf-8",
                        success : function(result) {
                            var enableBpm = result.detailMsg.data;
                            if(enableBpm=='Y'){
                            	return;
                            }else{
                            	//不启动审批流程，业务组直接设置单据上的状态
                            	viewModel.ygdemo_yw_infoDa.setValue("state",'1');
                            	//应该要提交吧?
                            	//debugger;
                            }
                        }
                    })
                },

                //审批流添加功能----提交审批
                submit: function () {
                    var selectArray = viewModel.ygdemo_yw_infoDa.selectedIndices();

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

                    var selectedData = viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'});

                    if(selectedData[0].state &&	selectedData[0].state !='0'){ //状态不为待确认
                    	message("该单据已经使用关联流程，不能启动","error");
                    	return ;
                    }

                    $.ajax({
                        type: 'GET',
                        url: '/eiap-plus/appResAllocate/queryBpmTemplateAllocate?funccode=' + getAppCode() + '&nodekey=003',
                        datatype: 'json',
                        contentType: 'application/json;charset=utf-8',
                        success: function (result) {
                        	if(result){
                        		if(result.success=='success'){
                                    var data = result.detailMsg.data;
                                    if(data==null){
                                        u.messageDialog({ msg: "此数据在“资源分配”中未分配流程！", title: '操作有误', btnText: '确定' });
                                    }
                                    else{
                                        var processDefineCode = data.res_code;
                                        viewModel.event.submitBPMByProcessDefineCode(selectedData,processDefineCode);
                                    }

                        		}else{
                        			u.messageDialog({
										msg : data.detailMsg.msg,
										title : "提示",
										btnText : "OK"
									});
                        		}

                        	}else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                	})
                },

                submitBPMByProcessDefineCode:function(selectedData,processDefineCode){
                	$.ajax({
                        type: "post",
                        url: viewModel.submiturl + "?processDefineCode=" + processDefineCode,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(selectedData),
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                	message("流程启动成功");
                                    viewModel.event.initUerList();
                                } else {
                                    u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }

                    });
                },

                //审批流添加功能----取消提交
                unsubmit: function () {
                    var selectedData = viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'});
                    $.ajax({
                        type: "post",
                        url: viewModel.unsubmiturl,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(selectedData),
                        success: function (res) {
                            if (res) {
                                if (res.detailMsg.data.success == 'success') {
                                	message("流程收回成功");
                                	viewModel.event.initUerList();
                                } else {
                                    u.messageDialog({msg: res.detailMsg.data.message, title: '操作提示', btnText: '确定'});
                                }
                            } else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }

                    });
                },

                /**
                 * 查看单据详情
                 */
                viewClick:function(viewModel,app){
                    viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                    var selectArray = viewModel.ygdemo_yw_infoDa.selectedIndices();

                    if (selectArray.length < 1) {
                        u.messageDialog({
                            msg: "请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }

                    if (selectArray.length > 1) {
                        u.messageDialog({
                            msg: "一次只能编辑一条记录，请选择要编辑的记录!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }

                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();

                    $("#save").hide();

                    $(".ygdemo_yw_sub-actions").hide();   //子任务的增加和删除按钮隐藏

                    //获取选取行数据
                    viewModel.ygdemo_yw_infoDa.setRowSelect(selectArray);
                    viewModel.ygdemo_yw_infoFormDa.clear();
                    viewModel.ygdemo_yw_subFormDa.clear();
                    viewModel.ygdemo_yw_infoFormDa.setSimpleData(viewModel.ygdemo_yw_infoDa.getSimpleData({type: 'select'}));
                    viewModel.ygdemo_yw_subFormDa.setSimpleData(viewModel.ygdemo_yw_subDa.getSimpleData(), {unSelect: true});

                    viewModel.event.fileQuery();

                    //显示操作卡片
                    viewModel.md.dGo('addPage');

                    $('#addPage').each(function(index,element){
                    	$(element).find('input[type!="radio"]').attr('disabled',true);
                    });

                    //加入bpm按钮
                    viewModel.initBPMFromBill(viewModel.ygdemo_yw_infoDa.getValue("id"),viewModel);
                },

                /*
                 * 打印
                 */
                printPage: function(){
                	//已经功能节点编码获取打印模版编码
                	$.ajax({
                        type: 'GET',
                        url: '/eiap-plus/appResAllocate/queryPrintTemplateAllocate?funccode=ygdemo_yw_info&nodekey=003',
                        datatype: 'json',
                        contentType: 'application/json;charset=utf-8',
                        success: function (result) {
                        	if(result){
                        		if(result.success=='success'){
                        			var data = result.detailMsg.data;
        							var templateCode = data.res_code;
        							//调用打印
        							viewModel.event.printPageByTemplateCode(templateCode);
                        		}else{
                        			u.messageDialog({
										msg : result.detailMsg.msg,
										title : "提示",
										btnText : "OK"
									});
                        		}

                        	}else {
                                u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                            }
                        }
                	})
                },

                printPageByTemplateCode:function(templateCode){
                	//打印逻辑
                	id = viewModel.ygdemo_yw_infoFormDa.getValue('id');
                	if(id != undefined && id.trim() != null){
//                		var tenantId = cookie.get('tenantid');//租户ID
                		var tenantId = "tenant";//固定字符串
	                	var serverUrl = appCtx + '/ygdemo_yw_info/dataForPrint';//取数据的url地址
	                	var params = {//去后台打印数据的参数
	                			'id': id
	                	};
	                	params = encodeURIComponent(JSON.stringify(params));//URL参数部分有特殊字符，必须编码(不同的tomcat对特殊字符的处理不一样)
	                	var url = '/print_service/print/preview?tenantId='
	                		+ tenantId + '&printcode=' + templateCode + '&serverUrl=' + serverUrl
	                		+ '&params=' + params + '&sendType=post';
	                	window.open(url);
                	}
                	else{
                		u.messageDialog({msg: '请选择一条数据进行打印', title: '提示', btnText: '确定'});
                	}
                },

                /**子表列表 */
                getUserJobList: function () {
                    var userId = viewModel.ygdemo_yw_infoDa.getValue("id");
                    var jsonData = {
                        pageIndex: 0,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "asc"
                    };

                    jsonData['search_fk_id_ygdemo_yw_sub'] = userId;

                    $.ajax({
                        type: 'GET',
                        url: appCtx + '/ygdemo_yw_sub/list',
                        datatype: 'json',
                        data: jsonData,
                        contentType: 'application/json;charset=utf-8',
                        success: function (res) {
                            if (res) {
                                if (res.success == 'success') {
                                    if (res.detailMsg.data) {
                                        if(res.detailMsg.data.content.length){
                                            $('#childNoData').hide();
                                            // $('#child_list_pagination').show();
                                        }else{
                                            $('#childNoData').show();
                                            // $('#child_list_pagination').hide();
                                        }
                                        viewModel.ygdemo_yw_subDa.removeAllRows();
                                        viewModel.ygdemo_yw_subDa.clear();
                                        viewModel.ygdemo_yw_subDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});

                                        viewModel.ygdemo_yw_subFormDa.setSimpleData(res.detailMsg.data.content, {unSelect: true});
                                        var totleCount = res.detailMsg.data.totalElements;
                                        var totlePage = res.detailMsg.data.totalPages;

                                        // viewModel.child_list_pcomp.update({ //列表页子表的分页信息
                                        //     totalPages: totlePage,
                                        //     pageSize: viewModel.pageSize,
                                        //     currentPage: viewModel.childdraw,
                                        //     totalCount: totleCount
                                        // });

                                        viewModel.child_card_pcomp.update({ //卡片页子表的分页信息
                                        	totalPages: totlePage,
                                        	pageSize: viewModel.pageSize,
                                        	currentPage: viewModel.childdraw,
                                        	totalCount: totleCount
                                        });

                                        if(totleCount > viewModel.pageSize ){//根据总条数，来判断是否显示子表的分页层
                                        	$('#child_card_pagination').show();
                                        	// $('#child_list_pagination').show();
                                        }else{
                                        	$('#child_card_pagination').hide();
                                        	// $('#child_list_pagination').hide();
                                        }

                                    }
                                } else {
                                    $('#childNoData').show();
                                    var msg = "";
                                    for (var key in res.message) {
                                        msg += res.message[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                $('#childNoData').show();
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        }
                    });
                },

                //
                addUserJob: function () {
                    viewModel.ygdemo_yw_subFormDa.createEmptyRow();
                },

                delUserJob: function (rowdata,rowIndex) {


                    if (!rowdata) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                    }else {
                    u.confirmDialog({
                        msg:
                        '<div class="pull-left col-padding u-msg-content-center" >' +
                        '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',

                        title: ' ',
                        onOk: function () {

                            var jsonDel =[rowdata];
                            var index = rowIndex;

                            if (jsonDel[0].sub_id == null) {
                                viewModel.ygdemo_yw_subFormDa.removeRows(index);
                                return;
                            }

                            $.ajax({
                                type: "post",
                                url: appCtx + "/ygdemo_yw_sub/del",
                                contentType: 'application/json;charset=utf-8',
                                data: JSON.stringify(jsonDel[0]),
                                success: function (res) {
                                    if (res) {
                                        if (res.success == 'success') {
                                            /* u.showMessage({
                                                 msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                                 position: "center"
                                             })*/
                                            viewModel.ygdemo_yw_subFormDa.removeRows(index);
                                        } else {
                                            u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                        }
                                    } else {
                                        u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                                    }
                                }
                            });
                        }
                    });
                    }
                },

				/**枚举类型渲染 */
				changeygdemo_yw_infoly_code: function (obj) {
                    var v = obj.value;
                    for( var i= 0 ;i< viewModel.ygdemo_yw_info_ly_code.length;i++ ){
                    	if(v == viewModel.ygdemo_yw_info_ly_code[i].value ){
                            obj.element.innerHTML =viewModel.ygdemo_yw_info_ly_code[i].name;
                        }
                    }
                    ko.applyBindings(viewModel, obj.element);
                },

                /**枚举类型渲染 */
				changeygdemo_yw_infostate: function (obj) {
                    var v = obj.value;
                    for( var i= 0 ;i< viewModel.ygdemo_yw_info_state.length;i++ ){
                    	if(v == viewModel.ygdemo_yw_info_state[i].value ){
                            obj.element.innerHTML = viewModel.ygdemo_yw_info_state[i].name ;
                    	}
                    }
                    ko.applyBindings(viewModel, obj.element);
                },

                /**枚举类型渲染 */
                changeygdemo_yw_info_zy_cd: function (obj) {
                    var v = obj.value;
                    for( var i= 0 ;i< viewModel.ygdemo_yw_info_zy_cd.length;i++ ){
                    	if(v == viewModel.ygdemo_yw_info_zy_cd[i].value ){
                            obj.element.innerHTML =  viewModel.ygdemo_yw_info_zy_cd[i].name ;
                    	}
                    }
                    ko.applyBindings(viewModel, obj.element);
                },

                /**枚举类型渲染 */
                changeygdemo_yw_info_kpi_flag: function (obj) {
                    var v = obj.value;
                    for( var i= 0 ;i< viewModel.ygdemo_yw_info_kpi_flag.length;i++ ){
                    	if(v == viewModel.ygdemo_yw_info_kpi_flag[i].value ){
                            obj.element.innerHTML =  viewModel.ygdemo_yw_info_kpi_flag[i].name ;
                    	}
                    }
                    ko.applyBindings(viewModel, obj.element);
                },

                /**枚举类型渲染 */
                changeygdemo_yw_info_kpi_level: function (obj) {
                    var v = obj.value;
                    for( var i= 0 ;i< viewModel.ygdemo_yw_info_kpi_level.length;i++ ){
                    	if(v == viewModel.ygdemo_yw_info_kpi_level[i].value ){
                            obj.element.innerHTML =  viewModel.ygdemo_yw_info_kpi_level[i].name ;
                    	}
                    }
                    ko.applyBindings(viewModel, obj.element);
                },
                operateFun:function(obj){
                    var dataTableRowId = obj.row.value["$_#_@_id"];
                    var rownum = obj.rowIndex;
                    var delfun = "data-bind=click:event.subdel.bind($data," + dataTableRowId + ","+ rownum +")";
                    obj.element.innerHTML =
                        '<div class="editTable" style="display:none;"><button class="u-button u-button-border" title="删除" ' +
                        delfun +
                        ">删除</button></div>";
                    ko.cleanNode(obj.element);
                    ko.applyBindings(viewModel, obj.element);
                },
                //任务分解子表删除
                subdel:function(rowId,rowIndex,e){
                    if (rowId && rowId != -1) {
                        var datatableRow = viewModel.ygdemo_yw_subFormDa.getRowByRowId(rowId);
                        //修改操作
                        var rowData = datatableRow.getSimpleData();
                        viewModel.event.delUserJob(rowData,rowIndex);
                    }

                },
                initTableHeight:function(){
                    var Total =$('.duban').height();
                    var topPart = $(".topPart").height();
                    var bottom = $("#pagination").height()+20;
                    var hh = $('#dubanMainGrid_header').height();
                    if(!hh||hh==null){
                        hh=33;
                    }
                    var h = Total-topPart-bottom-hh;
                    $("#dubanMainGrid_content").css("max-height",h);
                    $("#addPage").css("max-height",Total-topPart-10);
                },
                //--------------------------------excel导入导出-------------------------------------------------
                //督办任务导入模板下载
        		onTempletExcel: function(){
        		    var form = $("<form>");   //定义一个form表单
                    form.attr('style', 'display:none');   //在form表单中添加查询参数
                    form.attr('target', '');
                    form.attr('method', 'post');
                    form.attr('action', appCtx+"/ygdemo_yw_info/excelTemplateDownload");

//                    var input1 = $('<input>');
//                    input1.attr('type', 'hidden');
//                    input1.attr('name', 'ids');
//                    input1.attr('value', "");
                    $('#user-mdlayout').append(form);  //将表单放置在web中
//                    form.append(input1);   //将查询参数控件提交到表单上

                    var input2 = $('<input>');
                    input2.attr('type', 'hidden');
                    input2.attr('name', 'x-xsrf-token');
                    input2.attr('value', window.x_xsrf_token);
                    form.append(input2);

                    form.submit();
        		},

        		//督办任务信息导入
        		onUploadFile: function(){
        			window.md = u.dialog({id:'testDialg2',content:"#dialog_content",hasCloseMenu:true});
        			$('.sub-list1-new').css('display','inline-block');
                    $("#filenamediv2").html("");
                    $("#uploadingIMG2").attr("src","../example/static/beforeUpload.svg");
                    $("#uploadingMsg2").html("").addClass("uploading").removeClass("fail").removeClass("success");
                    $("#fileName").remove();
                    $("#dialog_content").find(".choosefile").append('<input class="u-input"  type="file" name="fileName" id="fileName"/>');
                    var demoInput = document.getElementById('fileName');
                    demoInput.addEventListener('change',viewModel.event.onUploadExcel);

        		},

        		onUploadExcel: function(){

                    var filevalue=document.getElementById("fileName").files;
                    if(!filevalue ||filevalue.length < 1 ){
                        var demoInput = document.getElementById('fileName');
                        demoInput.removeEventListener('change',viewModel.event.onUploadExcel);
                        demoInput.addEventListener('change',viewModel.event.onUploadExcel);
                        return;
                    }else{
                        $("#filenamediv2").html(filevalue[0].name);
                    }
        			var urlInfo = "/ygdemo_yw_info/excelDataImport";
                    viewModel.event.uploadingshow();
        			$.ajaxFileUpload({
        				url: appCtx+urlInfo,
        				secureuri:false,
        				fileElementId:'fileName',
        				dataType: 'json',
        				//data:{id},
        				success: function(data) {
                            $("#fileName").remove();
                            $("#dialog_content").find(".choosefile").append('<input class="u-input"  type="file" name="fileName" id="fileName"/>');
                            var demoInput = document.getElementById('fileName');
                            demoInput.removeEventListener('change',viewModel.event.onUploadExcel);
                            demoInput.addEventListener('change',viewModel.event.onUploadExcel);
                            window.clearInterval(viewModel.loadingTimer);
                            $('.file-lodedPart').width(200);
                            viewModel.event.uploadingHide();
                            $("#uploadingIMG2").attr("src","../example/static/success.svg");
                            $("#uploadingMsg2").html("导入成功").addClass("success").removeClass("uploading").removeClass("fail");
        					viewModel.event.initUerList();
        					// md.close();
        				},
        				error: function(XMLHttpRequest, textStatus, errorThrown) {
        					// u.messageDialog({msg:errorThrown,title:'请求错误',btnText:'确定'});
                            $("#fileName").remove();
                            $("#dialog_content").find(".choosefile").append('<input class="u-input"  type="file" name="fileName" id="fileName"/>');
                            var demoInput = document.getElementById('fileName');
                            demoInput.removeEventListener('change',viewModel.event.onUploadExcel);
                            demoInput.addEventListener('change',viewModel.event.onUploadExcel);
                            $("#uploadingIMG2").attr("src","../example/static/fail.svg");
                            $("#uploadingMsg2").html("导入失败"+data.message).addClass("fail").removeClass("uploading").removeClass("success");
                            // u.messageDialog({msg:"上传失败！"+data.message,title:"提示", btnText:"OK"});
                            //是吧逻辑处理
                            window.clearInterval(viewModel.loadingTimer);
        				}
        			});
        		},

        		//导出
        		onDownloadExcel: function(){
        			var dats = [];
        			var pks = ""
        			var row = viewModel.ygdemo_yw_infoDa.getSelectedRows();
       			if(row==null || row.length==0){
       				u.messageDialog({msg:"请选择要导出的数据",title:"提示", btnText:"确定"});
       				return;
       			}

        			if(row!=null && row.length!=0){
        				for(var i=0;i<row.length;i++){
        					var pkItem = row[i].getValue("id");
        					dats.push(row[i].getSimpleData());
        					if(pks.length==0){
        						pks = pkItem;
        					}else{
        						pks = pks+","+pkItem;
        					}
        				}
        			}

        			var form = $("<form>");   //定义一个form表单
                    form.attr('style', 'display:none');   //在form表单中添加查询参数
                    form.attr('target', '');
                    form.attr('method', 'post');
                    form.attr('action', appCtx+"/ygdemo_yw_info/excelDataExport");

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
        		},

                //--------------------------------excel导入导出-------------------------------------------------

                //--------------------------------附件下载-------------------------------------------------
                //打开附件上传界面
        		onOpenUploadWin: function(){
        			window.md = u.dialog({id:'testDialg2',content:"#dialog_uploadfile2",hasCloseMenu:true});
        			$('.sub-list1-new').css('display','inline-block');
                    // $("#uploadOkbtn").addClass("buttonDisable");
                    // $("#uploadcancelbtn").show();
                    $("#filenamediv").html("");
                    $("#uploadingIMG").attr("src","../example/static/beforeUpload.svg");
                    $("#uploadingMsg").html("").addClass("uploading").removeClass("fail").removeClass("success");
                    $("#uploadbatch_id").remove();
                    $("#dialog_uploadfile2").find(".choosefile").append('<input class="u-input" type="file" name="addfile" id="uploadbatch_id"/>');
                    var demoInput = document.getElementById('uploadbatch_id');
                    demoInput.addEventListener('change',viewModel.event.onFileUpload);
        		},
        		//关闭上传附件界面
        		onCloseFileWindow: function(e){
        			md.close();
        		},

        		//上传附件
        		onFileUpload: function(){
        		    var filevalue=document.getElementById("uploadbatch_id").files;
        		    if(!filevalue ||filevalue.length < 1 ){
                        var demoInput = document.getElementById('uploadbatch_id');
                        demoInput.removeEventListener('change',viewModel.event.onFileUpload);
                        demoInput.addEventListener('change',viewModel.event.onFileUpload);
                        return;
                    }else{
        		        $("#filenamediv").html(filevalue[0].name);

                        var  rows = viewModel.fileData.getAllRows();
                        if(viewModel.formStatus === _CONST.FORM_STATUS_ADD &&rows.length>0){
                            var row=[];
                            for(var i=0;i<rows.length; i++) {
                                var filename = rows[i].getSimpleData().filename;
                                if(filename===filevalue[0].name){
                                    $("#uploadingMsg").html("{"+filename+"}已经存在不能重复上传").addClass("fail").removeClass("uploading").removeClass("success");
                                    return;
                                }
                            }
                        }
                    }

        			//获取表单
        			//var row = viewModel.ygdemo_yw_infoFormDa.getCurrentRow();
        			var pk = viewModel.ygdemo_yw_infoFormDa.getValue('id');
        			//获取表单数据
        			//var main = row.getSimpleData();
        			//var pk = main.pk;
        			var par = {
        					 fileElementId: "uploadbatch_id",  //【必填】文件上传空间的id属性  <input type="file" id="id_file" name="file" />,可以修改，主要看你使用的 id是什么
        					 filepath: pk,   //【必填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
        					 groupname: "ygdemo",//【必填】分組名称,未来会提供树节点
        					 permission: "read", //【选填】 read是可读=公有     private=私有     当这个参数不传的时候会默认private
        					 url: true,          //【选填】是否返回附件的连接地址，并且会存储到数据库
        					 //thumbnail :  "500w",//【选填】缩略图--可调节大小，和url参数配合使用，不会存储到数据库
        					 cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
        					 }
        			 var f = new interface_file();
        			 f.filesystem_upload(par,viewModel.event.fileUploadCallback);
        			 // window.vm.onLoading();
                    viewModel.event.uploadingshow();

        		},
                uploadingshow:function(){
        		    $("#uploadingIMG").attr("src","../example/static/uploading.svg");
                    $("#uploadingIMG2").attr("src","../example/static/uploading.svg");
        		    $("#uploadingMsg").html("文件上传中").addClass("uploading").removeClass("success").removeClass("fail");
                    $("#uploadingMsg2").html("文件上传中").addClass("uploading").removeClass("success").removeClass("fail");
        		    $(".file-loding").show();
                    $('.file-lodedPart').width(0);
                    viewModel.loadingTimer = window.setInterval(function(){
                        var loadingpart = $('.file-lodedPart');
                        var width = loadingpart.width();
                        if(width>190){
                            window.clearInterval(viewModel.loadingTimer);
                        }
                        loadingpart.width(width+1);
                    },100);
                },
                uploadingHide:function(){
                    window.setTimeout(function(){
                        $(".file-loding").hide();
                        $('.file-lodedPart').width(0);
                    },1000);
                },
        		//上传文件回传信息
        		fileUploadCallback: function(data){
                    $("#uploadbatch_id").remove();
                    $("#dialog_uploadfile2").find(".choosefile").append('<input class="u-input" type="file" name="addfile" id="uploadbatch_id"/>');

                    var demoInput = document.getElementById('uploadbatch_id');
                    demoInput.removeEventListener('change',viewModel.event.onFileUpload);
                    demoInput.addEventListener('change',viewModel.event.onFileUpload);
        			window.vm.onCloseLoading();
        			 if(1 == data.status){//上传成功状态
                         viewModel.fileData.addSimpleData(data.data);
        				 // u.messageDialog({msg:"上传成功！",title:"提示", btnText:"OK"});
                         // message("上传成功");
                         // viewModel.event.onOpenUploadWin();
                         window.clearInterval(viewModel.loadingTimer);
                         $('.file-lodedPart').width(200);
                         // $("#uploadOkbtn").removeClass("buttonDisable");
                         // $("#uploadcancelbtn").hide();
                         viewModel.event.uploadingHide();
                         $("#uploadingIMG").attr("src","../example/static/success.svg");
                         $("#uploadingMsg").html("文件上传成功").addClass("success").removeClass("uploading").removeClass("fail");
                         //成功逻辑处理
        			 }else{//error 或者加載js錯誤
                         $("#uploadingIMG").attr("src","../example/static/fail.svg");
                         if(!data.message || data.message === ""){
                             $("#uploadingMsg").html("文件上传失败").addClass("fail").removeClass("uploading").removeClass("success");
                         }else{
                             $("#uploadingMsg").html(data.message).addClass("fail").removeClass("uploading").removeClass("success");
                         }
                         // u.messageDialog({msg:"上传失败！"+data.message,title:"提示", btnText:"OK"});
        				 //是吧逻辑处理
                         window.clearInterval(viewModel.loadingTimer);
        			 }
        		 },

        		 fileQuery: function(){
        			//获取表单
        			var row = viewModel.ygdemo_yw_infoFormDa.getCurrentRow();
        			if(null==row){
        				row = viewModel.ygdemo_yw_infoDa.getSelectedRows()[0];
        			}
        			//获取表单数据
        			var main = row.getSimpleData();
        			var pk = main.id;
        			 var par = {
        				     //建议一定要有条件否则会返回所有值
        					 filepath: pk, //【选填】单据相关的唯一标示，一般包含单据ID，如果有多个附件的时候由业务自己制定规则
        					 groupname: "ygdemo",//【选填】[分組名称,未来会提供树节点]
        					 cross_url : window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
        				}
        			 var f = new interface_file();
        			 f.filesystem_query(par,viewModel.event.fileQueryCallBack);
        		 },

        		 fileQueryCallBack: function(data){
        			 if(1 == data.status){//上传成功状态
                         viewModel.fileData.setSimpleData(data.data);
                         if(data.data.length){
                            $('#noDataFile').hide();
                       }else{
                            $('#noDataFile').show();
                       }

        			 }else{
                        $('#noDataFile').show();
        				 //没有查询到数据，可以不用提醒
        				 if("没有查询到相关数据" != data.message){
        					 u.messageDialog({msg:"查询失败"+data.message,title:"提示", btnText:"OK"});
        				 }else{
        					 viewModel.fileData.removeAllRows();
        				 }
        			 }
        		 },

        		 //附件删除
        		 fileDelete: function(rowId){
                     var rowdata = viewModel.fileData.getRowByRowId(rowId);
                     var row=[rowdata];


        			 if(row==null || row.length==0){
        				 u.messageDialog({msg:"请选择要删除的附件",title:"提示", btnText:"OK"});
        				 return
        			 }else if(row.length>1){
        				 u.messageDialog({msg:"每次只能删除一个附件",title:"提示", btnText:"OK"});
        				 return
        			 }
                     u.confirmDialog({
                         msg:
                         '<div class="pull-left col-padding u-msg-content-center" >' +
                         '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这个附件吗？</div>',

                         title: '',
                         onOk: function () {
                             for (var i = 0; i < row.length; i++) {
                                 var pk = row[i].getValue("id");
                                 var par = {
                                     id: pk,//【必填】表的id
                                     cross_url: window.ctxfilemng //【选填】跨iuap-saas-fileservice-base 时候必填
                                 }
                                 var f = new interface_file();
                                 f.filesystem_delete(par, viewModel.event.fileDeleteCallBack);
                             }
                         }
                     });
        		 },

        		 //附件删除回调
        		 fileDeleteCallBack: function(data){
        			 if(1 == data.status){//上传成功状态
        				 viewModel.event.fileQuery();
        			 }else{
        				 u.messageDialog({msg:"删除失败"+data.message,title:"提示", btnText:"OK"});
        			 }
        		 },

        		 //下载
        		 fileDownload: function(rowId){

                     var rowdata = viewModel.fileData.getRowByRowId(rowId);
                     var row=[rowdata];

        			 if(row==null || row.length==0){
        				 u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
        				 return
        			 }else if(row.length>1){
        				 u.messageDialog({msg:"每次只能下载一个附件",title:"提示", btnText:"OK"});
        				 return
        			 }

        			 for(var i=0;i<row.length;i++){
        				 var pk = row[i].getValue("id");
        				 var form = $("<form>");   //定义一个form表单
        				 form.attr('style', 'display:none');   //在form表单中添加查询参数
        				 form.attr('target', '');
        				 form.attr('enctype', 'multipart/form-data');
        				 form.attr('method', 'post');
        				 form.attr('action', window.ctxfilemng+"file/download?permission=read&stream=false&id="+pk);
        				 $('#user-mdlayout').append(form);  //将表单放置在web中
        				 form.submit();
        			 }
        		 },

        		 //查看
        		 fileView: function(rowId){
                     var rowdata = viewModel.fileData.getRowByRowId(rowId);
                     var row=[rowdata];
        			 if(row==null || row.length==0){
        				 u.messageDialog({msg:"请选择要下载的附件",title:"提示", btnText:"OK"});
        				 return
        			 }else if(row.length>1){
        				 u.messageDialog({msg:"每次只能查看一个附件",title:"提示", btnText:"OK"});
        				 return
        			 }
        			 for(var i=0;i<row.length;i++){
        				 var url = row[i].getValue("url");
                         if(url.indexOf("http://")>=0||url.indexOf("https://")>=0){
                             parent.open(url);
                         }else{
                             parent.open("http://"+url);
                         }

        			 }
        		 },
                filerowHoverHandel:function (obj) {
                    $(".editTable").hide();
                    var num = obj["rowIndex"];
                    var a = $("#gridUserJob2grid_content_tbody").find("tr")[num];
                    var ele = a.getElementsByClassName("editTable");
                    ele[0].style.display = "block";

                },
                subrowHoverHandel:function (obj) {
                    $(".editTable").hide();
                    var num = obj["rowIndex"];
                    var a = $("#grid_content_tbody").find("tr")[num];
                    var ele = a.getElementsByClassName("editTable");
                    ele[0].style.display = "block";
                },

                dubancodelink:function(obj){
                    var dataTableRowId = obj.row.value["$_#_@_id"];
                    var editfun = "data-bind=click:beforeEdit.bind($data," + dataTableRowId + ")";
                    obj.element.innerHTML =
                        '<div class="dubancodelink"' +
                        editfun +
                        '><span>' +
                        obj.value+
                        '</span></div>';
                    ko.applyBindings(viewModel, obj.element);
                },

        		 //--------------------------------附件下载-------------------------------------------------

                //gird 中使用新参照
                childGridEditType:function(options){
                    var grid = options.gridObj,
                        datatable = grid.dataTable,
                        viewModel = grid.viewModel,
                        field = options.field,

                        showField = options.gridObj.getColumnByField(options.field).options.showField;
                    element = options.element,
                        column = grid.getColumnByField(field);
                    //grid控件加上一个是否只读的属性判断
                    var referInputReadonly = column.options.editOptions.referInputReadonly;
                    var readOnly = 'readonly="readonly"';
                    var placeholder = column.options.editOptions.placeholder?column.options.editOptions.placeholder:"";
                    var htmlStr = '<div class="input-group date form_date">' +
                        '<input  placeholder="'+placeholder+'" class="form-control" type="text" '+readOnly+'/>' +
                        '<span class="input-group-addon refer"><span class="fa fa-angle-down"></span></span>' +
                        '</div>';

                    var refmodel = datatable.getMeta(field,'refmodel');
                    var refparam = datatable.getMeta(field,'refparam');

                    var refOptions =  column.options.editOptions;
                    refOptions['refmodel'] = refmodel;
                    refOptions['refparam'] = refparam;
                    // songyd3 优先使用已创建的实例，避免重复创建实例
                    var ncrefer = grid.gridModel.editComponent[field];
                    options.element.innerHTML = '';
                    if(ncrefer instanceof u.cloudRefComp) {
                        if(grid.gridModel.editComponentDiv[field] && grid.gridModel.editComponentDiv[field].length > 0) {
                            $(options.element).html(grid.gridModel.editComponentDiv[field]);
                            var referComp = $("#refContainer"+ncrefer.fieldId).data("uui.refer");
                            referComp.init();
                            referComp.options.data = [];
                            if(options.value && options.value != "") {
                                var pks = options.value.split(",");
                                var items = referComp.getRefValByPK(pks);
                                setTimeout(function() {
                                    referComp.setValue(items);
                                });
                            }
                            referComp.bindFirstEvent();
                        }
                        // $(options.element).html($(ncrefer.element).clone(true,true));
                    }
                    else {
                        ncrefer = new u.cloudRefComp({
                            // el:$(element).find('div')[0],
                            el:$(htmlStr)[0],
                            options: refOptions,
                            model:viewModel});
                        $(options.element).html($(ncrefer.element));
                        grid.gridModel.editComponent[field] = ncrefer;
                        grid.gridModel.editComponentDiv[field] = $(ncrefer.element);
                    }
                    ncrefer.updateMeta();
                    options.gridObj.editComp = ncrefer;

                    var rowId = options.rowObj['$_#_@_id'];
                    var row = datatable.getRowByRowId(rowId);
                    var display
                    if (showField){
                        display = row.getValue(showField);
                    }else{
                        display = row.getMeta(field, 'display')	|| '';
                    }
                    $(element).find('input').val(display);
                }
            }, // end  event
            optFileFun:function (obj) {
                var dataTableRowId = obj.row.value["$_#_@_id"];
                var delfun = "data-bind=click:event.fileDelete.bind($data," + dataTableRowId + ")";
                var editfun ="data-bind=click:event.fileView.bind($data," + dataTableRowId + ")";
                var downfun ="data-bind=click:event.fileDownload.bind($data," + dataTableRowId + ")";
                obj.element.innerHTML =

                    '<div class="editTable" style="display:none;"><button class="u-button u-button-border"title="修改"' +
                    editfun +
                    ">查看</button>" +
                    '<button class="u-button u-button-border" title="删除" ' +
                    delfun +
                    ">删除</button>"+
                    '<button class="u-button u-button-border" title="下载" '+
                    downfun +
                    ">下载</button></div>";

                ko.applyBindings(viewModel, obj.element);
            },
            //定义操作列的内容
            optFun: function(obj) {
                var dataTableRowId = obj.row.value["$_#_@_id"];
                var delfun = "data-bind=click:del.bind($data," + dataTableRowId + ")";
                var editfun =
                    "data-bind=click:beforeEdit.bind($data," + dataTableRowId + ")";
                obj.element.innerHTML =
                    '<div class="editTable"><button class="u-button u-button-border"title="修改"' +
                    editfun +
                    ">修改</button>" +
                    '<button class="u-button u-button-border" title="删除" ' +
                    delfun +
                    ">删除</button></div>";
                ko.applyBindings(viewModel, obj.element);
            },
            //删除操作
            del: function(rowId) {
                var datatableRow = viewModel.ygdemo_yw_infoDa.getRowByRowId(rowId);
                //请求后端删除对应的数据；
                // index为数据下标
                u.confirmDialog({
                    msg:
                    '<div class="pull-left col-padding u-msg-content-center" >' +
                    '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                    title: " ",
                    onOk: function() {

                        //删除
                        var currentData = datatableRow.getSimpleData();
                        var currentDataarr=[currentData];
                        $.ajax({
                            type: "post",
                            url: viewModel.delURL,
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify(currentDataarr),
                            success: function (res) {
                                if (res) {
                                    if (res.success == 'success') {
                                        /*u.showMessage({
                                            msg: "<i class=\"fa fa-check-circle margin-r-5\"></i>删除成功",
                                            position: "center"
                                        })*/
                                        viewModel.event.initUerList();
                                    } else {
                                        u.messageDialog({msg: res.message, title: '操作提示', btnText: '确定'});
                                    }
                                } else {
                                    u.messageDialog({msg: '无返回数据', title: '操作提示', btnText: '确定'});
                                }
                            }

                        });

                    }
                });
            },
            beforeEdit: function(rowId, s, e) {
                var self = this;
                viewModel.rowId = rowId;
                if (rowId && rowId != -1) {
                    var datatableRow = viewModel.ygdemo_yw_infoDa.getRowByRowId(rowId);
                    //修改操作
                    var currentData = datatableRow.getSimpleData();
                    viewModel.event.openaddPage(currentData);
                } else {
                    console.log("添加");
                    //添加操作
                    viewModel.formDataTable.removeAllRows();
                    viewModel.formDataTable.createEmptyRow();
                }

            }

        };

        //end viewModel

        //继承BPM的viewModel
        viewModel=$.extend({}, viewModel, bpmopenbill.model);//扩展viewModel

        if(arg && arg.vtype && arg.vtype=='bpm'){
        	window.initButton(viewModel, $(element)[0], "ygdemo_yw_info");//初始化按钮权限
        }else{
        	window.initButton(viewModel, element, "ygdemo_yw_info");//初始化按钮权限
        }
        viewModel.ygdemo_searchFormDa.createEmptyRow();
        window.csrfDefense();//跨站请求伪造防御 added by yany

        $(element).html(template);

        if(arg && arg.vtype && arg.vtype=='bpm'){
        	ko.cleanNode($(element)[0]);
        }else{
        	ko.cleanNode(element);
        }

        var app = u.createApp({
            el: element,
            model: viewModel
        });

        viewModel.md = $(element).find('#user-mdlayout')[0]['u.MDLayout'];
        var paginationDiv = $(element).find('#pagination')[0];
        viewModel.comps = new u.pagination({el: paginationDiv, jumppage: true});

        // viewModel.child_list_pcomp = new u.pagination({el: $(element).find('#child_list_pagination')[0], jumppage: true});
        viewModel.child_card_pcomp = new u.pagination({el: $(element).find('#child_card_pagination')[0], jumppage: true});
        viewModel.childdraw=1 ;

        if(arg && arg.vtype && arg.vtype=='bpm'){
        	viewModel.initBpmFromTask(arg,viewModel);//初始化BPM相关内容(添加审批操作头部和审批相关弹出框的代码片段)

            viewModel.event.initPage(viewModel.id);//加载表单数据
            viewModel.md.dGo('addPage');//跳转到浏览页面

            // 把卡片页面变成不能编辑
            $('#addPage').each(function(index,element){
            	$(element).find('input[type!="radio"]').attr('disabled',true);
            });

   			//隐藏查询框
   			bpmHideQueryInfoHandler('.u-mdlayout-btn');
   			bpmHideQueryInfoHandler('.form-search');
           //隐藏子表
			bpmHideQueryInfoHandler('#ygdemo_yw_sub-form');
        }else{
        	 viewModel.event.initUerList();
             viewModel.event.pageChange();
             viewModel.event.sizeChange();
        }

        /*
         * 参照滚动条
         */
        $("#user-mdlayout").scroll(function(){
        	$("#autocompdiv").css("display",'none');
        });
        $("#addPage").scroll(function(){
            $("#autocompdiv").css("display",'none');
        });


        window.vm = viewModel;

        //加载条
		window.vm.onLoading = function onLoading(){
		 	 var centerContent='<i class="fa fa-cloud u-loader-centerContent"></i>';
		 	 var opt1={
		 		 hasback:true,
		 		 hasDesc:true,//是否含有加载内容描述
		 		 centerContent:centerContent
		 	 };
		 	 u.showLoader(opt1);
		 }

		 //关闭加载条
		 window.vm.onCloseLoading = function onCloseLoading(){
		 	 u.hideLoader();
		 }

    }  //end init

    return {
        'model': init.viewModel,
        'template': template,
        init: function (param1, param2) {
            if(typeof(param1)=="string"){
                var arg = {};
                arg.appCtx = '/example';
                init(param2, arg);
            }else{
                init(param1, param2);
            }

            /*回车搜索*/
            $('.search-enter').keydown(function (e) {
                if (e.keyCode == 13) {
                    // 失去焦点
                    $('.search-enter').blur();
                    $('#user-action-search').trigger('click');
                }
            });
            var toggleBtn = document.querySelector("#condition-toggle");
            u.on(toggleBtn, "click", function() {
                var conditionRow = document.querySelector("#condition-row");
                var toggleIcon = this.querySelector("i");
                if (u.hasClass(conditionRow, "b-searech-close")) {
                    u
                        .removeClass(conditionRow, "b-searech-close")
                        .addClass(conditionRow, "b-searech-open");
                    u
                        .removeClass(toggleIcon, "uf-arrow-up")
                        .addClass(toggleIcon, "uf-arrow-down");
                    this.querySelector("span").innerText = "收起";
                } else {
                    u
                        .removeClass(conditionRow, "b-searech-open")
                        .addClass(conditionRow, "b-searech-close");
                    u
                        .removeClass(toggleIcon, "uf-arrow-down")
                        .addClass(toggleIcon, "uf-arrow-up");
                    this.querySelector("span").innerText = "展开";
                }
            });
        }
    }
});
//end define
