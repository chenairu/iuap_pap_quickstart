package com.yonyou.iuap.example.workorder.web.controller;

import java.util.Date;
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
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.bpm.service.JsonResultService;
import com.yonyou.iuap.bpm.web.IBPMBusinessProcessController;
import com.yonyou.iuap.example.workorder.service.WorkorderService;
import com.yonyou.iuap.mvc.type.JsonResponse;

import yonyou.bpm.rest.response.historic.HistoricProcessInstanceResponse;

@Controller
@RequestMapping(value = "/example_workorder")
public class WorkorderBPMController implements IBPMBusinessProcessController {
	
	private Logger logger = LoggerFactory.getLogger(WorkorderBPMController.class);

	  @RequestMapping(value={"/doApprove"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public Object doApproveAction(@RequestBody Map<String, Object> params, HttpServletRequest arg1) throws Exception {
		logger.info(JSON.toJSONString(params));
		String jsonResult = this.jsonResultService.toJson(params);
		JSONObject requestBody = (JSONObject) this.jsonResultService.toObject(jsonResult, JSONObject.class);
		Object Node = requestBody.get("historicProcessInstanceNode");
		HistoricProcessInstanceResponse historicProcessInstance = (HistoricProcessInstanceResponse) this.jsonResultService
																		.toObject(Node.toString(), HistoricProcessInstanceResponse.class);
		Date endTime = historicProcessInstance.getEndTime();
		if (endTime != null) {
			String busiId = historicProcessInstance.getBusinessKey();
			workorderService.doApprove(busiId);
		}
		JsonResponse response = new JsonResponse();
		return response;
	}

	@Override
	public JsonResponse doRejectMarkerBillAction(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonResponse doTerminationAction(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**************************************************************************************/
	@Autowired
	private JsonResultService jsonResultService;
	@Autowired
	private WorkorderService workorderService;

}