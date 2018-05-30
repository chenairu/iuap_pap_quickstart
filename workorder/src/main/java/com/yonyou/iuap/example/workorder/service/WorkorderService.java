package com.yonyou.iuap.example.workorder.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.bpm.service.BPMSubmitBasicService;
import com.yonyou.iuap.bpm.util.BpmRestVarType;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.workorder.dao.WorkorderMapper;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

import yonyou.bpm.rest.request.RestVariable;

@Component
public class WorkorderService extends GenericService<Workorder>{

	/**
	 * 工单申请提交（批量）
	 * @param listWorkorder
	 * @param processDefineCode
	 */
	public void batchSubmit(List<Workorder> listWorkorder, String processDefineCode) {
		for(Workorder workorder : listWorkorder) {
			this.doSubmit(workorder, processDefineCode);
		}
	}
	
	public void doSubmit(Workorder workorder, String processDefineCode) {
		BPMFormJSON submitJson = this.buildBPMFormJSON(processDefineCode, workorder);		
		JSONObject resultJson = bpmSubmitBasicService.submit(submitJson);
		if (isSuccess(resultJson)) {
			workorder.setStatus(1);									// 从未提交状态改为已提交状态;
			this.save(workorder);
		} else if (isFail(resultJson)) {
			String msg = resultJson.get("msg").toString();
			throw new BusinessException("提交启动流程实例发生错误，请联系管理员！错误原因：" + msg);
		}
	}
	
	/**
	 * 工单申请撤回
	 * @param listWorkorder
	 */
	public JSONObject batchRecall(List<Workorder> listWorkorder) {
		Workorder entity = listWorkorder.get(0);
		entity.setStatus(0);							// 从已提交状态改为未提交状态;
		this.save(entity);
		
		return bpmSubmitBasicService.unsubmit(entity.getId());
	}
	
	/**
	 * 审批通过
	 * @param workorder
	 */
	public void doApprove(String id) {
		Workorder workorder = this.findById(id);
		workorder.setStatus(2);
		this.save(workorder);
	}
	
	private boolean isSuccess(JSONObject resultJson){
		return resultJson.get("flag").equals("success");
	}
	private boolean isFail(JSONObject resultJson){
		return resultJson.get("flag").equals("fail");
	}
	
	/**
	 * 设置BPMFormJSON
	 * @param processDefineCode
	 * @param Workorder
	 * @return
	 * @throws  
	 */
	public BPMFormJSON buildBPMFormJSON(String processDefineCode, Workorder workorder){
		try
		{
			BPMFormJSON bpmform = new BPMFormJSON();
			bpmform.setProcessDefinitionKey(processDefineCode);
			String userName = InvocationInfoProxy.getUsername();
			userName = java.net.URLDecoder.decode(userName,"utf-8");
			userName = java.net.URLDecoder.decode(userName,"utf-8");
			String title = userName + "提交的【工单】,单号 是" + workorder.getCode() + ", 请审批";
			
			bpmform.setTitle(title);			
			bpmform.setFormId(workorder.getId());										// 单据id
			bpmform.setBillNo(workorder.getCode());										// 单据号
			bpmform.setBillMarker(InvocationInfoProxy.getUserid());						// 制单人
			String orgId = "";// usercxt.getSysUser().getOrgId() ;				
			bpmform.setOrgId(orgId);													// 组织
			bpmform.setOtherVariables(assemblingOtherVariables(workorder));				// 其他变量			
			bpmform.setFormUrl("/iuap-example/pages/workorder/workorder.js");			// 单据url
			bpmform.setProcessInstanceName(title);										// 流程实例名称
			String url = "/iuap-example/example_workorder";								// 流程审批后，执行的业务处理类(controller对应URI前缀)
			bpmform.setServiceClass(url);
			return bpmform;
		}
		catch(Exception ex)
		{
			throw new RuntimeException(ex.getMessage());
		}
	}

	public List<RestVariable> assemblingOtherVariables(Workorder workorder) {
		Field[] fields = workorder.getClass().getDeclaredFields();
		List<RestVariable> otherVariables = new ArrayList<RestVariable>();

		for (Field field : fields) {
			Class<?> type = field.getType();
			field.setAccessible(true);
			Object fieldValue;
			try {
				fieldValue = field.get(workorder);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} 
			
			String restVariavleType = BpmRestVarType.ClassToRestVariavleTypeMap.get(type);
			if (restVariavleType == null || fieldValue == null) {
				continue;
			}

			RestVariable var = new RestVariable();
			var.setName(field.getName());
			
			if (restVariavleType.equals("date")){
				//var.setValue("2017-10-10 10:30:30");
			}else{
				var.setValue(fieldValue);
			}
			var.setType(restVariavleType);
			otherVariables.add(var);
		}
		return otherVariables;
	}
	
	/******************************************************/
	private WorkorderMapper workorderMapper;
	@Autowired
	private BPMSubmitBasicService bpmSubmitBasicService;

	@Autowired
	public void setWorkorderMapper(WorkorderMapper workorderMapper) {
		this.workorderMapper = workorderMapper;
		super.setIbatisMapper(workorderMapper);
	}
	
}