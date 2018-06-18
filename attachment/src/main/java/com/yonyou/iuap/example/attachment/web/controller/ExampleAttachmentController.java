package com.yonyou.iuap.example.attachment.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.attachment.entity.ExampleAttachment;
import com.yonyou.iuap.example.attachment.service.ExampleAttachmentService;
import com.yonyou.iuap.example.common.utils.CommonUtils;
import com.yonyou.iuap.mvc.type.SearchParams;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * <p>
 * Title: CardTableController
 * </p>
 * <p>
 * Description: 卡片表示例的controller层
 * </p>
 */
@RestController
@RequestMapping(value = "/ExampleAttachment")
public class ExampleAttachmentController extends BaseController {
	@Autowired
	private ExampleAttachmentService service;

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
    	CommonUtils.decode(searchParams);
        Page<ExampleAttachment> data = service.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExampleAttachment> list) {
    	service.save(list);
        return buildSuccess();
    }


    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<ExampleAttachment> list) {
    	service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public String getACode() {
    	return UUID.randomUUID().toString();
    }
    	
  
    
}
