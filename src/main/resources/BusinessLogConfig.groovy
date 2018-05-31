import static com.yonyou.uap.ieop.busilog.context.ContextKeyConstant.BUSINESS_SYS_ID;
class BusinessLogConfig {
    def context;    
    def Ygdemo_yw_info_save() {
        [category:"保存类",log:"督办任务：执行保存方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time},编码为${context._methodReturn.code},名称为${context._param0.name}"]
    }
    
    def Ygdemo_yw_info_selectAllByPage() {
        [category:"查询类",log:"督办任务：执行查询方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
    def Ygdemo_yw_info_batchDeleteEntity() {
        [category:"删除类",log:"督办任务：执行查询方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
}
