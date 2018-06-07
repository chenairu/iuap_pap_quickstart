package com.yonyou.iuap.example.workorder.web.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.bpm.service.JsonResultService;
import com.yonyou.iuap.bpm.web.IBPMBusinessProcessController;
import com.yonyou.iuap.example.workorder.service.WorkorderService;
import com.yonyou.iuap.mvc.type.JsonResponse;

@Controller
@RequestMapping(value = "/example_workorder")
@SuppressWarnings("all")
public class WorkorderBPMController implements IBPMBusinessProcessController {
	
	private Logger logger = LoggerFactory.getLogger(WorkorderBPMController.class);

	
	/**
	 * 审批通过回调服务
	 */
	@RequestMapping(value={"/doApprove"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	@ResponseBody
	public Object doApproveAction(@RequestBody Map<String, Object> params, HttpServletRequest arg1) throws Exception {
		logger.info(JSON.toJSONString(params));
		Object approvetype = params.get("approvetype");
		if(approvetype!=null && approvetype.toString().equals("agree")) {
			Object bpmNode = params.get("historicProcessInstanceNode");
			if(bpmNode != null && bpmNode instanceof Map) {
				Object busiId = ((Map)bpmNode).get("businessKey");
				Object endTime = ((Map)bpmNode).get("endTime");
				if(endTime != null) {
					workorderService.doApprove(busiId.toString(), 3);					//已办结
				}else {
					workorderService.doApprove(busiId.toString(), 2);					//审批中
				}
			}
		}else {
			Object bpmNode = params.get("historicProcessInstanceNode");
			if(bpmNode != null && bpmNode instanceof Map) {
				Object busiId = ((Map)bpmNode).get("businessKey");
				workorderService.doReject(busiId.toString());
			}
		}
		JsonResponse response = new JsonResponse();
		return response;
	}

	/**
	 * 审批驳回回调服务
	 */
	@Override
	public JsonResponse doRejectMarkerBillAction(@RequestBody Map<String, Object> params) throws Exception {
		String busiId = params.get("billId").toString();
		workorderService.doReject(busiId);
		return new JsonResponse();
	}

	@Override
	public JsonResponse doTerminationAction(@RequestBody Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**************************************************************************************/
	@Autowired
	private JsonResultService jsonResultService;
	@Autowired
	private WorkorderService workorderService;

}