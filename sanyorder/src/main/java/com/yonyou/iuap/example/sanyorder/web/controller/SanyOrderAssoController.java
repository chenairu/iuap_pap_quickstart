package com.yonyou.iuap.example.sanyorder.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrderAssoVo;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderContractService;
import com.yonyou.iuap.example.sanyorder.service.SanyOrderService;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/sany_order")
public class SanyOrderAssoController extends BaseController {

    @Autowired
    SanyOrderService sanyOrderService;
    @Autowired
    SanyOrderContractService sanyOrderContractService;

    @RequestMapping(value = "/getAssoVo")
    @ResponseBody
    public Object  getAssoVo(PageRequest pageRequest,
            SearchParams searchParams){
        String id = MapUtils.getString(searchParams.getSearchMap(), "id");
        SanyOrderAssoVo vo = new SanyOrderAssoVo();
        if (null==id){ return buildSuccess();}
        vo.setSanyOrder( sanyOrderService.findById(id)  );//刷数据,关键是TS
        List contractList= sanyOrderContractService.queryList("orderId",id);
        vo.setSanyOrderContractList(contractList);
        return this.buildSuccess(vo) ;
    }

}
