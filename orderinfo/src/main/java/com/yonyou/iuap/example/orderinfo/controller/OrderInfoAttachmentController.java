package com.yonyou.iuap.example.orderinfo.controller;

import com.yonyou.iuap.baseservice.attachment.controller.GenericAtController;
import com.yonyou.iuap.example.orderinfo.entity.OrderInfo;
import com.yonyou.iuap.example.orderinfo.service.OrderInfoAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/order_info")
public class OrderInfoAttachmentController extends GenericAtController<OrderInfo>{
    
    private Logger logger = LoggerFactory.getLogger(OrderInfoController.class);


    private OrderInfoAttachmentService service;
    @Autowired
    public void setService(OrderInfoAttachmentService service) {
        this.service = service;
        super.setAtService(service);
    }
    @Override
    public Object getListWithAttach(PageRequest pageRequest,
                       @FrontModelExchange(modelType = OrderInfo.class) SearchParams searchParams) throws Exception {
        return super.getListWithAttach(pageRequest,searchParams);
    }
}
