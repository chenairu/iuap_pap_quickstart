package com.yonyou.iuap.example.sanyorder.web.controller;

import com.yonyou.iuap.baseservice.controller.GenericExController;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrderContract;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderContractService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sany_order_contract")
public class SanyOrderContractController extends GenericExController<SanyOrderContract> {
    private Logger logger = LoggerFactory.getLogger(SanyOrderController.class);

    private SanyOrderContractService service;

    @Autowired
    public void setSanyOrderContractService(SanyOrderContractService service) {
        this.service = service;
        super.setService(service);
    }

    @Override
    public Object list(PageRequest pageRequest,
                       @FrontModelExchange(modelType = SanyOrderContract.class) SearchParams searchParams) {
        return super.list(pageRequest,searchParams);
    }
}
