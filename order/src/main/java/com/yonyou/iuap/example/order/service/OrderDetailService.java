package com.yonyou.iuap.example.order.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yonyou.iuap.example.order.dao.OrderDetailMapper;
import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class OrderDetailService implements IOrderDetailService{

	@Override
	public Page<OrderDetail> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
		Page<OrderDetail> pageResult = orderDetailMapper.selectAllByPage(pageRequest, searchParams).getPage();
		return pageResult;
	}
	
	@Override
	public OrderDetail saveEntity(OrderDetail orderdetail) {
		if(StringUtils.isEmpty(orderdetail.getId())) {
			return this.insert(orderdetail);
		}else {
			return this.update(orderdetail);
		}
	}
	
	public OrderDetail insert(OrderDetail orderdetail) {
		orderdetail.setId(UUID.randomUUID().toString());
		int count = orderDetailMapper.insert(orderdetail);
		if(count == 1) {
			return orderdetail;
		}else {
			throw new RuntimeException("新增保存订单明细出错，插入数据条数为:"+count);
		}
	}

	public OrderDetail update(OrderDetail orderdetail) {
		int count = orderDetailMapper.update(orderdetail);
		if(count == 1) {
			return orderdetail;
		}else {
			throw new RuntimeException("更新保存订单明细出错，待更新记录数不合法，记录数为:"+count);
		}
	}

	@Override
	public void delete4Order(String orderId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		orderDetailMapper.delete(params);
	}
	
	@Override
	public void delete4Order(List<String> orderIds) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderIds", orderIds);
		orderDetailMapper.delete(params);
	}
	
	@Override
	public void deleteByIds4Order(String orderId, List<String> detailIds4NoDel) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		params.put("detailIds4NoDel", detailIds4NoDel);
		orderDetailMapper.delete(params);
	}
	
	/******************************************************/
	@Autowired
	private OrderDetailMapper orderDetailMapper;

}