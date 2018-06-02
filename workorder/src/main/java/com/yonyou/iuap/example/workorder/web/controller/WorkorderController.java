package com.yonyou.iuap.example.workorder.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.example.workorder.service.WorkorderService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/example_workorder")
public class WorkorderController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(WorkorderController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = Workorder.class) SearchParams searchParams) {
		Page<Workorder> page = workorderService.selectAllByPage(pageRequest, searchParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return this.buildMapSuccess(map);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestParam("id") String id) {
		Workorder workorder = workorderService.findById(id);
		return this.buildSuccess(workorder);
	}
	
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@ResponseBody
	public Object batchSave(@RequestBody List<Workorder> listWorkorder, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			workorderService.batchSave(listWorkorder);
			jsonResp = this.buildSuccess(listWorkorder);
		}catch(Exception exp) {
			logger.error("工单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody Workorder workorder, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			workorderService.save(workorder);
			jsonResp = this.buildSuccess(workorder);
		}catch(Exception exp) {
			logger.error("工单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteByIds(@RequestBody List<String> ids, HttpServletRequest request, HttpServletResponse response) throws Exception {
		for(int i=0; i<ids.size(); i++) {
			this.workorderService.delete(ids.get(i));
		}
		return super.buildSuccess(ids);
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(@RequestBody List<Workorder> listWorkorders, HttpServletRequest request, HttpServletResponse response) throws Exception {
		for(int i=0; i<listWorkorders.size(); i++) {
			this.workorderService.delete(listWorkorders.get(i));
		}
		return super.buildSuccess(listWorkorders);
	}
	
	/**
	 * 提交申请
	 * @param listWorkorder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Object submit(@RequestBody List<Workorder> listWorkorder, HttpServletRequest request, HttpServletResponse response) {
		String processDefineCode = request.getParameter("processDefineCode");
		try{
			String resultMsg = workorderService.batchSubmit(listWorkorder, processDefineCode);
			if(StringUtils.isEmpty(resultMsg)) {
				return this.buildSuccess("工单撤回操作成功!");
			}else {
				return this.buildGlobalError(resultMsg);
			}
		}catch(Exception exp) {
			logger.info("工单提交出错!", exp);
			return this.buildGlobalError(exp.getMessage());
		}
	}

	/**
	 * 撤回申请
	 * @param listWorkorder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/recall", method = RequestMethod.POST)
	@ResponseBody
	public Object recall(@RequestBody List<Workorder> listWorkorder, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("execute recall operate.");
		String resultMsg = workorderService.batchRecall(listWorkorder);
		if(StringUtils.isEmpty(resultMsg)) {
			return this.buildSuccess("工单撤回操作成功!");
		}else {
			return this.buildGlobalError(resultMsg);
		}
	}
	
	/*************************************************************/
	@Autowired
	private WorkorderService workorderService;
	
}