var meta = {
	meta : {
		id : {},
		code : {
			type : "string",
			required : true,
			nullMsg : "商品目录编码不能为空!"
		},
		name : {
			type : "string",
			required : true,
			nullMsg : "商品目录名称不能为空!"
		},
		parentId : {
			type : "string"
		},
		remark : {
			type : "string"
		}
	}
};

var viewModel = {
	draw : 1,				//页数(第几页)
	pageSize : 5,
	treeData : new u.DataTable(meta),
	formData : new u.DataTable(meta)
};