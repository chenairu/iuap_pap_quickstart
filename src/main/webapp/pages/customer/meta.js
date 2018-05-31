var meta = {
    meta: {
        //主键  
        id: {type: 'string'},
        //编码
        customerCode: {
            type: 'string',
            enable:false
        },
        //名称
        customerName: {
            type: 'string',
            required: true,
            nullMsg: '名称不能为空!'
        },
        //省份
        province: {
            /*'refmodel' : JSON.stringify(refinfo['wbUser']),
            'refcfg' : '{"ctx":"/uitemplate_web"}'*/
        },
        //城市
        city: {
            default:function(){
                    value:$.cookie("userId")
            }

        },
        //企业规模
        corpsize:{
            type: 'string'

        },
        //状态
        status: {
            type: 'string'
        }
    },
    
    pageSize: 5,
    //启用前端缓存
    pageCache: false
}