package com.yonyou.iuap.example.sanyorder.web.controller;

import com.yonyou.iuap.baseservice.controller.GenericAssoController;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrderContract;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderContractService;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderAssoController extends GenericAssoController<SanyOrder> {

    private SanyOrderService sanyOrderService;
    private SanyOrderContractService sanyOrderContractService;


    /**
     * 注入主表service
     * @param sanyOrderService
     */
    @Autowired
    public void setSanyOrderService(SanyOrderService sanyOrderService) {
        this.sanyOrderService = sanyOrderService;
        super.setService(sanyOrderService);
    }

    /**
     * 注入子表service
     * @param sanyOrderContractService
     */
    @Autowired
    public void setSanyOrderService(SanyOrderContractService sanyOrderContractService) {
        this.sanyOrderContractService = sanyOrderContractService;
        super.setSubService(SanyOrderContract.class,sanyOrderContractService);
    }


}
