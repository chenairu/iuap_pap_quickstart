package com.yonyou.iuap.example.orderinfo.dao;

import com.yonyou.iuap.example.orderinfo.entity.OrderInfo;
import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;


@MyBatisRepository
public interface OrderInfoMapper extends GenericExMapper<OrderInfo> {

}