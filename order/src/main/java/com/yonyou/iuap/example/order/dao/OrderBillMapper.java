package com.yonyou.iuap.example.order.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.order.entity.OrderBill;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrderBillMapper extends GenericMapper<OrderBill>{

//	public PageResult<OrderBill> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	public PageResult<OrderBill> selectAllByPage(@Param("page") PageRequest pageRequest,@Param("condition") Map<String, Object> searchParams);
	
	public List<OrderBill> queryList(@Param("condition")Map<String,Object> params);
	
	public int insert(OrderBill entity);

	public int update(OrderBill entity);

	public int delete(@Param("condition")Map<String,Object> params);
	
}