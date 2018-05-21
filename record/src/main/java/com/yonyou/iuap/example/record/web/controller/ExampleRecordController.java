package com.yonyou.iuap.example.record.web.controller;

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
import com.yonyou.iuap.example.record.entity.ExampleRecord;
import com.yonyou.iuap.example.record.service.ExampleRecordService;
import com.yonyou.iuap.example.record.validator.ExampleRecordValidator;
import com.yonyou.iuap.example.supervise.utils.CommonUtils;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>
 * Title: InstitController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author xiadl
 */

@Controller
@RequestMapping(value = "/exampleRecord")
public class ExampleRecordController extends BaseController {
    public static Logger logger = LoggerFactory.getLogger(ExampleRecordController.class);

    @Autowired
    private ExampleRecordService exampleRecordService;
    
    @Autowired
    private ExampleRecordValidator validtor;

    /**
     * 获取档案列表
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
    	CommonUtils.decode(searchParams);
        Page<ExampleRecord> tmpdata = exampleRecordService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(tmpdata);
    }


    /**
     * 保存档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExampleRecord> list) {
        validtor.valid(list);
    	exampleRecordService.save(list);
        return buildSuccess();
    }

    /**
     * 删除档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Object delete(@RequestBody List<ExampleRecord> list) {
    	for(int i=0; i<list.size(); i++) {
    		System.out.println("...........................刪除數據："+list.get(i).getId());
    	}
    	exampleRecordService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
}