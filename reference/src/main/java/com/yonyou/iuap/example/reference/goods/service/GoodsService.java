package com.yonyou.iuap.example.reference.goods.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.reference.goods.dao.GoodsMapper;
import com.yonyou.iuap.example.reference.goods.entity.Goods;

@Service
public class GoodsService extends GenericService<Goods>{


    public void delete(List<String> ids) {
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("ids", ids);
    	goodsMapper.delete(params);
    }
    
    
    /***********************************************************/
    private GoodsMapper goodsMapper;

    @Autowired
	public void setGoodsMapper(GoodsMapper goodsMapper) {
    	super.ibatisMapper = goodsMapper;
		this.goodsMapper = goodsMapper;
	}
    
    

}