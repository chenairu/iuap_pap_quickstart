
package com.yonyou.iuap.example.controller;

import com.yonyou.iuap.baseservice.controller.GenericAssoController;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.entity.ShowOffDetail;
import com.yonyou.iuap.example.entity.ShowOffSub;
import com.yonyou.iuap.example.service.ShowOffDetailService;
import com.yonyou.iuap.example.service.ShowOffService;
import com.yonyou.iuap.example.service.ShowOffSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/show_off")
public class ShowOffAssoController extends GenericAssoController<ShowOff> {

    private ShowOffService service;
    private ShowOffSubService showOffSubService;
    private ShowOffDetailService showOffDetailService;

    /**
     * 注入主表service
     */
    @Autowired
    public void setService(ShowOffService service) {
        this.service = service;
        super.setService( service);
    }

    /**
     * 注入子表ShowOffSubService
     */
    @Autowired
    public void setShowOffSubService(ShowOffSubService subService) {
        this.showOffSubService = subService;
        super.setSubService(ShowOffSub.class,subService);
        ;
    }


    /**
     * 注入子表ShowOffDetailService
     */
    @Autowired
    public void setShowOffDetailService(ShowOffDetailService subService) {
        this.showOffDetailService = subService;
        super.setSubService(ShowOffDetail.class,subService);
    }


}
