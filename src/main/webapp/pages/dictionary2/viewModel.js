var meta = {
  meta: {
    //主键
    id: { type: "string" },
    //编码
    code: {
      type: "string",
      required: true,
      nullMsg: "编码不能为空!"
    },
    //名称
    name: {
      type: "string",
      required: true,
      nullMsg: "名称不能为空!"
    },
    //是否固定(系统预置)
    sys: {
      type: "string",
      default: "否"
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
  md: document.querySelector('#u-mdlayout'),	
  condition: new u.DataTable(conditionMeta),
  gridData: new u.DataTable(meta),
  formData: new u.DataTable(meta),
  yesOrNo: [{name: "是", value: "是"}, {name: "否", value: "否"}]
};

