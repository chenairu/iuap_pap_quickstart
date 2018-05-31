define(['text!./order.html','./meta.js', 'css!./order.css',
        'cookieOperation',
        '/eiap-plus/pages/flow/bpmapproveref/bpmopenbill.js',
        '../../config/sys_const.js', 'css!../../style/style.css',
        'css!../../style/widget.css', 'css!../../style/currency.css',
        'uiReferComp', 'uiNewReferComp', 'refer'], function (template, cookie, bpmopenbill) {

	//开始初始页面基础数据
    var init =  function (element, arg) {
    	var appCtx = arg.appCtx;
        var viewModel = {
            draw: 1,												//页数(第几页)
            pageSize: 10,
            loadingTimer:{},
            
            listUrl: appCtx + '/demo_order/list',
            saveUrl: appCtx + "/demo_order/save",
            deleteUrl: appCtx + "/demo_order/delete",
            getUrl: appCtx + "/demo_order/get",
            
            
            subGridListUrl: appCtx + "/demo_order_detail/list",
            subGridDeleteUrl: appCtx + "/demo_order_detail/delete",
            
            //根据单据主键获得单据
            formStatus: _CONST.FORM_STATUS_ADD,

            ygdemo_searchFormDa:new u.DataTable(searchData),
            gridData: new u.DataTable(viewModel),
            formData: new u.DataTable(viewModel),
            subGridData: new u.DataTable(meta_sub),
            
            //orderState_refers : [{value:'0', name:'待确认'},{value:'1', name:'执行中'},{value:'2', name:'已办结'},{value:'3', name:'终止'}],
            
            event: {
                /*** 头部区操作事件、方法 ***/
            	//返回|取消
                listBack: function () {
                    viewModel.event.userListBtn();
                    viewModel.md.dBack();
                    viewModel.event.initDataGrid();
                    $('#papList').show();
                },
                
                //保存
                saveClick: function () {
                    if (! app.compsValidate($(element).find('#user-form')[0])) {
                    	u.messageDialog({
                            msg: "请检查必输项(*)!",
                            title: "提示",
                            btnText: "OK"
                        });
                        return;
                    }

                    var data = viewModel.formData.getSimpleData();
                    var subData = viewModel.subGridData.getSimpleData();
                    var jsondata =data[0];
                    jsondata.orderDetails = subData;

                    $.ajax({
                        type: "post",
                        url: viewModel.saveUrl,
                        contentType: 'application/json;charset=utf-8',
                        data: JSON.stringify(jsondata),							//将对象序列化成JSON字符串
                        success: function (result) {
                            if (result) {
                                $('#papList').show();
                                if (result.success == 'success') {
                                	message("操作成功");
                                    viewModel.event.initDataGrid();
                                    viewModel.event.userListBtn();
                                    viewModel.md.dBack();
                                } else {
                                    var msg = "";
                                    if (result.success == 'fail_global') {
                                        msg = result.message;
                                    } else {
                                        for (var key in result.detailMsg) {
                                            msg += result.detailMsg[key] + "<br/>";
                                        }
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                            	message("<i class=\"fa fa-check-circle margin-r-5\"></i>没有返回数据","error");
                            }
                        }
                    });
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
                
                //加载列表
                initDataGrid: function () {
                    var jsonData = {
                        pageIndex: viewModel.draw - 1,
                        pageSize: viewModel.pageSize,
                        sortField: "ts",
                        sortDirection: "desc"
                    };
                    var searchObj = viewModel.gridData.params;
                    for(var key in searchObj){
                        if(searchObj[key] && searchObj[key] != null ){
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
                                        }else{
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
                                        window.setTimeout(function(){
                                            viewModel.event.initTableHeight();
                                        },500);
                                    }
                                } else {
                                    $('#noData').show();
                                    var msg = "";
                                    for (var key in result.detailMsg) {
                                        msg += result.detailMsg[key] + "<br/>";
                                    }
                                    u.messageDialog({msg: msg, title: '请求错误', btnText: '确定'});
                                }
                            } else {
                                $('#noData').show();
                                u.messageDialog({msg: '后台返回数据格式有误，请联系管理员', title: '数据错误', btnText: '确定'});
                            }
                        }
                    });
                },            	
                /*** 头部区操作事件、方法——end ***/
            	
                
                /*** 列表区【主表】操作事件、方法 ***/
                //新增操作
                addClick: function() {
                    viewModel.formStatus = _CONST.FORM_STATUS_ADD;
                    //只显示返回和保存按钮
                    viewModel.event.userCardBtn();
                    viewModel.event.clearDa(viewModel.formData);
                    viewModel.formData.createEmptyRow();
                    viewModel.formData.setRowSelect(0);
                    
                    //设置业务操作逻辑
                    viewModel.event.clearDa(viewModel.subGridData);
                    var row = viewModel.formData.getCurrentRow();
                    
                    //显示操作卡片
                    viewModel.md.dGo('addPage');
                    $('#addPage').each(function(index,element){
                        $(element).find('input[type!="radio"]').attr('disabled',false);
                    });
                    //防止多次HTML不为空
                    viewModel.event.initTableHeight();
                },
                
                //主表行——双击事件
                dbClickRow:function(gridObj){
                    viewModel.event.doEdit(gridObj.rowObj.value);
                },
                
                //刪除操作
                deleteClick: function() {
                    let num = viewModel.gridData.selectedIndices();
                    if (num.length > 0) {
                        var selectDatas = viewModel.gridData.getSimpleData({
                            "type": 'select'
                        });
                        viewModel.event.deleteFunc(selectDatas);
                    }
                },
                
                //刪除方法——调用后台服务
                deleteFunc: function(datas) {
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
                doEdit: function(rowData){
                	if(rowData){
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
                            success: function(result){
                                if (result) {
                                    if (result.success == 'success') {
                                    	if (result.detailMsg.data) {
                                            viewModel.formStatus = _CONST.FORM_STATUS_EDIT;
                                            //表单数据
                                            var curFormData =[result.detailMsg.data.data];
                                            viewModel.event.userCardBtn();
                                            viewModel.formData.clear();
                                            viewModel.formData.setSimpleData(curFormData);

                                            //子表数据
                                            if(result.detailMsg.data.subPage.content.length){
                                                $('#childNoData').hide();
                                            }else{
                                                $('#childNoData').show();
                                            };
                                            viewModel.subGridData.removeAllRows();
                                            viewModel.subGridData.clear();
                                            var subPage = result.detailMsg.data.subPage;
                                            viewModel.subGridData.setSimpleData(subPage.content, {unSelect: true});
                                            
                                            //显示操作卡片
                                            viewModel.md.dGo('addPage');
                                            $('#addPage').each(function(index,element){
                                                $(element).find('input[type!="radio"]').attr('disabled',false);
                                            });

                                            viewModel.child_card_pcomp.update({ 				//卡片页子表的分页信息
                                                totalPages: subPage.totalPages,
                                                pageSize: viewModel.pageSize,
                                                currentPage: viewModel.childdraw,
                                                totalCount: subPage.totalElements
                                            });

                                            if(subPage.totalElements > viewModel.pageSize ){	//根据总条数，来判断是否显示子表的分页层
                                                $('#child_card_pagination').show();
                                            }else{
                                                $('#child_card_pagination').hide();
                                            }
                                            viewModel.event.initTableHeight();
                                    	}else {
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
                            }
                        });
                	}else{
                        u.messageDialog({msg: '请选择一条记录！', title: '提示信息', btnText: '确定'});
                	}
                },
                
                /*** 新增/修改页面操作事件、方法 ***/
                //子表操作
                //子表——新增行
                addRow4SubGrid: function () {
                    viewModel.subGridData.createEmptyRow();
                },

                //子表行——操作列
                optFunc4SubGrid:function(obj){
                    var curRowId = obj.row.value["$_#_@_id"];
                    var rownum = obj.rowIndex;
                    var delFunc = "data-bind=click:event.subDeleteClick.bind($data," + curRowId + ","+ rownum +")";
                    obj.element.innerHTML = '<div class="editTable" style="display:none;">' + 
                    							'<button class="u-button u-button-border" title="删除" ' + delFunc + ">删除</button></div>";
                    ko.cleanNode(obj.element);
                    ko.applyBindings(viewModel, obj.element);
                },
                
                //任务分解子表删除
                subDeleteClick: function(rowId,rowIndex,e){
                    if (rowId && rowId != -1) {
                        var datatableRow = viewModel.subGridData.getRowByRowId(rowId);
                        //修改操作
                        var rowData = datatableRow.getSimpleData();
                        viewModel.event.doDelete4SubGrid(rowData,rowIndex);
                    }
                },
                
                //子表删除方法
                doDelete4SubGrid: function (data,rowIndex) {
                    if (!data) {
                        u.messageDialog({
                            msg: "请选择要删除的行!",
                            title: "提示",
                            btnText: "OK"
                        });
                    }else {
                    u.confirmDialog({
                        msg: '<div class="pull-left col-padding u-msg-content-center" >' +
                        	 '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                        title: ' ',
                        onOk: function () {
                        	var ids = [];
                            if(data.id==null || data.id==""){
                            	return;
                            }else{
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
                                            u.messageDialog({msg: result.message, title: '操作提示', btnText: '确定'});
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
                
                //子表行——悬浮事件
                rowHover4SubGrid:function (obj) {
                    $(".editTable").hide();
                    var num = obj["rowIndex"];
                    var a = $("#subDataGrid_content_tbody").find("tr")[num];
                    var ele = a.getElementsByClassName("editTable");
                    ele[0].style.display = "block";
                },
                
                /**********************************************************************/
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
            }, // end  event

            
            /*************** 列表操作 ******************/
            //定义列表——操作列内容
            optFunc: function(obj) {
                var rowId = obj.row.value["$_#_@_id"];
                var editFunc = "data-bind=click:doEditRow.bind($data," + rowId + ")";
                var delFunc = "data-bind=click:doDeleteRow.bind($data," + rowId + ")";
                obj.element.innerHTML =  '<div class="editTable">' + 
                						 '<button class="u-button u-button-border"title="修改" ' + editFunc + ">修改</button>" +
                						 '<button class="u-button u-button-border" title="删除" ' + delFunc + ">删除</button></div>";
                ko.applyBindings(viewModel, obj.element);
            },
            
            //行操作——修改
            doEditRow: function(rowId, s, e) {
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
            doDeleteRow: function(rowId) {
                var dataGridRow = viewModel.gridData.getRowByRowId(rowId);
                u.confirmDialog({
                    msg: '<div class="pull-left col-padding u-msg-content-center" >' +
                    	 '<i class="fa fa-exclamation-triangle margin-r-5 fa-3x red del-icon" style="vertical-align:middle"></i><br/>确认删除这些数据吗？</div>',
                    title: "删除确认提示",
                    onOk: function() {
                        var currentData = dataGridRow.getSimpleData();
                        viewModel.event.deleteFunc([currentData]);
                    }
                });
            }
        };
        //end viewModel

        viewModel.ygdemo_searchFormDa.createEmptyRow();
        window.csrfDefense();									//跨站请求伪造防御

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
        	 viewModel.event.initDataGrid();
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
                if (e.keyCode == 13) {			//失去焦点
                    $('.search-enter').blur();
                    $('#user-action-search').trigger('click');
                }
            });
            var toggleBtn = document.querySelector("#condition-toggle");
            u.on(toggleBtn, "click", function() {
                var conditionRow = document.querySelector("#condition-row");
                var toggleIcon = this.querySelector("i");
                if (u.hasClass(conditionRow, "b-searech-close")) {
                    u.removeClass(conditionRow, "b-searech-close")
                        .addClass(conditionRow, "b-searech-open");
                    u.removeClass(toggleIcon, "uf-arrow-up")
                        .addClass(toggleIcon, "uf-arrow-down");
                    this.querySelector("span").innerText = "收起";
                } else {
                    u.removeClass(conditionRow, "b-searech-open")
                        .addClass(conditionRow, "b-searech-close");
                    u.removeClass(toggleIcon, "uf-arrow-down")
                        .addClass(toggleIcon, "uf-arrow-up");
                    this.querySelector("span").innerText = "展开";
                }
            });
        }
    }
});