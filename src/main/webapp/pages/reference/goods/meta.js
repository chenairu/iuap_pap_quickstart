var meta = {

	meta : {
		id : {},
		goodsCode : {
			required : true,
			nullMsg : "不能为空"
		},
		goodsName : {
			required : true,
			nullMsg : "不能为空"
		},
		model : {
			required : true,
			nullMsg : "不能为空"
		},
		price : {},
		currency : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":5},
							"refCode":'currency',
							"refModelUrl":"http://192.168.1.7:8082/iuap-example/reference/dictionary/",
							"refName": "币种",
							"refUIType": "RefGrid",
							"rootName": "币种列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}',
            'refparam':'{"isUseDataPower":"true"}'
		},
		currency_name : {},
		remark : {},
		version : {},
		createTime : {},
		createUser : {},
		createUser_name : {},
		lastModified : {},
		lastModifyUser : {},
		lastModifyUser_name : {},
		manufacturer : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":1000},
							"refCode":'currency',
							"refModelUrl":"http://192.168.1.7:8082/iuap-example/reference/organization/",
							"refName": "生产厂商",
							"refUIType": "RefTree",
							"rootName": "生产厂商列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		manufacturer_name : {},
		linkman : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":10},
							"refCode":'currency',
							"refModelUrl":"http://192.168.1.78082/iuap-example/reference/contacts/",
							"refName": "联系人",
							"refUIType": "RefGrid",
							"rootName": "联系人列表",
							"strFieldCode":["refcode", "refname", "organization"],
							"strFieldName":["联系人编码", "联系人名称", "所属机构"],
							"defaultFieldCount":3
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		linkman_name : {}
	}

};