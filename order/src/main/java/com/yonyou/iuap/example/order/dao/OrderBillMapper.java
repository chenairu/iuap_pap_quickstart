package com.yonyou.iuap.example.order.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.order.entity.OrderBill;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrderBillMapper{

	PageResult<OrderBill> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	int insert(OrderBill entity);

	int update(OrderBill entity);

	int delete(@Param("condition")Map<String,Object> params);
	
}