import static com.yonyou.uap.ieop.busilog.context.ContextKeyConstant.BUSINESS_SYS_ID;
class BusinessLogConfig_iuap_example {

    def context;    
    def workorder_bmp_submit() {
        [category:"业务日志",log:"工单申请流程：执行提交方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
 
    def Bill_save() {
        [category:"业务日志",log:"${context._billName}：执行保存方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time},编码为${context._methodReturn.code},名称为${context._param0.name}"]
    }
    
    def Bill_selectAllByPage() {
        [category:"业务日志",log:"${context._billName}：执行查询方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
    def Bill_batchDeleteEntity() {
        [category:"业务日志",log:"${context._billName}：执行删除方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
    def bill_save() {
        [category:"业务日志",log:"${context._billName}:执行新增方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
    def bill_selectAllByPage() {
        [category:"业务日志",log:"${context._billName}：执行查询方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
    def bill_batchDeleteEntity() {
        [category:"业务日志",log:"${context._billName}：执行删除方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
}