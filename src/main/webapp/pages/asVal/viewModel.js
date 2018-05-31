var meta = {
    meta: {
        id: {type: 'string'},
        pid: {
            type: 'string',
            require:true,
            nullMsg:"父节点不能为空"
        },
        //编号
        code: {
            type: 'string',
            enable:false
        },
        //名称
        name: {
            type: 'string',
            require:true,
            nullMsg:"名称不能为空"
        },
        //编码
        value: {
            type: 'string',
            require:true,
            nullMsg:"编码不能为空"
        },

        ordIndex:{
            
        },
        lstdate:{
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
var viewModel = {
    draw: 1,
    pageSize: 5,
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