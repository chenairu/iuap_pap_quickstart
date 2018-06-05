package com.yonyou.iuap.example.asyncOrg.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.asyncOrg.entity.ExampleTree;
import com.yonyou.iuap.example.asyncOrg.service.ExampleTreeService;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/exampleTree")
public class ExampleTreeController extends BaseController {

    @Autowired
    private ExampleTreeService exampleTreeService;

    @RequestMapping(value = {"/list"},method={RequestMethod.GET})
    public Object list(HttpServletRequest request, PageRequest pageRequest, SearchParams searchParams){
        String node = request.getParameter("node");
        searchParams.getSearchMap().put("pid",node);
        Page<ExampleTree> list = exampleTreeService.selectAllByPage(pageRequest,searchParams);
        return buildSuccess(list);
    }
}
