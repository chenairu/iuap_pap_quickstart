package com.yonyou.iuap.example.sanyorder.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderController extends GenericBpmController<SanyOrder> {

	private Logger logger = LoggerFactory.getLogger(SanyOrderController.class);

	private SanyOrderService sanyOrderService;
	
	@Autowired
	public void setSanyOrderService(SanyOrderService sanyOrderService) {
		this.sanyOrderService = sanyOrderService;
		super.setService(sanyOrderService);
	}

	@Override
	public Object list(PageRequest pageRequest,
					   @FrontModelExchange(modelType = SanyOrder.class) SearchParams searchParams) {
		return super.list(pageRequest,searchParams);
	}

}