package com.yonyou.iuap.example.sanyorder.service;

import com.yonyou.iuap.baseservice.attachment.service.GenericAtService;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SanyOrderAttachmentService extends GenericAtService<SanyOrder> {

    private SanyOrderMapper sanyOrderMapper;

    @Autowired
    public void setSanyOrderMapper(SanyOrderMapper sanyOrderMapper) {
        this.sanyOrderMapper = sanyOrderMapper;
        super.setIbatisMapperEx(sanyOrderMapper);
        super.setMapper(sanyOrderMapper);
    }

    @Override
    public SanyOrder saveWithAttachment(SanyOrder entity) {
        entity.setOrderCode(entity.getBpmBillCode());//编码code生成
        return super.saveWithAttachment(entity);
    }
}
