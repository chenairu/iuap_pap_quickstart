package com.yonyou.iuap.example.customer.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.customer.entity.ExampleCustomer;
import com.yonyou.iuap.example.customer.service.ExampleCustomerService;
import com.yonyou.iuap.example.customer.validator.ExampleCustomerValidator;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/exampleCustomer")
public class ExampleCustomerController extends BaseController {

    @Autowired
    private ExampleCustomerService exampleCustomerService;

    @Autowired
    private ExampleCustomerValidator validator;



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public @ResponseBody
    Object page(PageRequest pageRequest, SearchParams searchParams){
        Page<ExampleCustomer> tmpdata = exampleCustomerService.selectAllByPage(pageRequest,searchParams);
        return buildSuccess(tmpdata);
    }

    @RequestMapping(value = "/save" ,method=RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExampleCustomer> list){
        validator.valid(list);
        exampleCustomerService.save(list);
        return buildSuccess();
    }

    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<ExampleCustomer> list){
        exampleCustomerService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }

}
