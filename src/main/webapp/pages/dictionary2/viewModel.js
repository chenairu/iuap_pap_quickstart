var meta = {
  meta: {
    //主键
    id: { type: "string" },
    //编码
    code: {
      type: "string",
      required: true,
      nullMsg: "字典类型编码不能为空!"
    },
    //名称
    name: {
      type: "string",
      required: true,
      nullMsg: "字典类型名称不能为空!"
    },
    //是否固定(系统预置)
    sys: {
      type: "string",
      default: "N"
    },
    //备注信息
    remark: {
      type: "string"
    },
    //创建者
    creator: {
      type: "string"
    },
    createtime: {
      type: "string"
    }
  }
};
var viewModel = {
  draw: 1,
  //页数(第几页)
  pageSize: 5,
  gridData: new u.DataTable(meta),
  formData: new u.DataTable(meta),
  yesOrNo: [{name: "是", value: "是"}, {name: "否", value: "否"}]
};
