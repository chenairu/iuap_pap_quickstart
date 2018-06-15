package com.yonyou.iuap.example.sanydelivery.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.sanydelivery.dao.SanyDeliveryMapper;
import com.yonyou.iuap.example.sanydelivery.entity.SanyDelivery;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanyDeliveryService extends GenericService<SanyDelivery>{
	
	public void batchSave(List<SanyDelivery> listSanyDelivery) {
		for(int i=0; i<listSanyDelivery.size(); i++) {
			this.save(listSanyDelivery.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyDelivery insert(SanyDelivery SanyDelivery) {
		if(StringUtils.isEmpty(SanyDelivery.getId())) {
			//编码code生成
			SanyDelivery.setOrderCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(SanyDelivery);
	}
	
	/******************************************************/
	private SanyDeliveryMapper SanyDeliveryMapper;

	@Autowired
	public void setSanyOrderMapper(SanyDeliveryMapper SanyDeliveryMapper) {
		this.SanyDeliveryMapper = SanyDeliveryMapper;
		super.setIbatisMapper(SanyDeliveryMapper);
	}
	
}