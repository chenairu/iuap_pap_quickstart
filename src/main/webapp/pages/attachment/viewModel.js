var meta = {
	meta: {
		id: { type: 'string' },
		code: { type: 'string', required: true, nullMsg: '编码不能为空!' },
		name: { type: 'string', required: true, nullMsg: '名称不能为空!' }
	}
};

//后台spring-mvc配置里使用了com.yonyou.iuap.mvc.RequestArgumentResolver对参数进行处理，
//处理类里判断要自动转换为SearchParams实体，属性名前必须添加search_
//所以在创建查询mete时，在字段前添加search_
var conditionMeta = {
	meta: {
		search_code: {
			type: "string"
		},
		search_name: {
			type: "string"
		}
	}
};
var fileDataMeta = {
	meta: {
		id: { type: 'string' },//主键
		filepath: { type: 'string' },//文件名称
		filesize: { type: 'string' },//文件大小
		filename: { type: 'string' },//文件名称
		uploadtime: { type: 'string' },//上传时间
		groupname: { type: 'string' },//
		url: { type: 'string' }//URL
	}
};
var viewModel = {
	condition: new u.DataTable(conditionMeta),
	gridData: new u.DataTable(meta),
	formData: new u.DataTable(meta),
	fileData: new u.DataTable(fileDataMeta)
};
