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
							"refModelUrl":"http://127.0.0.1:8088/iuap-example/reference/dictionary/",
							"refName": "币种",
							"refUIType": "RefGrid",
							"rootName": "币种列表"
					}),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
            // 'refparam':'{"isUseDataPower":"true"}',

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
							"refModelUrl":"http://127.0.0.1:8088/iuap-example/reference/organization/",
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
				    			"refModelUrl" : "http://127.0.0.1:8088/iuap-example/reference/treegrid/",
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
							"refCode":'currency',
							"refModelUrl":"http://127.0.0.1:8088/iuap-example/reference/director/",
							"rootName": "业务负责人",
							"refName": "业务负责人列表",
			    			strFieldName : ["负责人编码", "负责人名称", "所属机构"],
			    			strFieldCode : ["refcode", "refname", "organization"],
			    			defaultFieldCount : 3,
							"refUIType": "RefGrid"
						}),
			 'refcfg' : '{"ctx":"/uitemplate_web"}'

		},
		director_name : {}
	}

};