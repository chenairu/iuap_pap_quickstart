var mainMeta = {
	meta: {
		pkNotice: {
			type: "string"
		},
		noticeCode: { 
			type: "string" 
		},
		noticeName: {
			type: "string"
		},
		dspDept : {
			'refmodel' : JSON.stringify(refinfo['organization']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		dspDeptName:{
			
		},
		dspDate: {
			type: "date"
		},
		noticeContent: {
			type: "string"
		},
		emergency:{
			type: "string"
		},
		dr: {
			type: "string"
		},
		ts: {
			type: "date"
		},
		version: {
			type: "string"
		},
		lastModified: {
			type: "date"
		},
		lastModifyUser: {
			type: "string"
		},
		lastModifyUserName: {
			
		},
		createTime: {
			type: "date"
		},
		createUser: {
			'refmodel' : JSON.stringify(refinfo['bd_user']),
			'refcfg' : '{"ctx":"/uitemplate_web"}'
		},
		createUserName: {
			
		},
		processDefinitionKey:{
			type: "string"
		},
		processInstanceName:{
			type: "string"
		}
	}
};


//后台spring-mvc配置里使用了com.yonyou.iuap.mvc.RequestArgumentResolver对参数进行处理，
//处理类里判断要自动转换为SearchParams实体，属性名前必须添加search_
//所以在创建查询mete时，在字段前添加search_
var conditionMeta = {
		meta: {
			search_noticeCode: { 
				type: "string" 
			},
			search_noticeName: {
				type: "string"
			},
			search_dspDept : {
				'refmodel' : JSON.stringify(refinfo['organization']),
				'refcfg' : '{"ctx":"/uitemplate_web"}'
			},
			search_dspDate: {
				type: "date"
			},
			search_noticeContent: {
				type: "string"
			},
			search_emergency:{
				type: "string"
			}
		}
};

var processMeta = {
		meta: {
			processDefinitionKey: {
				type: "string"
			},
			processInstanceName: { 
				type: "string" 
			},
			businessPk: {
				type: "string"
			},
			businessCode: {
				type: "string"
			},
			orgId: {
				type: "string"
			},
			createUser:{
				type: "string"
			},
			showTile:{
				type: "string"
			},
			modelUrl:{
				type: "string"
			},
			serviceUrl:{
				type: "string"
			},
			businessTable:{
				type: "string"
			},
			processStatusFiled:{
				type: "string"
			}
		}
	};

var viewModel = {
	md: document.querySelector('#u-mdlayout'),
	
	condition: new u.DataTable(conditionMeta),
		
	mainGridData: new u.DataTable(mainMeta),
	
	infoData: new u.DataTable(mainMeta),
	
	status:[{value:"0",name:"正常"},{value:"1",name:"紧急"}]
	
};