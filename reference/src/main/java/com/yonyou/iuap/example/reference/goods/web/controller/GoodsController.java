package com.yonyou.iuap.example.reference.goods.web.controller;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.common.utils.CommonUtils;
import com.yonyou.iuap.example.reference.goods.entity.Goods;
import com.yonyou.iuap.example.reference.goods.service.GoodsService;
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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/example_goods")
public class GoodsController extends BaseController {
	
    public static Logger logger = LoggerFactory.getLogger(GoodsController.class);
    
    /**
     * 分页查询商品列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
    	CommonUtils.decode(searchParams);
        Page<Goods> tmpdata = goodsService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(tmpdata);
    }

    /**
     * 保存商品
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public @ResponseBody Object get(String id) {
    	Goods data = goodsService.findById(id);
        return buildSuccess(data);
    }

    /**
     * 保存商品
     * 
     * @param entity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody Goods entity) {
    	goodsService.save(entity);
        return buildSuccess();
    }

    /**
     * 删除商品
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Object delete(@RequestBody List<Goods> list) {
    	for(int i=0; i<list.size(); i++) {
    		System.out.println("...........................删除数据："+list.get(i).getId());
    	}
    	List<String> ids = new ArrayList<String>();
    	for(Goods goods: list) {
    		ids.add(goods.getId());
    	}
    	goodsService.delete(ids);
        return buildSuccess();
    }

    /**********************************************************************/
    @Autowired
    private GoodsService goodsService;
    
}