package com.yonyou.iuap.example.sanyorder.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderAttachmentMapper;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.AttachmentEntity;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;

import cn.hutool.core.util.StrUtil;
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
	
	public SanyOrder saveWithAttachment(SanyOrder SanyOrder) {
		if(SanyOrder.getId() == null){
			SanyOrder insertSanyOrder = new SanyOrder();
			insertSanyOrder = super.insert(SanyOrder);
			String id = insertSanyOrder.getId();
			String name = insertSanyOrder.getOrderName();
			List<AttachmentEntity> attachments = SanyOrder.getAttachment();
			for(AttachmentEntity att:attachments){
				att.setRefId(id);
				att.setRefName(name);
				if(att.getId()==null || StrUtil.isBlankIfStr(att.getId())) {
				    Serializable attid = GeneratorManager.generateID(att);
					att.setId(attid);
				}
				sanyOrderAttachmentMapper.insert(att);
			}
			return insertSanyOrder;
		}else{
			super.update(SanyOrder);
			String id = SanyOrder.getId();
			String name = SanyOrder.getOrderName();
			List<AttachmentEntity> attachments = SanyOrder.getAttachment();
			for(AttachmentEntity att:attachments){
				att.setRefId(id);
				att.setRefName(name);
				sanyOrderAttachmentMapper.insert(att);
			}
			return SanyOrder;
		}
	}
	
	public Page<SanyOrder> getListWithAttach(PageRequest pageRequest, SearchParams searchParams) {
		Page<SanyOrder> page = sanyOrderMapper.selectAllByPage(pageRequest, searchParams).getPage();
		List<SanyOrder> listSanyOrder = page.getContent();
		for(SanyOrder sanyOrder : listSanyOrder){
			List<AttachmentEntity> attachments = sanyOrderAttachmentMapper.getRefId(sanyOrder.getId());
			sanyOrder.setAttachment(attachments);
		}
		return page;
	}

	private SanyOrderMapper sanyOrderMapper;
	
	@Autowired
	private SanyOrderAttachmentMapper sanyOrderAttachmentMapper;

	@Autowired
	public void setSanyOrderMapper(SanyOrderMapper sanyOrderMapper) {
		this.sanyOrderMapper = sanyOrderMapper;
		super.setIbatisMapperEx(this.sanyOrderMapper);
	}

}