package com.yonyou.iuap.example.billcode.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
import com.yonyou.iuap.example.billcode.service.ExampleCustomerService;
import com.yonyou.iuap.example.billcode.validator.ExampleCustomerValidator;
import com.yonyou.iuap.example.supervise.utils.CommonUtils;
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
@RequestMapping("/customer")
public class ExampleCustomerController extends BaseController {

    @Autowired
    private ExampleCustomerService exampleCustomerService;

    @Autowired
    private ExampleCustomerValidator validator;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public @ResponseBody
    Object page(PageRequest pageRequest, SearchParams searchParams){
        CommonUtils.decode(searchParams);
        Page<ExampleCustomer> tmpdata = exampleCustomerService.selectAllByPage(pageRequest,searchParams);
        return buildSuccess(tmpdata);
    }

    @RequestMapping(value = "/save" ,method=RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExampleCustomer> list){
        validator.valid(list);

    }

}
