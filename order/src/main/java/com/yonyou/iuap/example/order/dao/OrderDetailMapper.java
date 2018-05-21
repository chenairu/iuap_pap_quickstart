package com.yonyou.iuap.example.order.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrderDetailMapper{

	PageResult<OrderDetail> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	int insert(OrderDetail entity);

	int update(OrderDetail entity);
	
	int delete(@Param("condition")Map<String,Object> params);
	
}