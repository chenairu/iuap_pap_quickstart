package com.yonyou.iuap.example.goods.directory.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.common.utils.CommonUtils;
import com.yonyou.iuap.example.goods.directory.entity.GoodsDirectory;
import com.yonyou.iuap.example.goods.directory.service.GoodsDirectoryService;
import com.yonyou.iuap.mvc.type.SearchParams;

import yonyou.bpm.rest.utils.StringUtils;

@Controller
@RequestMapping(value = "/goodsDirectory")
@SuppressWarnings("all")
public class GoodsDirectoryController extends BaseController {

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(PageRequest pageRequest) {
    	List<GoodsDirectory> listData = goodsDirectoryService.findAll();
    	return this.buildSuccess(this.buildZTree(listData, true));
    }
    
    @RequestMapping(value = "/children")
    @ResponseBody
    public Object children(String id, Integer level) {
    	if(StringUtils.isBlank(id)) {
    		id = "root";
    	}
    	List<GoodsDirectory> listData = goodsDirectoryService.queryList("parentId", id);
    	return this.buildZTree(listData, true);
    }
    
    @RequestMapping(value = "/searchTree")
    @ResponseBody
    public Object searchTree(String searchParam) {
    	List<GoodsDirectory> listData = goodsDirectoryService.searchTree(searchParam);
    	return this.buildSuccess(this.buildZTree(listData, false));
    }

    
    private List<JSONObject> buildZTree(List<GoodsDirectory> listData, boolean setParent){
    	List<JSONObject> listJson = new ArrayList<JSONObject>();
    	for(GoodsDirectory entity: listData) {
    		listJson.add(this.convert(entity, setParent));
    	}
    	return listJson;
    }
    
    private JSONObject convert(GoodsDirectory entity, boolean setParent) {
    	JSONObject json = new JSONObject();
    	json.put("id", entity.getId());
    	json.put("code", entity.getCode());
    	json.put("name", entity.getName());
    	json.put("pId", entity.getParentId());
    	if(setParent) {
        	json.put("isParent", true);		//根据实际情况设置
    	}
    	return json;
    }
	
	/****************************************/
	private GoodsDirectoryService goodsDirectoryService;

	@Autowired
	public void setGoodsDirectoryService(GoodsDirectoryService goodsDirectoryService) {
		this.goodsDirectoryService = goodsDirectoryService;
	}

}