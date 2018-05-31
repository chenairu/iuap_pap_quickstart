var meta = {
    meta: {
        id: {type: 'string'},
        title: {
            type: 'string',
            require:true,
            nullMsg:"标题不能为空"
        },
        
        content: {
            type: 'string'
        },

        createUserId: {
            type: 'string'
        },

        createUserName: {
            type: 'string'
        },

        publishUserId:{
            
        },
        publishUserName:{
        },

        createTime: {

        },
        publishTime:{

        },
        status:{
            
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