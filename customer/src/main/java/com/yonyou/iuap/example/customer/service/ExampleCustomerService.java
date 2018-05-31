package com.yonyou.iuap.example.customer.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.customer.dao.ExampleCustomerMapper;
import com.yonyou.iuap.example.customer.entity.ExampleCustomer;
import com.yonyou.iuap.message.CommonMessageSendService;
import com.yonyou.iuap.message.MessageEntity;
import com.yonyou.iuap.message.WebappMessageConst;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.utils.PropertyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ExampleCustomerService {
    private Logger logger = LoggerFactory.getLogger(ExampleCustomerService.class);
    @Autowired
    private ExampleCustomerMapper exampleCustomerMapper;
    @Autowired
    private CommonMessageSendService commonMessageSendService;

    public Page<ExampleCustomer> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return exampleCustomerMapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }

    public List<ExampleCustomer> findByCode(String code) {


        return exampleCustomerMapper.findByCode(code);
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(List<ExampleCustomer> list) {
        List<ExampleCustomer> addList = new ArrayList<>(list.size());
        List<ExampleCustomer> updateList = new ArrayList<>(list.size());
        for (ExampleCustomer customer : list) {
            if (customer.getId() == null) {
                this.insert(customer);
            } else {
                this.update(customer);
            }
        }

    }

    public void insert(ExampleCustomer entity) {
        entity.setId(UUID.randomUUID().toString());
        exampleCustomerMapper.insert(entity);
        //发送消息
        sendMessage(entity);
    }

    public void update(ExampleCustomer entity) {
        exampleCustomerMapper.updateByPrimaryKey(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByPrimaryKey(List<ExampleCustomer> list) {
        exampleCustomerMapper.batchDelete(list);
    }

    private void sendMessage(ExampleCustomer customer) {
        MessageEntity msg = new MessageEntity();
        String userid = InvocationInfoProxy.getUserid();
        String userName = InvocationInfoProxy.getUsername();

        msg.setSendman(userid); //发送人,当前设置为当前操作人
        msg.setChannel(new String[]{WebappMessageConst.CHANNEL_SYS}); //SYS是消息中心
        msg.setRecevier(new String[]{InvocationInfoProxy.getUserid(), "U001"});//接收者,数组形式,可以根据实际情况配置多人

        msg.setTemplatecode("cust");//消息模板编码,即在管理中心创建的消息模板编号
        msg.setBillid(customer.getCustomerCode());//消息ID
        msg.setTencentid(InvocationInfoProxy.getTenantid());
        msg.setMsgtype(WebappMessageConst.MESSAGETYPE_NOTICE);
        msg.setSubject("站内消息标题");
        msg.setContent("您新建了一条单据,单据号为" + customer.getCustomerCode() + "单据名称为" + customer.getCustomerName());
        JSONObject busiData = new JSONObject();

        //entity.code 即为在注册的实体加上实体的属性
        busiData.put("busientity.code", customer.getCustomerCode());
        //entity.name
        busiData.put("busientity.name", customer.getCustomerName());

        //这段方法是根据userid 获取username
        String url_user = "/wbalone/userRest/getByIds";
        List<String> listUser_name = new ArrayList<String>();
        listUser_name.add(userid);
        Map<String, String> mapParameter4 = new ConcurrentHashMap<String, String>();
        mapParameter4.put("tenantId", "tenant");
        mapParameter4.put("userIds", JSONArray.toJSON(listUser_name).toString());
        Map<String, String> mapUser_name = convertRefName(url_user, mapParameter4);
        userName = mapUser_name.get(userid);


        busiData.put("SENDMAN", userName); //设置发送人,为消息模板中的系统变量
        SimpleDateFormat df0 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        busiData.put("SYS_DATE", df1.format(System.currentTimeMillis()));//设置日期,为消息模板中的系统变量
        busiData.put("SYS_TIME", df0.format(System.currentTimeMillis()));//设置时间,为消息模板中的系统变量

        commonMessageSendService.sendTemplateMessage(msg, busiData); //发送模板消息
        commonMessageSendService.sendTextMessage(msg, busiData); //发送站内消息
    }

    private Map<String, String> convertRefName(String url, Map<String, String> mapParameter) {
        Map<String, String> mapRefName = new ConcurrentHashMap<String, String>();
        try {
            if (mapParameter != null && mapParameter.size() > 0) {
                int index = 0;
                for (Map.Entry<String, String> entry : mapParameter.entrySet()) {
                    String joinSymbol = null;
                    if (index == 0) {
                        joinSymbol = "?";
                    } else {
                        joinSymbol = "&";
                    }
                    url = String.format("%s%s%s=%s", url, joinSymbol, entry.getKey(), entry.getValue());
                    index++;
                }

                url = PropertyUtil.getPropertyByKey("base.url") + url;

                JSONObject getbillcodeinfo = RestUtils.getInstance().doPost(url, null, JSONObject.class);

                Map<String, Object> mapJson = (Map<String, Object>) JSON.parse(getbillcodeinfo.toString());

                JSONArray array = (JSONArray) mapJson.get("data");

                for (Object obj : array) {
                    Map<String, String> map = (Map<String, String>) obj;

                    mapRefName.put(map.get("id"), map.get("name"));
                }
                return mapRefName;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            mapRefName.clear();
        }
        return mapRefName;
    }
}
