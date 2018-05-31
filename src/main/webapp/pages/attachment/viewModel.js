var meta = {
	meta: {
		id: {
			type: 'string',
			required: true,
			nullMsg: 'Id不能为空!'
		},
		code: {
			type: 'string',
			required: true,
			nullMsg: '编码不能为空!'
		},
		name: {
			type: 'string',
			required: true,
			nullMsg: '名称不能为空!'
		}
	}
};


var fileDataMeta = {
	meta: {
		id: { type: 'string' },//主键
		filepath: { type: 'string' },//文件名称
		filesize: { type: 'string' },//文件大小
		filename: { type: 'string' },//文件名称
		uploadtime: { type: 'string' },//上传时间
		groupname: { type: 'string' },//
		url: { type: 'string' }//URL
	}
};

var viewModel = {
  draw: 1,
  //页数(第几页)
  pageSize: 5,
  gridData: new u.DataTable(meta),
  formData: new u.DataTable(meta),
};
