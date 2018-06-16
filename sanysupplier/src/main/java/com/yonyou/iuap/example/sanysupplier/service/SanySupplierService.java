package com.yonyou.iuap.example.sanysupplier.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.service.GenericService;
import com.yonyou.iuap.example.sanysupplier.dao.SanySupplierMapper;
import com.yonyou.iuap.example.sanysupplier.entity.SanySupplier;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanySupplierService extends GenericService<SanySupplier>{
	
	public void batchSave(List<SanySupplier> listSanySupplier) {
		for(int i=0; i<listSanySupplier.size(); i++) {
			this.save(listSanySupplier.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanySupplier insert(SanySupplier SanySupplier) {
		if(StringUtils.isEmpty(SanySupplier.getId())) {
			//编码code生成
			SanySupplier.setIdentifycode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(SanySupplier);
	}
	
	/******************************************************/
	private SanySupplierMapper SanySupplierMapper;

	@Autowired
	public void setSanyOrderMapper(SanySupplierMapper SanySupplierMapper) {
		this.SanySupplierMapper = SanySupplierMapper;
		super.setGenericMapper(SanySupplierMapper);
	}
	
}