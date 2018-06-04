import static com.yonyou.uap.ieop.busilog.context.ContextKeyConstant.BUSINESS_SYS_ID;
class BusinessLogConfig_iuap_example {

    def context;    
    def workorder_bmp_submit() {
        [category:"业务日志",log:"工单申请流程：执行提交方法:IP地址为${context._ip},USER用户为${context._user},TIME操作时间为${context._time}"]
    }
    
}