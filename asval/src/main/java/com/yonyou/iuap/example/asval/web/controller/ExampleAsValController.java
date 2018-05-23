package com.yonyou.iuap.example.asval.web.controller;


import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.asval.entity.ComboboxEntity;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/exampleAsVal")
public class ExampleAsValController extends BaseController {

    @Autowired
    private ExampleAsValService exampleAsValService;

    @RequestMapping(value={"/list"},method = {RequestMethod.GET})
    public @ResponseBody Object list(){
        List<ComboboxEntity> list = exampleAsValService.findProvince();
//        return JSONArray.toJSONString(list);
        return buildSuccess(list);
    }
}
