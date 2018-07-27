package com.yonyou.iuap.example.sanyorder.service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Service
public class SanyOrderService extends GenericBpmService<SanyOrder>{

	/**
	 * 新增保存工单信息
	 */
    @Override
    public SanyOrder insert(SanyOrder entity) {
        entity.setOrderCode(entity.getBpmBillCode());//编码code生成
        return super.insert(entity);
    }
	@Override
	public SanyOrder save(SanyOrder entity) {
        entity.setOrderCode(entity.getBpmBillCode());//编码code生成
		return super.save(entity);
	}
	

	private SanyOrderMapper sanyOrderMapper;
	
	@Autowired
	public void setSanyOrderMapper(SanyOrderMapper sanyOrderMapper) {
		this.sanyOrderMapper = sanyOrderMapper;
		super.setIbatisMapperEx(this.sanyOrderMapper);
	}

    @Override
    public BPMFormJSON buildBPMFormJSON(String processDefineCode, SanyOrder entity) {
        try {
            BPMFormJSON bpmform = new BPMFormJSON();
            bpmform.setProcessDefinitionKey(processDefineCode);
            String userName = InvocationInfoProxy.getUsername();
            try {
                userName = URLDecoder.decode(userName,"utf-8");
            } catch (UnsupportedEncodingException e) {
                userName =InvocationInfoProxy.getUsername();
            }
            //title
            String title = userName + "提交的【工单】,单号是" + entity.getBpmBillCode() + ", 请审批";
            bpmform.setTitle(title);

            // 单据id
            bpmform.setFormId(entity.getId().toString());
            // 单据号
            bpmform.setBillNo(entity.getBpmBillCode());
            // 制单人
            bpmform.setBillMarker(InvocationInfoProxy.getUserid());
            // 其他变量
            bpmform.setOtherVariables(buildEntityVars(entity));
            // 单据url
            bpmform.setFormUrl("/dist/#/templates/example-edit?btnFlag=2&search_id="+entity.getId());	// 单据url
            // 流程实例名称
            bpmform.setProcessInstanceName(title);										// 流程实例名称
            // 流程审批后，执行的业务处理类(controller对应URI前缀)
            bpmform.setServiceClass("/iuap_pap_quickstart/sany_order");// 流程审批后，执行的业务处理类(controller对应URI前缀)
            //设置单据打开类型 uui/react
            bpmform.setFormType(BPMFormJSON.FORMTYPE_REACT);
            return bpmform;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

}