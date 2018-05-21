package com.yonyou.iuap.example.order.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.order.entity.OrderBill;
import com.yonyou.iuap.mvc.type.SearchParams;

public interface IOrderBillService {
	
	public Page<OrderBill> selectAllByPage(PageRequest pageRequest, SearchParams searchParams);
	
	public OrderBill saveEntity(OrderBill order);
	
	public int delete(List<OrderBill> listOrder);

}