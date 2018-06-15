package com.yonyou.iuap.example.sanydelivery.web.controller;

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
import com.yonyou.iuap.example.sanydelivery.entity.DeliveryMapEntity;
import com.yonyou.iuap.example.sanydelivery.entity.SanyDelivery;
import com.yonyou.iuap.example.sanydelivery.entity.SanyDeliveryDetail;
import com.yonyou.iuap.example.sanydelivery.service.SanyDeliveryDetailService;
import com.yonyou.iuap.example.sanydelivery.service.SanyDeliveryService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_delivery")
public class SanyDeliveryController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(SanyDeliveryController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = SanyDelivery.class) SearchParams searchParams) {
		Page<SanyDelivery> page = SanyDeliveryService.selectAllByPage(pageRequest, searchParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return this.buildMapSuccess(map);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestParam("id") String id) {
		SanyDeliveryDetail SanyDeliveryDetail = SanyDeliveryDetailService.findById(id);
		return this.buildSuccess(SanyDeliveryDetail);
	}
	
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@ResponseBody
	public Object batchSave(@RequestBody DeliveryMapEntity DeliveryMapEntity,HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			List<SanyDelivery> listSanyDelivery = DeliveryMapEntity.getTable();
			List<SanyDeliveryDetail> listSanyDeliveryDetail = DeliveryMapEntity.getForm();
			
			if(listSanyDelivery != null && listSanyDelivery.size() > 0){
				//保存列表
				SanyDeliveryService.batchSave(listSanyDelivery);
			}
			if(listSanyDeliveryDetail != null && listSanyDeliveryDetail.size() > 0){
				//保存form
				SanyDeliveryDetailService.batchSave(listSanyDeliveryDetail);
			}
			
			jsonResp = this.buildSuccess(listSanyDeliveryDetail);
		}catch(Exception exp) {
			logger.error("工单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody SanyDeliveryDetail SanyDeliveryDetail, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			SanyDeliveryDetailService.save(SanyDeliveryDetail);
			jsonResp = this.buildSuccess(SanyDeliveryDetail);
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
			this.SanyDeliveryDetailService.delete(ids.get(i));
		}
		return super.buildSuccess(ids);
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(@RequestBody List<SanyDelivery> listSanyDelivery, HttpServletRequest request, HttpServletResponse response) throws Exception {
		for(int i=0; i<listSanyDelivery.size(); i++) {
			this.SanyDeliveryService.delete(listSanyDelivery.get(i));
		}
		return super.buildSuccess(listSanyDelivery);
	}
	
	/*************************************************************/
	@Autowired
	private SanyDeliveryDetailService SanyDeliveryDetailService;
	@Autowired
	private SanyDeliveryService SanyDeliveryService	;
	
}