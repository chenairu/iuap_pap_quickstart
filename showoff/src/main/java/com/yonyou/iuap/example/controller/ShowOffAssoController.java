
package com.yonyou.iuap.example.controller;

import com.yonyou.iuap.baseservice.controller.GenericAssoController;
import com.yonyou.iuap.baseservice.vo.GenericAssoVo;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.service.ShowOffService;
import com.yonyou.iuap.example.entity.ShowOffSub;
import com.yonyou.iuap.example.service.ShowOffSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/show_off")
public class ShowOffAssoController extends GenericAssoController<ShowOff> {

    private ShowOffService service;
    private ShowOffSubService showOffSubService;

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
    }


}
