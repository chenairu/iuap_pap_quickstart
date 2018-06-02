package com.yonyou.iuap.example.workorder.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.bpm.service.BPMSubmitBasicService;
import com.yonyou.iuap.bpm.util.BpmRestVarType;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.workorder.dao.WorkorderMapper;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.example.workorder.util.WorkFlowUtils;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;

import yonyou.bpm.rest.ex.util.DateUtil;
import yonyou.bpm.rest.request.RestVariable;

@Service
public class WorkorderService extends GenericService<Workorder>{
	
	public void batchSave(List<Workorder> listWorkorder) {
		for(int i=0; i<listWorkorder.size(); i++) {
			this.save(listWorkorder.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public Workorder insert(Workorder workorder) {
		if(StringUtils.isEmpty(workorder.getId())) {
			workorder.setCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(workorder);
	}
	

	/**
	 * 工单申请提交（批量）
	 * @param listWorkorder
	 * @param processDefineCode
	 */
	@BusiLogConfig("workorder_bmp_submit")
	public String batchSubmit(List<Workorder> listWorkorder, String processDefineCode) {
		StringBuffer errorMsg = new StringBuffer("");
		for(Workorder workorder : listWorkorder) {
			Workorder curInfo = this.findById(workorder.getId());
			if(curInfo.getStatus() == 0) {							//当前单据状态：未提交
				this.doSubmit(workorder, processDefineCode);
			}else {
				errorMsg.append("工单["+curInfo.getCode()+"]状态不合法，无法提交!\r\n");
			}
		}
		return errorMsg.toString();
	}
	
	private void doSubmit(Workorder workorder, String processDefineCode) {
		BPMFormJSON submitJson = this.buildBPMFormJSON(processDefineCode, workorder);		
		JSONObject resultJson = bpmSubmitBasicService.submit(submitJson);
		if (WorkFlowUtils.isSuccess(resultJson)) {
			workorder.setStatus(1);									// 从未提交状态改为已提交状态;
			this.save(workorder);
		} else {
			Object msg = resultJson.get("message")!=null ? resultJson.get("message"):resultJson.get("msg");
			throw new BusinessException("提交启动流程实例发生错误，请联系管理员！错误原因：" + msg.toString());
		}
	}
	
	/**
	 * 工单申请撤回
	 * @param listWorkorder
	 */
	public String batchRecall(List<Workorder> listWorkorder) {
		StringBuffer errorMsg = new StringBuffer("");
		for(Workorder workorder : listWorkorder) {
			Workorder curInfo = this.findById(workorder.getId());
			if(curInfo.getStatus() == 1) {							//当前单据状态：已提交
				this.doRecall(workorder);
			}else {
				errorMsg.append("工单["+curInfo.getCode()+"]状态不合法，无法撤回!\r\n");
			}
		}
		return errorMsg.toString();
	}
	
	private void doRecall(Workorder workorder) {
		JSONObject resultJson = bpmSubmitBasicService.unsubmit(workorder.getId());
		if (WorkFlowUtils.isSuccess(resultJson)) {
			workorder.setStatus(0);									// 从已提交状态改为未提交状态;
			this.save(workorder);
		} else {
			Object msg = resultJson.get("message")!=null ? resultJson.get("message"):resultJson.get("msg");
			throw new BusinessException("提交启动流程实例发生错误，请联系管理员！错误原因：" + msg.toString());
		}
	}
	
	/**
	 * 审批通过——更新工单状态
	 * @param workorder
	 */
	public void doApprove(String id, Integer status) {
		Workorder workorder = this.findById(id);
		workorder.setStatus(status);
		this.save(workorder);
	}
	
	/**
	 * 驳回：更新工单状态——未提交
	 * @param id
	 */
	public void doReject(String id) {
		Workorder workorder = this.findById(id);
		workorder.setStatus(0);
		this.save(workorder);
	}
	
	/**
	 * 构建BPMFormJSON
	 * @param processDefineCode
	 * @param Workorder
	 * @return
	 * @throws  
	 */
	public BPMFormJSON buildBPMFormJSON(String processDefineCode, Workorder workorder){
		try{
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
		}catch(Exception ex){
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