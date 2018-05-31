package com.yonyou.iuap.example.contacts.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.example.contacts.service.OrganizationService;
import com.yonyou.iuap.example.contacts.validator.OrganizationValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * Title: InstitController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zhukai
 */

@Controller
@RequestMapping(value = "/instit")
public class OrganizationController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Autowired
    private OrganizationService oranizationService;

    @Autowired
    private OrganizationValidator validtor;

    /**
     * 获取机构列表
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object loadTree() {
        List<Organization> data = oranizationService.findAll();
        return buildSuccess(data);
    }

    /**
     * 根据父节点获取子节点
     * 
     * @param pid
     * @return
     */
    @RequestMapping(value = "/findByFid", method = RequestMethod.GET)
    public @ResponseBody Object findByFid(@RequestBody String pid) {
        List<Organization> data = oranizationService.findByFid(pid);
        return buildSuccess(data);
    }

    /**
     * 保存机构
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Organization> list) {
        validtor.valid(list);
        oranizationService.save(list);
        return buildSuccess();
    }

    /**
     * 删除机构
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Organization> list) {
        oranizationService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
}