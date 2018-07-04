package com.yonyou.iuap.example.workorder.service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.example.workorder.dao.WorkorderMapper;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yonyou.bpm.rest.ex.util.DateUtil;

import java.util.Date;
import java.util.Random;

@Service
public class WorkorderService extends GenericBpmService<Workorder> {
	
	/**
	 * 新增保存工单信息
	 */
	@Override
	public Workorder insert(Workorder workorder) {
		if(workorder.getId()==null ) {
			//编码code生成
			workorder.setCode(DateUtil.toDateString(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
		}
		return super.insert(workorder);
	}

	/******************************************************/

	private WorkorderMapper workorderMapper;

	@Autowired
	public void setWorkorderMapper(WorkorderMapper workorderMapper) {
		this.workorderMapper = workorderMapper;
		super.setIbatisMapperEx(workorderMapper);
	}
	
}