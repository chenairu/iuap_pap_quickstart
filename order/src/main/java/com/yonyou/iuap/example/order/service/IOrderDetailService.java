package com.yonyou.iuap.example.order.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.mvc.type.SearchParams;

public interface IOrderDetailService {

	public Page<OrderDetail> selectAllByPage(PageRequest pageRequest, SearchParams searchParams);

	public OrderDetail saveEntity(OrderDetail orderdetail);
	
	public void delete4Order(String orderId);
	
	public void delete4Order(List<String> orderIds);
	
	public void deleteByIds4Order(String orderId, List<String> detailIds4NoDel);

}