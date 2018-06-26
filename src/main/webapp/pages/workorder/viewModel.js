var meta = {
	meta: {
		id : {},
		code : {
			enable : false,
			nullMsg : "不能为空"
		},
		name : {
			required : true,
			nullMsg : "不能为空"
		},
		type : {
			required : true,
			nullMsg : "不能为空",
			default: "投诉工单"
		},
		type_name : {},
		content : {
			required : true,
			nullMsg : "不能为空",
		},
		applicant : {
			enable : false
		},
		applyTime : {
			enable : false
		},
		finishTime : {},
		status : {
			enable : false,
			default: "未提交"
		},
		status_name : {},
		remark : {},
		version : {},
		createTime : {},
		createUser : {},
		lastModified : {},
		lastModifyUser : {}
	}
};


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
var viewModel = {
	condition: new u.DataTable(conditionMeta),//查询条件
	gridData: new u.DataTable(meta),
	formData: new u.DataTable(meta),
	workorderType_list:[{name:"投诉工单",value:"0"},{name:"对账工单",value:"1"}],
	workorderStatus_list:[{name:"未提交",value:"0"},{name:"已提交",value:"1"},{name:"审批中",value:"2"},{name:"已完结",value:"3"}]
};
