package com.yonyou.iuap.example.sysRefer.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.sysRefer.entity.ExampleEquip;
import com.yonyou.iuap.example.sysRefer.service.ExampleEquipService;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.yonyou.iuap.example.sysRefer.web.controller
 *
 * @author guoxh
 * @date 2018/5/29 15:39
 * @description
 */
@RestController
@RequestMapping("/exampleEquip")
public class ExampleEquipController extends BaseController {

    @Autowired
    private ExampleEquipService exampleEquipService;

    @RequestMapping(value={"/list"},method = RequestMethod.GET)
    public Object list(PageRequest pageRequest, SearchParams searchParams){
        Page<ExampleEquip> list = exampleEquipService.selectAllByPage(pageRequest,searchParams);
        return buildSuccess(list);
    }

    @RequestMapping(value={"/save"},method = RequestMethod.POST)
    public Object insert(@RequestBody List<ExampleEquip> exampleEquips){
        exampleEquipService.save(exampleEquips);
        return buildSuccess();
    }


    @RequestMapping(value={"/delete"},method = RequestMethod.POST)
    public Object delete(@RequestBody List<ExampleEquip> exampleEquips){
        exampleEquipService.delete(exampleEquips);
        return buildSuccess();
    }

}
