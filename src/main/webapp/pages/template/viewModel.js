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
  condition: new u.DataTable(conditionMeta),
  gridData: new u.DataTable(meta),
  formData: new u.DataTable(meta),
};
