var iuap = iuap || {};


/**
 * 信息框
 * @param message	显示信息
 */
iuap.message = function (message) {
	u.messageDialog({
		msg: message,
		title: "友情提醒",
		btnText: "确定",
		width: "400px"
	});
};




/**
 * ajax删除数据
 * @param url	删除URL
 * @param data	要删除的数据
 * @param succCallBack	成功回调函数
 */
iuap.ajaxDelData = function (url, data, succCallBack) {
	$.ajax({
		url: appCtx + url,
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function (result) {
			if (result.state == "success") {
				if (succCallBack) {
					succCallBack(result);
				}
			}
		}
	});
};

/**
 * ajax保存数据
 * @param url	保存URL
 * @param data	要保存的数据
 * @param succCallBack	成功回调函数
 */
iuap.ajaxSaveData = function (url, data, succCallBack) {
	$.ajax({
		url: appCtx + url,
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(data),
		success: function (result) {
			if (result.state == "success") {
				if (succCallBack) {
					succCallBack(result);
				}
			}
		}
	});
};

/**
 * ajax查询数据
 * @param url	查询URL
 * @param data	要查询的数据
 * @param succCallBack	成功回调函数
 */
iuap.ajaxQueryData = function (url, queryData, succCallBack) {
	$.ajax({
		type: 'GET',
		url: appCtx + url,
		data: queryData,
		contentType: 'application/json;charset=utf-8',
		dataType: 'json',
		success: function (result) {
			if (result.state == "success") {
				if (succCallBack) {
					succCallBack(result);
				}
			}
		}
	});
};

/**
 * html转义
 * @param html	需要转义的html
 */
