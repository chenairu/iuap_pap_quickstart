package com.yonyou.iuap.example.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrderDetailMapper{

	public PageResult<OrderDetail> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") SearchParams searchParams);
	
	public List<OrderDetail> queryList(@Param("condition") Map<String,Object> params);
	
	public int insert(OrderDetail entity);

	public int update(OrderDetail entity);
	
	public int delete(@Param("condition")Map<String,Object> params);
	
}