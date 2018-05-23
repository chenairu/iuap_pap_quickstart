package com.yonyou.iuap.example.reference.goods.dao;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.reference.goods.entity.Goods;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface GoodsMapper extends GenericMapper<Goods>{
	
}