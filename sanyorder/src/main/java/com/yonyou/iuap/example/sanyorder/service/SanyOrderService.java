package com.yonyou.iuap.example.sanyorder.service;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanyOrderService extends GenericBpmService<SanyOrder>{

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyOrder insert(SanyOrder SanyOrder) {
		if(SanyOrder.getId()==null) {
			//编码code生成
			SanyOrder.setOrderCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		
		return super.insert(SanyOrder);
	}
	

	private SanyOrderMapper sanyOrderMapper;

	@Autowired
	public void setSanyOrderMapper(SanyOrderMapper sanyOrderMapper) {
		this.sanyOrderMapper = sanyOrderMapper;
		super.setIbatisMapperEx(this.sanyOrderMapper);
	}
}