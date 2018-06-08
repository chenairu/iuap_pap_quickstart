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
		price : {
		},
		currency : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":100},
							"refCode":'currency',
							"refModelUrl":"http://127.0.0.1:8180/iuap-example/reference/dictionary/",
							"refName": "币种",
							"refUIType": "RefGrid",
							"rootName": "币种列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		currency_name : {},
		remark : {},
		version : {},
		createTime : {},
		createUser : {
			'refmodel' : JSON.stringify(refinfo['wbUser']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		createUser_name : {},
		lastModified : {},
		lastModifyUser : {},
		lastModifyUser_name : {},
		manufacturer : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":100},
							"refCode":'customOrganization',
							"refModelUrl":"http://127.0.0.1:8180/iuap-example/reference/organization/",
							"refName": "生产厂商",
							"refUIType": "RefTree",
							"rootName": "生产厂商列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		manufacturer_name : {},
		linkman : {
			'refmodel' : JSON.stringify({
								"refClientPageInfo" : {
											"pageSize" : 100,
											"currPageIndex" : 0,
											"pageCount" : 0
										},
				    			"refCode" : "contactsTree",
				    			"rootName" : "全部",
				    			"refName" : "联系人",
				    			strFieldName : ["编码", "名称","电话","邮箱"],
				    			strFieldCode : ["refcode", "refname","reftel","refemail"],
				    			defaultFieldCount : 4,
				    			"refModelUrl" : "http://127.0.0.1:8180/iuap-example/reference/treegrid/",
				    			"refUIType" : "RefGridTree"
						}),
			 'refcfg' : '{"ctx":"/uitemplate_web"}'

		},
		linkman_name : {},
		director : {
			'refmodel' : JSON.stringify({
							"refClientInfoPageInfo": {
										"currentPageIndex":0,
										"pageCount":0,
										"pageSize":100},
							"refCode":'director',
							"refModelUrl":"http://127.0.0.1:8180/iuap-example/reference/director/",
							"rootName": "业务负责人",
							"refName": "业务负责人列表",
			    			strFieldName : ["负责人编码", "负责人名称", "所属机构"],
			    			strFieldCode : ["refcode", "refname", "organization"],
			    			defaultFieldCount : 3,
							"refUIType": "RefGrid"
						}),
			 'refcfg' : '{"ctx":"/uitemplate_web"}'

		},
		director_name : {},
		
		supplier : {},
		linkmanMobile : {},
		supplierTel : {},
		supplierFax : {},
		supplierAddr : {},
		
		goodsType : {},
		goodsSubType : {}
	}

};

var viewModel = {
    draw: 1,
    pageSize: 5,
    gridData: new u.DataTable(meta),
    formData: new u.DataTable(meta),
    goodsStatus_list:[{value:"0",name:"未上架"},{value:"1",name:"已上架"},{value:"2",name:"已下架"}],
	goodsType_list:[{value:"0",name:"食品类"},{value:"1",name:"日用品类"},{value:"2",name:"鞋帽服饰类"}],
	goodsSubType_list:[],
	goodsFoods_list:[{value:"0",name:"牛奶"},{value:"1",name:"酸奶"}],
	goodsLives_list:[{value:"0",name:"刷子"},{value:"1",name:"衣架"}],
	goodsDress_list:[{value:"0",name:"大衣"},{value:"1",name:"裙子"}]
};
