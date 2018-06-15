package com.yonyou.iuap.example.sanyrole.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.sanyrole.dao.SanyRoleMapper;
import com.yonyou.iuap.example.sanyrole.entity.SanyRole;

import yonyou.bpm.rest.ex.util.DateUtil;

@Service
public class SanyRoleService extends GenericService<SanyRole>{
	
	public void batchSave(List<SanyRole> listSanyRole) {
		for(int i=0; i<listSanyRole.size(); i++) {
			this.save(listSanyRole.get(i));
		}
	}
	

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyRole insert(SanyRole SanyRole) {
		if(StringUtils.isEmpty(SanyRole.getId())) {
			//编码code生成
			SanyRole.setRoleCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(SanyRole);
	}
	
	/******************************************************/
	private SanyRoleMapper SanyRoleMapper;

	@Autowired
	public void setSanyOrderMapper(SanyRoleMapper SanyRoleMapper) {
		this.SanyRoleMapper = SanyRoleMapper;
		super.setIbatisMapper(SanyRoleMapper);
	}
	
}