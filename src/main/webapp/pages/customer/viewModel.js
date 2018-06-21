var meta = {
    meta: {
        //主键  
        id: {type: 'string'},
        //编码
        customerCode: {
            type: 'string',
            required: true,
            nullMsg: '编码不能为空!'
        },
        //名称
        customerName: {
            type: 'string',
            required: true,
            nullMsg: '名称不能为空!'
        },
        //省份
        province: {

        },
        //城市
        city: {

        },
        //企业规模
        corpsize:{

        },
        //状态
        status: {

        }
    },
    
    pageSize: 5,
    //启用前端缓存
    pageCache: false
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
    yesOrNo: [{name: "启用", value: "1"}, {name: "停用", value: "0"}]
};