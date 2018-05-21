package com.yonyou.iuap.example.supervise.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.example.supervise.service.Ygdemo_yw_subService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/ygdemo_yw_sub")
public class Ygdemo_yw_subController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(Ygdemo_yw_subController.class);

	@Autowired
	private Ygdemo_yw_subService service;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = Ygdemo_yw_info.class) SearchParams searchParams) {
		logger.debug("execute data search.");
		
		Page<Ygdemo_yw_sub> page = service.selectAllByPage(pageRequest, searchParams);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return super.buildMapSuccess(map);
	}

	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestBody Ygdemo_yw_sub child, HttpServletRequest request) {
		service.deleteEntity(child);
		
		return super.buildSuccess(child);
	}

}
