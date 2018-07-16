package com.yonyou.iuap.example.orderinfo.service;

import com.yonyou.iuap.baseservice.attachment.service.GenericAtService;
import com.yonyou.iuap.example.orderinfo.dao.OrderInfoMapper;
import com.yonyou.iuap.example.orderinfo.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderInfoAttachmentService extends GenericAtService<OrderInfo>{

    private OrderInfoMapper OrderInfoMapper;

    @Autowired
    public void setOrderInfoMapper(OrderInfoMapper OrderInfoMapper) {
        this.OrderInfoMapper = OrderInfoMapper;
        super.setMapper(OrderInfoMapper);
    }
}
