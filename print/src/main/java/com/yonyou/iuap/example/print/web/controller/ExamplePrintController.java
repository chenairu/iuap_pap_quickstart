package com.yonyou.iuap.example.print.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.common.BaseEntityUtils;
import com.yonyou.iuap.example.print.entity.ExamplePrint;
import com.yonyou.iuap.example.print.service.ExamplePrintService;

import com.yonyou.iuap.mvc.type.SearchParams;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * Title: CardTableController
 * </p>
 * <p>
 * Description: 卡片表示例的controller层
 * </p>
 */
@RestController
@RequestMapping(value = "/examplePrint")
public class ExamplePrintController extends BaseController {
	@Autowired
	private ExamplePrintService service;

    /**
     * 查询分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
        Page<ExamplePrint> data = service.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(data);
    }

    /**
     * 保存数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<ExamplePrint> list) {
    	service.save(list);
        return buildSuccess();
    }


    /**
     * 删除数据
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public @ResponseBody Object del(@RequestBody List<ExamplePrint> list) {
    	service.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    
    
    
	/**
	 * 打印时获取数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dataForPrint", method = RequestMethod.POST)
	@ResponseBody
	public Object getDataForPrint(HttpServletRequest request) {
		String params = request.getParameter("params");
		JSONObject jsonObj = JSON.parseObject(params);
		String id = (String) jsonObj.get("id");
		ExamplePrint vo = service.queryByPK(id);
		JSONArray mainDataJson = new JSONArray();// 主实体数据
		List<String> lsAttr = vo.getAllAttributeNames();
		JSONObject mainData = new JSONObject();
		for (String attr : lsAttr) {
			if (BaseEntityUtils.lsAttrExclude.contains(attr)) {
				continue;
			}
			System.out.println("attr" + attr + "------" + vo.getAttribute(attr));
			mainData.put(attr, vo.getAttribute(attr));
		}
		mainDataJson.add(mainData);// 主表只有一行
		JSONObject boAttr = new JSONObject();
		boAttr.put("example_print", mainDataJson);
		System.out.println(boAttr.toString());
		return boAttr.toString();
	}

    
    
    
}
