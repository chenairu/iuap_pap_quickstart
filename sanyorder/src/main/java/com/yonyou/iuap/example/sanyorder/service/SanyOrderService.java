package com.yonyou.iuap.example.sanyorder.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanyOrderService extends GenericService<SanyOrder>{
	
	public void batchSave(List<SanyOrder> listSanyOrder) {
		for(int i=0; i<listSanyOrder.size(); i++) {
			this.save(listSanyOrder.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyOrder insert(SanyOrder SanyOrder) {
		if(StringUtils.isEmpty(SanyOrder.getId())) {
			//编码code生成
			SanyOrder.setOrderCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(SanyOrder);
	}
	

	/******************************************************/
	private SanyOrderMapper SanyOrderMapper;

	@Autowired
	public void setSanyOrderMapper(SanyOrderMapper SanyOrderMapper) {
		this.SanyOrderMapper = SanyOrderMapper;
		super.setIbatisMapper(SanyOrderMapper);
	}
	
}