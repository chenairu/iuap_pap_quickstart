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
        //单据类型
        billType: {
            'refmodel':JSON.stringify({
                "refClientPageInfo":{"currPageIndex":0,
                "pageCount":0,"pageSize":5},
                "refCode":"currtype",
                "refModelUrl":"http://172.168.200.1:8082/iuap-example/restExampleAsVal/",
                "refName":"单据类型",
                "refUIType":"RefGrid",
                "rootName":"单据类型"
            }),
            //自定义过滤条件JS配置
           /* "refparam":JSON.stringify({
               "code":"BILL_TYPE"
            }),*/
            'refcfg':JSON.stringify({"ctx":"/uitemplate_web","isMultiSelectedEnabled":true,
               // "strFieldCode":["code","name","value"],
              //  "strFieldName":["父节点","名称","编码"],
                "defaultFieldCount":3

            })
        },
        //是否有效
        isValid: {

        },
        fiscal:{

        },
        busiDate: {

        },
        //付款单位
        payCode:{
            'refmodel':JSON.stringify({
                "refClientPageInfo":{"currPageIndex":0,
                    "pageCount":0,"pageSize":5},
                "refCode":"currtype",
                "refModelUrl":"http://172.168.200.1:8082/iuap-example/restExampleAsVal/",
                "refName":"单据类型",
                "refUIType":"RefGrid",
                "rootName":"单据类型"
            }),
            //自定义过滤条件JS配置
            /* "refparam":JSON.stringify({
                "code":"BILL_TYPE"
             }),*/
            'refcfg':JSON.stringify({"ctx":"/uitemplate_web","isMultiSelectedEnabled":true,
                // "strFieldCode":["code","name","value"],
                //  "strFieldName":["父节点","名称","编码"],
                "defaultFieldCount":3

            })
        },
        payName:{

        },
        //收款单位
        inCode:{
            'refmodel':JSON.stringify({
                "refClientPageInfo":{"currPageIndex":0,
                    "pageCount":0,"pageSize":5},
                "refCode":"currtype",
                "refModelUrl":"http://172.168.200.1:8082/iuap-example/restExampleAsVal/",
                "refName":"单据类型",
                "refUIType":"RefGrid",
                "rootName":"单据类型"
            }),
            //自定义过滤条件JS配置
            /* "refparam":JSON.stringify({
                "code":"BILL_TYPE"
             }),*/
            'refcfg':JSON.stringify({"ctx":"/uitemplate_web","isMultiSelectedEnabled":true,
                // "strFieldCode":["code","name","value"],
                //  "strFieldName":["父节点","名称","编码"],
                "defaultFieldCount":3

            })
        },
        inName:{

        },
        amount:{

        },
        billStatus:{

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