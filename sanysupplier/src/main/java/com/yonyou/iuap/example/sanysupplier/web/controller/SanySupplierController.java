package com.yonyou.iuap.example.sanysupplier.web.controller;

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
import com.yonyou.iuap.baseservice.controller.GenericController;
import com.yonyou.iuap.example.sanysupplier.entity.SanySupplier;
import com.yonyou.iuap.example.sanysupplier.service.SanySupplierService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_supplier")
public class SanySupplierController extends GenericController<SanySupplier>{

//	private Logger logger = LoggerFactory.getLogger(SanySupplierController.class);

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	@ResponseBody
//	public Object list(PageRequest pageRequest,
//			@FrontModelExchange(modelType = SanySupplier.class) SearchParams searchParams) {
//		Page<SanySupplier> page = sanySupplierService.selectAllByPage(pageRequest, searchParams);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("data", page);
//		return this.buildMapSuccess(map);
//	}
//	
//	@RequestMapping(value = "/get", method = RequestMethod.POST)
//	@ResponseBody
//	public Object get(@RequestParam("id") String id) {
//		SanySupplier SanySupplier = sanySupplierService.findById(id);
//		return this.buildSuccess(SanySupplier);
//	}
//	
//	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
//	@ResponseBody
//	public Object batchSave(@RequestBody List<SanySupplier> listSanyRole, HttpServletRequest request, HttpServletResponse response) {
//		JsonResponse jsonResp;
//		try {
//			sanySupplierService.batchSave(listSanyRole);
//			jsonResp = this.buildSuccess(listSanyRole);
//		}catch(Exception exp) {
//			logger.error("工单信息保存出错!", exp);
//			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
//		}
//		return jsonResp;
//	}
//	
//	@RequestMapping(value = "/save", method = RequestMethod.POST)
//	@ResponseBody
//	public Object save(@RequestBody SanySupplier SanyRole, HttpServletRequest request, HttpServletResponse response) {
//		JsonResponse jsonResp;
//		try {
//			sanySupplierService.save(SanyRole);
//			jsonResp = this.buildSuccess(SanyRole);
//		}catch(Exception exp) {
//			logger.error("工单信息保存出错!", exp);
//			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
//		}
//		return jsonResp;
//	}
//	
//	@RequestMapping(value = "/deleteByIds", method = RequestMethod.POST)
//	@ResponseBody
//	public Object deleteByIds(@RequestBody List<String> ids, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		for(int i=0; i<ids.size(); i++) {
//			this.sanySupplierService.delete(ids.get(i));
//		}
//		return super.buildSuccess(ids);
//	}
//	
//	
//	@RequestMapping(value = "/delete", method = RequestMethod.POST)
//	@ResponseBody
//	public Object deleteBatch(@RequestBody List<SanySupplier> listSanyRole, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		for(int i=0; i<listSanyRole.size(); i++) {
//			this.sanySupplierService.delete(listSanyRole.get(i));
//		}
//		return super.buildSuccess(listSanyRole);
//	}
//	
	/*************************************************************/
	@Autowired
	private SanySupplierService sanySupplierService;

	public void setSanySupplierService(SanySupplierService sanySupplierService) {
		this.sanySupplierService = sanySupplierService;
		super.setService(sanySupplierService);
	}
	
	
	
}