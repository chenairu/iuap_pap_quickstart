package com.yonyou.iuap.example.sanyorder.web.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(SanyOrderController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = SanyOrder.class) SearchParams searchParams) {
		Page<SanyOrder> page = sanyOrderService.selectAllByPage(pageRequest, searchParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return this.buildMapSuccess(map);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestParam("id") String id) {
		SanyOrder SanyOrder = sanyOrderService.findById(id);
		return this.buildSuccess(SanyOrder);
	}
	
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@ResponseBody
	public Object batchSave(@RequestBody List<SanyOrder> listSanyOrder, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			sanyOrderService.batchSave(listSanyOrder);
			jsonResp = this.buildSuccess(listSanyOrder);
		}catch(Exception exp) {
			logger.error("工单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody SanyOrder SanyOrder, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			sanyOrderService.save(SanyOrder);
			jsonResp = this.buildSuccess(SanyOrder);
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
			this.sanyOrderService.delete(ids.get(i));
		}
		return super.buildSuccess(ids);
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(@RequestBody List<SanyOrder> listSanyOrder, HttpServletRequest request, HttpServletResponse response) throws Exception {
		for(int i=0; i<listSanyOrder.size(); i++) {
			this.sanyOrderService.delete(listSanyOrder.get(i));
		}
		return super.buildSuccess(listSanyOrder);
	}
	
	/*************************************************************/
	@Autowired
	private SanyOrderService sanyOrderService;
	
}