package com.yonyou.iuap.example.sanyorder.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.baseservice.support.generator.GeneratorManager;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderAttachmentMapper;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.AttachmentEntity;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import com.yonyou.iuap.mvc.type.SearchParams;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;


@Service
public class SanyOrderService extends GenericBpmService<SanyOrder>{

	/**
	 * 新增保存工单信息
	 */
	@Override
	public SanyOrder insert(SanyOrder SanyOrder) {
		if(SanyOrder.getId()==null) {
			//编码code生成
			SanyOrder.setOrderCode(DateUtil.format(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10)));
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
				String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
				att.setCreateTime(now);
				att.setCreateUser(InvocationInfoProxy.getUserid());
				att.setLastModified(now);
				att.setLastModifyUser(InvocationInfoProxy.getUserid());
				att.setTs(now);
				sanyOrderAttachmentMapper.insert(att);
			}
			return insertSanyOrder;
		}else{
			super.update(SanyOrder);
			String id = SanyOrder.getId();
			String name = SanyOrder.getOrderName();
			List<AttachmentEntity> attachments = SanyOrder.getAttachment();
			for(AttachmentEntity att:attachments){
				if(att.getDel() != null){
					att.setDr(1);
					String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
					att.setLastModified(now);
					att.setLastModifyUser(InvocationInfoProxy.getUserid());
					sanyOrderAttachmentMapper.update(att);
				}else{
					if(att.getId()==null || StrUtil.isBlankIfStr(att.getId())){
						Serializable attid = GeneratorManager.generateID(att);
						att.setId(attid);
						att.setRefId(id);
						att.setRefName(name);
						String now = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss SSS");
						att.setCreateTime(now);
						att.setCreateUser(InvocationInfoProxy.getUserid());
						att.setLastModified(now);
						att.setLastModifyUser(InvocationInfoProxy.getUserid());
						att.setTs(now);
						sanyOrderAttachmentMapper.insert(att);
					}
				}
			}
			return SanyOrder;
		}
	}
	
	public Page<SanyOrder> getListWithAttach(PageRequest pageRequest, SearchParams searchParams) {
		Page<SanyOrder> page = sanyOrderMapper.selectAllByPage(pageRequest, searchParams).getPage();
		List<SanyOrder> listSanyOrder = page.getContent();
		for(SanyOrder sanyOrder : listSanyOrder){

            Map params = new HashMap<>();
            params.put("refId",sanyOrder.getId()   );
            List<AttachmentEntity> attachments = sanyOrderAttachmentMapper.queryList(params);
//			List<AttachmentEntity> attachments = sanyOrderAttachmentMapper.getRefId(sanyOrder.getId());
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

    @Override
    public  BPMFormJSON buildVariables(SanyOrder entity)
    {
        BPMFormJSON bpmform = new BPMFormJSON();
        String userName = InvocationInfoProxy.getUsername();
        try {
            userName = URLDecoder.decode(userName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            userName =InvocationInfoProxy.getUsername();
        }
        String title = userName + "提交的【工单】,单号 是" + entity.getBpmBillCode() + ", 请审批";
        bpmform.setTitle(title);
        bpmform.setFormUrl("/iuap_pap_quickstart/pages/workorder/workorder.js");	// 单据url
        bpmform.setProcessInstanceName(title);										// 流程实例名称
        bpmform.setServiceClass("/iuap_pap_quickstart/sany_order");// 流程审批后，执行的业务处理类(controller对应URI前缀)

        return bpmform;
    }

}