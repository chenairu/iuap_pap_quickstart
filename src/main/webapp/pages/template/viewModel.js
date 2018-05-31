var meta = {
  meta: {
    id: { type: "string" },
    code: {
      type: "string",
      required: true,
      nullMsg: "不能为空!"
    },
    name: {
      type: "string",
      required: true,
      nullMsg: "不能为空!"
    },
    remark: {
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
};
