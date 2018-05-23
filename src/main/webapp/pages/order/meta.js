var meta = {
	meta : {
		id : {},
		orderCode : {
			enable : false
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
			enable : true
		},
		beginDate : {},
		endDate : {},
		orderState : {
			enable : true,
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

var meta_sub = {
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
		ts : {},
		manufacturer : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":1000},
							"refCode":'currency',
							"refModelUrl":"http://localhost:8088/iuap-example/reference/organization/",
							"refName": "生产厂商",
							"refUIType": "RefTree",
							"rootName": "生产厂商列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		manufacturer_name : {}
	}
};

var searchData = {
		meta : {
			id : {},
			code : {},
			name : {},
			ly_code : {},
		}
	};