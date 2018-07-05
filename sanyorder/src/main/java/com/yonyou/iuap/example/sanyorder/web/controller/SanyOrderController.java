package com.yonyou.iuap.example.sanyorder.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.example.sanyorder.entity.AttachmentEntity;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderAttachmentService;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderController extends GenericBpmController<SanyOrder> {

	private Logger logger = LoggerFactory.getLogger(SanyOrderController.class);

	private SanyOrderService sanyOrderService;
	
	@Autowired
	private SanyOrderAttachmentService SanyOrderAttachmentService;
	
	@RequestMapping(value = "/saveToFile", method = RequestMethod.POST)
	public Object setFileBill(@RequestBody SanyOrder entity){
		SanyOrder sanyOrder = sanyOrderService.save(entity);
		String id = sanyOrder.getId();
		String name = sanyOrder.getOrderName();
		List<AttachmentEntity> attachments = entity.getAttachment();
		for(AttachmentEntity att:attachments){
			att.setRefId(id);
			att.setRefName(name);
			SanyOrderAttachmentService.save(att);
		}
		return this.buildSuccess(entity);
	}
	
	@RequestMapping(value = "/getToFile", method = RequestMethod.GET)
	public Object getFileBill(PageRequest pageRequest, SearchParams searchParams){
		SanyOrder sanyOrder = (SanyOrder) super.get(pageRequest,searchParams);
		
		List<AttachmentEntity> atts = SanyOrderAttachmentService.getRefId(sanyOrder.getId());
		sanyOrder.setAttachment(atts);
		return this.buildSuccess(sanyOrder);
	}
	
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