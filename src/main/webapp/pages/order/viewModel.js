var meta = {
	meta: {
		id: {},
		orderCode: {
			enable: false
		},
		orderName: {
			enable: true,
			required: true,
			nullMsg: "不能为空"
		},
		customer: {
			required: true,
			nullMsg: "不能为空"
		},
		depta: {
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		dept_name: {},
		busiman: {
			'refmodel': JSON.stringify(refinfo['wbUser']),
			'refcfg': '{"ctx":"/uitemplate_web"}'
		},
		busiman_name: {},
		amount: {
			enable: true
		},
		beginDate: {},
		endDate: {},
		orderState: {
			enable: true,
			required: true,
			nullMsg: "不能为空",
			default: "新订单"
		},
		createTime: {},
		createUser: {
			'refmodel': JSON.stringify(refinfo['wbUser']),
			'refcfg': '{"ctx":"/uitemplate_web"}'
		},
		createUser_name: {},
		lastModified: {},
		lastModifyUser: {
			'refmodel': JSON.stringify(refinfo['wbUser']),
			'refcfg': '{"ctx":"/uitemplate_web"}'
		},
		lastModifyUser_name: {},
		orderDetails: {},
		version: {}
	}
};

var meta_sub = {
	meta: {
		id: {},
		detailCode: {},
		goodsCode: {
			required: true,
			nullMsg: "不能为空"
		},
		goodsName: {
			required: true,
			nullMsg: "不能为空"
		},
		price: {},
		total: {},
		amount: {},
		remark: {},
		version: {},
		dr: {},
		ts: {},
		manufacturer: {
			'refmodel': JSON.stringify({
				"refClientInfoPageInfo": {
					"currentPageIndex": 0,
					"pageCount": 0,
					"pageSize": 1000
				},
				"refCode": 'currency',
				"refModelUrl": "http://127.0.0.1:8180/iuap_pap_quickstart/reference/organization/",
				"refName": "生产厂商",
				"refUIType": "RefTree",
				"rootName": "生产厂商列表"
			}),
			'refcfg': '{"ctx":"/uitemplate_web"}'
		},
		manufacturer_name: {}
	}
};

var conditionMeta = {
    meta: {
      search_orderCode: { 
        type: "string" 
      },
      search_orderName: {
        type: "string"
      }
    }
  };
  
  var viewModel = {
    condition: new u.DataTable(conditionMeta),//查询条件
    gridData: new u.DataTable(meta),  //表格数据
    formData: new u.DataTable(meta),  //表单数据
    subGridData: new u.DataTable(meta_sub),
    yesOrNo: [{name: "是", value: "是"}, {name: "否", value: "否"}],
    comboData:[{name:'新订单',value:'01'},{name:'已完成',value:'02'},{name:'已取消',value:'03'}]
  };
  
