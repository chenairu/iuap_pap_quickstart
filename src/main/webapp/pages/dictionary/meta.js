var meta = {
    
    meta: {
        //主键  
        id: {type: 'string'},
        //编码
        code: {
            type: 'string',
            required: true,
            nullMsg: '字典类型编码不能为空!'
        },
        //名称
        name: {
            type: 'string',
            required: true,
            nullMsg: '字典类型名称不能为空!'
        },
        //是否固定(系统预置)
        sys: {
            type: 'string',
            'default': 'N'
        },
        //备注信息
        remark: {
            type: 'string'
        },
        //创建者
        creator: {
            type: 'string'
        },
        createtime: {
            type: 'string'
        }
    },
    
    pageSize: 5,
    //启用前端缓存
    pageCache: false
}