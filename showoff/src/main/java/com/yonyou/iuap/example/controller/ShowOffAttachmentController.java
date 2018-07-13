package com.yonyou.iuap.example.controller;

import com.yonyou.iuap.baseservice.attachment.controller.GenericAtController;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.service.ShowOffAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/show_off")
public class ShowOffAttachmentController extends GenericAtController<ShowOff>{
    
    private Logger logger = LoggerFactory.getLogger(ShowOffController.class);


    private ShowOffAttachmentService service;
    @Autowired
    public void setService(ShowOffAttachmentService service) {
        this.service = service;
        super.setAtService(service);
    }
    @Override
    public Object getListWithAttach(PageRequest pageRequest,
                       @FrontModelExchange(modelType = ShowOff.class) SearchParams searchParams) throws Exception {
        return super.getListWithAttach(pageRequest,searchParams);
    }
}
