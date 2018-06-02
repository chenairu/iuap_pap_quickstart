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
			nullMsg : "不能为空"
		},
		type_name : {},
		content : {},
		applicant : {},
		applyTime : {},
		finishTime : {},
		status : {},
		status_name : {},
		remark : {},
		version : {},
		createTime : {},
		createUser : {},
		lastModified : {},
		lastModifyUser : {}
	}
};
var viewModel = {
	draw: 1,				//页数(第几页)
	pageSize: 5,
	gridData: new u.DataTable(meta),
	formData: new u.DataTable(meta),
  
	workorderType_list:[{name:"投诉工单",value:"0"},{name:"对账工单",value:"1"}],
	workorderStatus_list:[{name:"未提交",value:"0"},{name:"已提交",value:"1"},{name:"审批中",value:"2"},{name:"已完结",value:"3"}]

};
