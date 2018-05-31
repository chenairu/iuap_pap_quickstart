package com.yonyou.iuap.example.order.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.order.dao.OrderBillMapper;
import com.yonyou.iuap.example.order.entity.OrderBill;
import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class OrderBillService extends GenericService<OrderBill>{

	//根据传递的参数，进行分页查询
	public Page<OrderBill> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {		
		Page<OrderBill> pageResult = orderBillMapper.selectAllByPage(pageRequest, searchParams).getPage();
		return pageResult;
	}
	
	public OrderBill findById(String orderId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", orderId);
		List<OrderBill> listOrders = orderBillMapper.queryList(params);
		if(listOrders.size()==1) {
			return listOrders.get(0);
		}else {
			throw new RuntimeException("检索数据出错：id="+orderId);
		}
	}

	//保存订单（事务控制）
	@Transactional
	public OrderBill saveEntity(OrderBill order) {
		if(StringUtils.isEmpty(order.getId())) {
			return this.save4Insert(order);					//新增保存订单
		}else {
			return this.save4Update(order);					//更新保存订单
		}
	}
	
	//插入新订单
	private OrderBill save4Insert(OrderBill order) {
		//订单信息
		order.setId(UUID.randomUUID().toString());
		order.setDr(0);														//设置未删除标志
		Date now = new Date();
		if(StringUtils.isEmpty(order.getCreateUser())) {
			order.setCreateUser(InvocationInfoProxy.getUserid());			//设置创建人：当前用户
		}
		if(order.getCreateTime() == null) {
			order.setCreateTime(now);										//设置创建时间
		}
		if(StringUtils.isEmpty(order.getLastModifyUser())) {
			order.setLastModifyUser(InvocationInfoProxy.getUserid());
		}
		if(order.getLastModified()==null) {
			order.setLastModified(now);
		}
		orderBillMapper.insert(order);											//插入数据库
		
		//插入订单明细
		if(!CollectionUtils.isEmpty(order.getOrderDetails())) {
			List<OrderDetail> listDetail = order.getOrderDetails();
			for(int i=0; i<listDetail.size(); i++) {
				listDetail.get(i).setOrderId(order.getId());
				orderDetailService.saveEntity(listDetail.get(i));
			}
		}
		
		//订单明细
		return order;
	}
	
	//更新新订单
	private OrderBill save4Update(OrderBill order) {
		//订单信息
		order.setLastModifyUser(InvocationInfoProxy.getUserid());
		order.setLastModified(new Date());
		orderBillMapper.update(order);
		
		//保存订单明细
		if(!CollectionUtils.isEmpty(order.getOrderDetails())) {
			List<OrderDetail> listDetail = order.getOrderDetails();
			List<String> listSubIds = new ArrayList<String>();
			for(int i=0; i<listDetail.size(); i++) {
				if(!StringUtils.isEmpty(listDetail.get(i).getId())) {
					listSubIds.add(listDetail.get(i).getId());
				}
			}
			
			orderDetailService.deleteByIds4Order(order.getId(), listSubIds);
			for(int i=0; i<listDetail.size(); i++) {
				listDetail.get(i).setOrderId(order.getId());
				orderDetailService.saveEntity(listDetail.get(i));
			}
		}else {
			orderDetailService.delete4Order(order.getId());
		}
		
		//订单明细
		return order;
	}
	
	public int delete(List<String> orderIds) {
		orderDetailService.delete4Order(orderIds);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orderIds", orderIds);
		return orderBillMapper.delete(params);
	}
	
	public int deleteOrders(List<OrderBill> listOrder) {
		List<String> orderIds = new ArrayList<String>();
		for(OrderBill order: listOrder) {
			orderIds.add(order.getId());
		}
		orderDetailService.delete4Order(orderIds);

		Map<String,Object> params = new HashMap<String, Object>();
		params.put("listOrder", listOrder);
		return orderBillMapper.delete(params);
	}
	
	/*********************************************/
	private OrderBillMapper orderBillMapper;
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	public void setOrderBillMapper(OrderBillMapper orderBillMapper) {
		super.ibatisMapper = orderBillMapper;
		this.orderBillMapper = orderBillMapper;
	}
	
	

}