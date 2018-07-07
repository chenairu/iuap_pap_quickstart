package com.yonyou.iuap.example.sanyorder.web.controller;

import com.yonyou.iuap.baseservice.attachment.controller.GenericAtController;
import com.yonyou.iuap.baseservice.attachment.service.GenericAtService;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderAttachmentService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderAttachmentController extends GenericAtController<SanyOrder> {

    SanyOrderAttachmentService service;
    @Autowired
    public void setService(SanyOrderAttachmentService service) {
        this.service = service;
        super.setAtService(service);
    }
    @Override
    public Object getListWithAttach(PageRequest pageRequest,
                       @FrontModelExchange(modelType = SanyOrder.class) SearchParams searchParams) {
        return super.getListWithAttach(pageRequest,searchParams);
    }
}
