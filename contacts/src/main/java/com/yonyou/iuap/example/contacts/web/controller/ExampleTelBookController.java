package com.yonyou.iuap.example.contacts.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.contacts.entity.ExampleTelBook;
import com.yonyou.iuap.example.contacts.service.ExampleTelBookService;
import com.yonyou.iuap.example.contacts.validator.ExampleTelBookValidator;
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
public class ExampleTelBookController extends BaseController {
	
    private static Logger logger = LoggerFactory.getLogger(ExampleTelBookController.class);

    @Autowired
    private ExampleTelBookService telBookservice;

    @Autowired
    private ExampleTelBookValidator validtor;

    /**
     *
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams ,String institid) {
        Page<ExampleTelBook> tmpdata = telBookservice.selectAllByPage(pageRequest, searchParams,institid);
        Page<ExampleTelBook> data = telBookservice.selectInstit(tmpdata);
        return buildSuccess(data);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExampleTelBook> list) {
        validtor.valid(list);
        telBookservice.save(list);
        return buildSuccess();
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<ExampleTelBook> list) {
        telBookservice.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }

    /** 查询枚举值 */
//    @RequestMapping(value = "/loadEnum", method = RequestMethod.GET)
//    @ResponseBody
//    public Object loadEnum() throws Exception {
//        Map<String, List<EnumVo>> map = EnumUtils.loadEnum(Sex.class);
//        return super.buildMapSuccess(map);
//    }

}
