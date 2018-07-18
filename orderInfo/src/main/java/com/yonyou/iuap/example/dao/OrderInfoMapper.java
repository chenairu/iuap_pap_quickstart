package com.yonyou.iuap.example.dao;

import com.yonyou.iuap.example.entity.OrderInfo;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;


@MyBatisRepository
public interface OrderInfoMapper extends GenericExMapper<OrderInfo> {

}