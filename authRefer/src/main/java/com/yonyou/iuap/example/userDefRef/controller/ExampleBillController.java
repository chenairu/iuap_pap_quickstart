package com.yonyou.iuap.example.userDefRef.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.userDefRef.entity.ExampleBill;
import com.yonyou.iuap.example.userDefRef.service.ExampleBillService;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.yonyou.iuap.example.userDefRef.controller
 *
 * @author guoxh
 * @date 2018/5/30 10:19
 * @description
 */

@RestController
@RequestMapping("/exampleBill")
public class ExampleBillController extends BaseController {

    @Autowired
    private ExampleBillService exampleBillService;

    @RequestMapping(value = {"/list"},method={RequestMethod.GET})
    public Object list(PageRequest pageRequest, SearchParams searchParams){
        Page<ExampleBill> tmpdata = exampleBillService.selectAllByPage(pageRequest,searchParams);
        return buildSuccess(tmpdata);
    }

    @RequestMapping(value = {"/insert"},method={RequestMethod.POST})
    public Object insert(){

        return buildSuccess();
    }
    @RequestMapping(value = {"/update"},method={RequestMethod.PUT})
    public Object update(){

        return buildSuccess();
    }
    @RequestMapping(value = {"/delete"},method={RequestMethod.DELETE})
    public Object delete(){

        return buildSuccess();
    }
}
