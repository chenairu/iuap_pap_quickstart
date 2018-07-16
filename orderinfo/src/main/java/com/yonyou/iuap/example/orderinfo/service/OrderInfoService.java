package com.yonyou.iuap.example.orderinfo.service;

import com.yonyou.iuap.baseservice.bpm.service.GenericBpmService;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.orderinfo.dao.OrderInfoMapper;
import com.yonyou.iuap.example.orderinfo.entity.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Service
public class OrderInfoService extends GenericBpmService<OrderInfo>{

    private OrderInfoMapper OrderInfoMapper;

    @Autowired
    public void setOrderInfoMapper(OrderInfoMapper OrderInfoMapper) {
        this.OrderInfoMapper = OrderInfoMapper;
        super.setIbatisMapperEx(OrderInfoMapper);
    }

    @Override
    public  BPMFormJSON buildVariables(OrderInfo entity)
    {
        BPMFormJSON bpmform = new BPMFormJSON();
        String userName = InvocationInfoProxy.getUsername();
        try {
            userName = URLDecoder.decode(userName,"utf-8");
            userName = URLDecoder.decode(userName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            userName =InvocationInfoProxy.getUsername();
        }
        String title = userName + "提交的【工单】,单号 是" + entity.getBpmBillCode() + ", 请审批";
        bpmform.setTitle(title);
        bpmform.setFormUrl("/iuap_pap_quickstart/pages/workorder/workorder.js");	// 单据url
        bpmform.setProcessInstanceName(title);										// 流程实例名称
        bpmform.setServiceClass("/iuap_pap_quickstart/order_info");// 流程审批后，执行的业务处理类(controller对应URI前缀)

        return bpmform;
    }

}