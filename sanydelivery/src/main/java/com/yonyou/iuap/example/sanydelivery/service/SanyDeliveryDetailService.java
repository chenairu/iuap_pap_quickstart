package com.yonyou.iuap.example.sanydelivery.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.sanydelivery.dao.SanyDeliveryDetailMapper;
import com.yonyou.iuap.example.sanydelivery.entity.SanyDelivery;
import com.yonyou.iuap.example.sanydelivery.entity.SanyDeliveryDetail;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanyDeliveryDetailService extends GenericService<SanyDeliveryDetail>{
	
	public void batchSave(List<SanyDeliveryDetail> listSanyDelivery) {
		for(int i=0; i<listSanyDelivery.size(); i++) {
			this.save(listSanyDelivery.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyDeliveryDetail insert(SanyDeliveryDetail SanyDeliveryDetail) {
		if(StringUtils.isEmpty(SanyDeliveryDetail.getId())) {
			//编码code生成
			SanyDeliveryDetail.setDeliveryCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(SanyDeliveryDetail);
	}
	
	/******************************************************/
	private SanyDeliveryDetailMapper SanyDeliveryDetailMapper;

	@Autowired
	public void setSanyOrderMapper(SanyDeliveryDetailMapper SanyDeliveryDetailMapper) {
		this.SanyDeliveryDetailMapper = SanyDeliveryDetailMapper;
		super.setIbatisMapper(SanyDeliveryDetailMapper);
	}
	
}