var meta = {
  meta: {
    institcode: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "机构编码不能为空!"
    },
    institname: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "机构名称不能为空!"
    },
    institid: {
      type: "string"
    },
    parentid: {
      type: "string"
    },
    parentname: {
      type: "string"
    },
    shortname: {
      type: "string"
    }
  }
};

var metaTelbook = {
  meta: {
    id: {
      type: "string"
    },
    peoname: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "姓名不能为空!"
    },
    peocode: {
      type: "string",
      required: true,
      notipFlag: true,
      hasSuccess: true,
      nullMsg: "员工编号不能为空!"
    },
    institid: {
      type: "string"
    },
    institname: {
      type: "string"
    },
    worktel: {
      type: "string",
      regExp: /^1[34578]\d{9}$/,
      notipFlag: true,
      hasSuccess: true,
      errorMsg: "手机号码格式不对。"
    },
    email: {
      type: "string",
      regExp: /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/,
      notipFlag: true,
      hasSuccess: true,
      errorMsg: "邮箱格式不对。"
    },
    tel: {
      type: "string"
    },
    office: {
      type: "string"
    },
    operate: {
      type: "string"
    }
  }
};


//后台spring-mvc配置里使用了com.yonyou.iuap.mvc.RequestArgumentResolver对参数进行处理，
//处理类里判断要自动转换为SearchParams实体，属性名前必须添加search_
//所以在创建查询mete时，在字段前添加search_
var conditionMeta = {
  meta: {
    search_searchParam: { 
      type: "string" 
    }
  }
};


var viewModel = {
  app: {},
  draw: 1,
  totalPage: 0,
  pageSize: 5,
  totalCount: 0,



  condition: new u.DataTable(conditionMeta),

  /* 树数据 */
  treeData: new u.DataTable(meta),

  /* 编辑框树数据 */
  formData_org: new u.DataTable(meta),

  /* 电话本数据 */
  gridData: new u.DataTable(metaTelbook),

  /* 电话本数据 */
  formData_person: new u.DataTable(metaTelbook),


  /* 树设置 */
  treeSetting: {
    view: {
      showLine: false,
      selectedMulti: false
    },
    callback: {
      onClick: function (e, id, node) {
        treeid = [];
        viewModel.event.getAllChildren(node, treeid);
        var pid = node.pId;
        viewModel.event.loadTelbook(treeid);
      }
    }
  },
}
