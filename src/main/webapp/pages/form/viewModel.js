var meta1 = {
  meta: {
    code: {},
    createtime: {},
    sys: {
      type: "string",
      default: "否"
    }
  }
};

var meta2 = {
  meta: {
    sex: {}
  }
};


//主要为了测试2个formdata中分别放置下拉框，默认值和监控失效的问题。
var viewModel = {
  formData1: new u.DataTable(meta1),
  formData2: new u.DataTable(meta2),
  com1: [{ name: "是", value: "是" }, { name: "否", value: "否" }],
  com2: [{ name: "男", value: "男" }, { name: "女", value: "女" }]
};