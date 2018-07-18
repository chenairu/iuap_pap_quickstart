package com.yonyou.iuap.example.controller;

import com.yonyou.iuap.baseservice.vo.GenericAssoVo;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.entity.ShowOffDetail;
import com.yonyou.iuap.example.service.ShowOffDetailService;
import com.yonyou.iuap.example.service.ShowOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/show_off")
public class ShowOffAssoController extends GenericAssoController<ShowOff> {

    private ShowOffService service;
    private ShowOffDetailService sanyOrderDetailService;


    /**
     * 注入主表service
     */
    @Autowired
    public void setSanyOrderService(ShowOffService service) {
        this.service = service;
        super.setService( service);
    }

    /**
     * 注入子表service
     */

    @Autowired
    public void setDetailService(ShowOffDetailService subService) {
        this.sanyOrderDetailService = subService;
        super.setSubService(ShowOffDetail.class,subService);
    }

    @Override
    public Object  saveAssoVo(@RequestBody GenericAssoVo<ShowOff> vo){
        return super.saveAssoVo(vo) ;
    }


}
