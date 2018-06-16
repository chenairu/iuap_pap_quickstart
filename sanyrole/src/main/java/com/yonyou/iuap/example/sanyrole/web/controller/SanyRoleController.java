package com.yonyou.iuap.example.sanyrole.web.controller;

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
import com.yonyou.iuap.example.sanyrole.entity.SanyRole;
import com.yonyou.iuap.example.sanyrole.service.SanyRoleService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_role")
public class SanyRoleController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(SanyRoleController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = SanyRole.class) SearchParams searchParams) {
		Page<SanyRole> page = sanyRoleService.selectAllByPage(pageRequest, searchParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return this.buildMapSuccess(map);
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.POST)
	@ResponseBody
	public Object get(@RequestParam("id") String id) {
		SanyRole SanyRole = sanyRoleService.findById(id);
		return this.buildSuccess(SanyRole);
	}
	
	@RequestMapping(value = "/batchSave", method = RequestMethod.POST)
	@ResponseBody
	public Object batchSave(@RequestBody List<SanyRole> listSanyRole, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			sanyRoleService.batchSave(listSanyRole);
			jsonResp = this.buildSuccess(listSanyRole);
		}catch(Exception exp) {
			logger.error("工单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object save(@RequestBody SanyRole SanyRole, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			sanyRoleService.save(SanyRole);
			jsonResp = this.buildSuccess(SanyRole);
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
			this.sanyRoleService.delete(ids.get(i));
		}
		return super.buildSuccess(ids);
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(@RequestBody List<SanyRole> listSanyRole, HttpServletRequest request, HttpServletResponse response) throws Exception {
		for(int i=0; i<listSanyRole.size(); i++) {
			this.sanyRoleService.delete(listSanyRole.get(i));
		}
		return super.buildSuccess(listSanyRole);
	}
	
	/*************************************************************/
	@Autowired
	private SanyRoleService sanyRoleService;
	
}