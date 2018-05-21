var meta = {
		//待确认	houlf
    params: {
        "cls": "com.yonyou.iuap.example.entity.mybatis.SysDictType" //使用 mybaties方式
    },
    
    meta: {
        //主键  
        id: {type: 'string'},
        //编码
        code: {
            type: 'string',
            required: true,
            nullMsg: '编码不能为空!'
        },
        //名称
        name: {
            type: 'string',
            required: true,
            nullMsg: '名称不能为空!'
        },
        //省份
        province: {
            type: 'string'
        },
        //城市
        city: {
            type: 'string'
        },
        //企业规模
        corpSize:{},
        //状态
        status: {}
    },
    
    pageSize: 5,
    //启用前端缓存
    pageCache: false
}