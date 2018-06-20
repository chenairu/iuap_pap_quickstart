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
             ,'refparam':'{"isUseDataPower":"true"}'
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
};
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
    draw: 1,
    pageSize: 5,
    condition: new u.DataTable(conditionMeta),
    gridData: new u.DataTable(meta),
    formData: new u.DataTable(meta)
};
