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
public class OrderDetailService{

	public Page<OrderDetail> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
		Page<OrderDetail> pageResult = orderDetailMapper.selectAllByPage(pageRequest, searchParams).getPage();
		return pageResult;
	}
	
	public List<OrderDetail> query4OrderId(String orderId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		return orderDetailMapper.queryList(params);
	}
	
	public OrderDetail saveEntity(OrderDetail orderDetail) {
		if(StringUtils.isEmpty(orderDetail.getId())) {
			return this.insert(orderDetail);
		}else {
			return this.update(orderDetail);
		}
	}
	
	public OrderDetail insert(OrderDetail orderDetail) {
		orderDetail.setId(UUID.randomUUID().toString());
		int count = orderDetailMapper.insert(orderDetail);
		if(count == 1) {
			return orderDetail;
		}else {
			throw new RuntimeException("新增保存订单明细出错，插入数据条数为:"+count);
		}
	}

	public OrderDetail update(OrderDetail orderDetail) {
		orderDetailMapper.update(orderDetail);
		return orderDetail;
	}
	
	public void delete(List<String> ids) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ids", ids);
		orderDetailMapper.delete(params);
	}

	public void delete4Order(String orderId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		orderDetailMapper.delete(params);
	}
	
	public void delete4Order(List<String> orderIds) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderIds", orderIds);
		orderDetailMapper.delete(params);
	}
	
	/**
	 * 删除订单｛orderId｝下不在｛detailIds4NoDel｝范围内的明细
	 * @param orderId
	 * @param detailIds4NoDel
	 */
	public void deleteByIds4Order(String orderId, List<String> detailIds4NoDel) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderId", orderId);
		params.put("detailIds4NoDel", detailIds4NoDel);
		int count = orderDetailMapper.delete(params);
		System.out.println(count);
	}
	
	/******************************************************/
	@Autowired
	private OrderDetailMapper orderDetailMapper;

}