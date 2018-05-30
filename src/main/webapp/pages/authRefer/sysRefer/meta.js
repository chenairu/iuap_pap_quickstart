var meta = {
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
        //组织
        orgId: {
            'refmodel' : JSON.stringify(refinfo['organization']),
            'refcfg' : '{"ctx":"/uitemplate_web"}'
            ,'refparam':'{"isUseDataPower":"true"}'
        },
        orgName:{

        },
        //部门ID
        deptId: {
            'refmodel' : JSON.stringify(refinfo['dept']),
            'refcfg' : '{"ctx":"/uitemplate_web"}'
           // ,'refparam':'{"isUseDataPower":"true"}'
        },
        //部门
        deptName:{

        },
        //创建人
        createUserName:{
            default:function(){
                return decodeURI($.cookie("_A_P_userName"))
            }
        },
        //创建人ID
        createUserId:{
            default:function(){
                return $.cookie("userId")
            }
        },
        //创建时间
        createTime:{
            enbale:false,
            default:function(){
                return new Date();
            }
        }
    },
    
    pageSize: 5,
    //启用前端缓存
    pageCache: false
}