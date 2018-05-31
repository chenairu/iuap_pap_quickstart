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
var viewModel = {
    draw: 1,
    pageSize: 5,
    gridData: new u.DataTable(meta),
    formData: new u.DataTable(meta),
    yesOrNo: [{
        name: "启用",
        value: "1"
    },
        {
            name: "停用",
            value: "0"
        }
    ]
};