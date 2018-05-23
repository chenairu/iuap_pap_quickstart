package com.yonyou.iuap.example.dictionary.web.controller;

import java.util.List;

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

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.common.utils.CommonUtils;
import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;
import com.yonyou.iuap.example.dictionary.validator.DictionaryValidator;
import com.yonyou.iuap.mvc.type.SearchParams;

@Controller
@RequestMapping(value = "/example_dictionary")
public class DictionaryController extends BaseController {
	
    public static Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;
    
    @Autowired
    private DictionaryValidator validtor;

    /**
     * 获取档案列表
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
    	CommonUtils.decode(searchParams);
        Page<Dictionary> tmpdata = dictionaryService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(tmpdata);
    }


    /**
     * 保存档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Dictionary> list) {
        validtor.valid(list);
    	dictionaryService.save(list);
        return buildSuccess();
    }

    /**
     * 删除档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Object delete(@RequestBody List<Dictionary> list) {
    	for(int i=0; i<list.size(); i++) {
    		System.out.println("...........................删除数据："+list.get(i).getId());
    	}
    	dictionaryService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
}