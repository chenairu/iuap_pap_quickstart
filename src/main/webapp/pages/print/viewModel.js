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

//后台spring-mvc配置里使用了com.yonyou.iuap.mvc.RequestArgumentResolver对参数进行处理，
//处理类里判断要自动转换为SearchParams实体，属性名前必须添加search_
//所以在创建查询mete时，在字段前添加search_
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
