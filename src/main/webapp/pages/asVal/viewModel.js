var meta = {
    meta: {
        id: { type: 'string' },
        pid: {
            type: 'string',
            require: true,
            nullMsg: "父节点不能为空"
        },
        //编号
        code: {
            type: 'string',
            enable: false
        },
        //名称
        name: {
            type: 'string',
            require: true,
            nullMsg: "名称不能为空"
        },
        //编码
        value: {
            type: 'string',
            require: true,
            nullMsg: "编码不能为空"
        },

        ordIndex: {

        },
        lstdate: {
        },
        //状态
        isSystem: {
            type: 'string'
        }
    },

    pageSize: 5,
    //启用前端缓存
    pageCache: false,
}


//后台spring-mvc配置里使用了com.yonyou.iuap.mvc.RequestArgumentResolver对参数进行处理，
//处理类里判断要自动转换为SearchParams实体，属性名前必须添加search_
//所以在创建查询mete时，在字段前添加search_
var conditionMeta = {
    meta: {
        search_value: {
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
    yesOrNo: [{
        name: "是",
        value: "1"
    },
    {
        name: "否",
        value: "0"
    }
    ]
};