iuap.htmlEncode = function (html) {
	var temp = document.createElement("div");
	(temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
	var output = temp.innerHTML;
	temp = null;
	return output;
};

/**
 * html反转义
 * @param text	需要转义的文本
 */
iuap.htmlDecode = function (text) {
	var temp = document.createElement("div");
	temp.innerHTML = text;
	var output = temp.innerText || temp.textContent;
	temp = null;
	return output;
};

/**
 * 模板打印
 * @param templateCode	模板编码
 * @param url	取数请求
 * @param businessPk	业务主键
 */
iuap.printTemplate = function (templateCode, url, businessPk) {
	if (businessPk != undefined && businessPk.trim() != null) {
		var tenantId = "tenant";//固定字符串
		var serverUrl = appCtx + url;//取数据的url地址
		var params = {//去后台打印数据的参数
			'id': businessPk
		};
		params = encodeURIComponent(JSON.stringify(params));//URL参数部分有特殊字符，必须编码(不同的tomcat对特殊字符的处理不一样)
		var url = '/print_service/print/preview?tenantId='
			+ tenantId + '&printcode=' + templateCode + '&serverUrl=' + serverUrl
			+ '&params=' + params + '&sendType=post';
		window.open(url);
	} else {
		iuap.message('业务主键不能为空！');
	}
};

/**
 * 创建导出表单对象
 * @param mainPage	界面第一层DIV对象
 * @param url	请求URL
 * @param ids	需要导出的数据主键
 */
iuap.createExpForm = function (mainPage, url, ids) {
	var form = $("<form>");   //定义一个form表单
	form.attr('style', 'display:none');   //在form表单中添加查询参数
	form.attr('target', '');
	form.attr('method', 'post');
	form.attr('action', appCtx + url);
	//将表单追加到当前html中，否则会报Form submission canceled because the form is not connected错误
	mainPage.append(form);  //将表单放置在web中
	if (ids != null && ids != "") {
		var input1 = $('<input>');
		input1.attr('type', 'hidden');
		input1.attr('name', 'ids');
		input1.attr('value', ids);
		form.append(input1);   //将查询参数控件提交到表单上
	}
	var input2 = $('<input>');
	input2.attr('type', 'hidden');
	input2.attr('name', 'x-xsrf-token');
	input2.attr('value', window.x_xsrf_token);
	form.append(input2);
	return form;
};

/**
 * excel数据导入
 * @param mainPage	界面第一层DIV对象
 * @param url	请求URL
 */
iuap.excelDataImp = function (mainPage, url) {
	//生成上传弹出框的hmtl
	var excelImpHtml = '<div id="excel_imp_dialog" style="display: none;">' +
		'	<div class="u-msg-title">' +
		'		<h4>导入</h4>' +
		'	</div>' +
		'	<div class="u-msg-content aline-center">' +
		'		 <div class="u-msg-uplode-content">' +
		'     	 	<div class="choosefile">' +
		'         		<div class="choosefileImg">' +
		'             		<img src="../example/static/beforeUpload.svg" id="excelUploadImg">' +
		'         		</div>' +
		'         		<div id="excelUploadMsg" class="uploadingMsg"></div>' +
		'         		<button class="u-button u-button-border uploadBtn " title="选择上传文件" id="selectFileBtn">选择上传文件</button>' +
		'         		<input style="display: none" type="file" name="excelImpFile" id="excelImpFile" />' +
		'     		</div>' +
		'     		<div class="filenamediv" id="filenamediv2"></div>' +
		'     		<div class="file-loding" style="display: none;">' +
		'         		<div class="file-lodedPart" style="height:26px;background-color:#039BE5;color:#fff"></div>' +
		'     		</div>' +
		' 		</div>' +
		'	</div>' +
		'	<div class="u-msg-footer u-msg-date-footer">' +
		'	</div>' +
		'</div>';

	//追加到界面中
	mainPage.prepend(excelImpHtml);

	//将html放到dialog中显示
	window.md = u.dialog({
		id: 'excelImpDialog',
		content: "#excel_imp_dialog",
		width: "400px",
		hasCloseMenu: true
	});

	//绑定文件选择按钮的单击事件，使得调用文件框的文件选择
	window.setTimeout(function () {
		document.getElementById('selectFileBtn').addEventListener('click', function () {
			document.getElementById('excelImpFile').click();
		}, false);
	}, 500);

	//移除显示的文件名
	$("#filenamediv2").html("");
	$("#excelUploadMsg").html("").addClass("uploading").removeClass("fail").removeClass("success");

	//当文件发生改变，重新获取文件名在div中显示
	$("#excelImpFile").change(function () {
		var file = $("#excelImpFile").val();
		var strFileName = file.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi, "$1");
		$("#filenamediv2").html(strFileName);
		//改变显示图标
		$("#excelUploadImg").attr("src", "../example/static/uploading.svg");
		//改变显示信息
		$("#excelUploadMsg").html("文件上传中").addClass("uploading").removeClass("success").removeClass("fail");
		//显示文件上传进度条
		$(".file-loding").show();
		$('.file-lodedPart').width(0);

		//进度条增加
		window.loadingTimer = window.setInterval(function () {
			var loadingpart = $('.file-lodedPart');
			var width = loadingpart.width();
			//宽度为360
			if (width > 360) {
				window.clearInterval(viewModel.loadingTimer);
			}
			//每次宽度增加3.6
			loadingpart.width(width + 3.6);
			var progress = parseInt(width / 3.6);
			//当进度数字大于100时显示100
			if (progress > 100) {
				progress = 100;
			}
			//进度条显示数字
			loadingpart.html(progress + '%');
		}, 100);

		//发起数据导入请求
		$.ajaxFileUpload({
			url: appCtx + url,
			timeout: 30000,
			fileElementId: 'excelImpFile',
			dataType: 'json',//返回值
			success: function (data) {
				window.clearInterval(window.loadingTimer);
				md.close();
				iuap.message('数据导入成功，请重新查询数据！');
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				window.clearInterval(window.loadingTimer);
				md.close();
				iuap.message('数据导入失败！');
			}
		});
	});
};

