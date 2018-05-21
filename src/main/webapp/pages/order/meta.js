var meta = {
	meta : {
		id : {},
		orderCode : {
			enable : true,
			required : true,
			nullMsg : "不能为空"
		},
		orderName : {
			enable : true,
			required : true,
			nullMsg : "不能为空"
		},
		customer : {
			required : true,
			nullMsg : "不能为空"
		},
		dept : {
			required : true,
			nullMsg : "不能为空",
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		dept_name : {},
		busiman : {
			'refmodel' : JSON.stringify(refinfo['people']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		busiman_name : {},
		amount : {
			enable : false
		},
		beginDate : {},
		endDate : {},
		orderState : {
			enable : false,
			required : true,
			nullMsg : "不能为空"
		},
		createTime : {},
		createUser : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		createUser_name : {},
		lastModified : {},
		lastModifyUser : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		lastModifyUser_name : {},
		orderDetails : {},
		version : {}

	}
};

var meta_detail = {
	meta : {
		id : {},
		detailCode : {},
		goodsCode : {
			required : true,
			nullMsg : "不能为空"
		},
		goodsName : {
			required : true,
			nullMsg : "不能为空"
		},
		price : {},
		total : {},
		amount : {},
		remark : {},
		version : {},
		dr : {},
		ts : {}
	}
};