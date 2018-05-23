package com.yonyou.iuap.example.contacts.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.contacts.entity.Contacts;
import com.yonyou.iuap.example.contacts.service.ContactsService;
import com.yonyou.iuap.example.contacts.validator.ContactsValidator;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/telBook")
public class ContactsController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(ContactsController.class);

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private ContactsValidator validtor;

    /**
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams ,String institid) {
        Page<Contacts> tmpdata = contactsService.selectAllByPage(pageRequest, searchParams,institid);
        Page<Contacts> data = contactsService.selectInstit(tmpdata);
        return buildSuccess(data);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Contacts> list) {
        validtor.valid(list);
        contactsService.save(list);
        return buildSuccess();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<Contacts> list) {
        contactsService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }

}