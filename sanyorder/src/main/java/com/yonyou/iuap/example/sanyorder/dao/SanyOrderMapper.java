package com.yonyou.iuap.example.sanyorder.dao;

import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface SanyOrderMapper extends GenericExMapper<SanyOrder>  {

	
}
