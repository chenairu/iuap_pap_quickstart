package com.yonyou.iuap.example.orderinfo.controller;

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
import com.yonyou.iuap.example.orderinfo.entity.OrderInfo;
import com.yonyou.iuap.example.orderinfo.service.OrderInfoService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value="/order_info")
public class OrderInfoController extends GenericBpmController<OrderInfo>{

	private Logger logger = LoggerFactory.getLogger(OrderInfoController.class);

	private OrderInfoService OrderInfoService;
	
	@Autowired
    public void setOrderInfoService(OrderInfoService OrderInfoService) {
        this.OrderInfoService = OrderInfoService;
        super.setService(OrderInfoService);
    }

	@Override
	public Object list(PageRequest pageRequest,
					   @FrontModelExchange(modelType = OrderInfo.class) SearchParams searchParams) {
		return super.list(pageRequest,searchParams);
	}

}