package com.yonyou.iuap.example.billcode.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.billcode.dao.ExampleCustomerMapper;
import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
import com.yonyou.iuap.message.CommonMessageSendService;
import com.yonyou.iuap.message.MessageEntity;
import com.yonyou.iuap.message.WebappMessageConst;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
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
import java.util.concurrent.ConcurrentMap;


@Service
public class ExampleCustomerService {
    private Logger logger = LoggerFactory.getLogger(ExampleCustomerService.class);
    @Autowired
    private ExampleCustomerMapper exampleCustomerMapper;
    @Autowired
    private CommonMessageSendService commonMessageSendService;
    public Page<ExampleCustomer> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleCustomerMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
    }

    public List<ExampleCustomer> findByCode(String code){
        return exampleCustomerMapper.findByCode(code);
    }

    @Transactional
    public void save(List<ExampleCustomer> list){
        List<ExampleCustomer> addList = new ArrayList<>(list.size());
        List<ExampleCustomer> updateList = new ArrayList<>(list.size());
        for(ExampleCustomer customer : list){
            if(customer.getId() == null){
                customer.setId(UUID.randomUUID().toString());
                //编码规则
                String customerCode = this.getCustomerCode("customer","",customer,customer.getCustomerCode());
                customer.setCustomerCode(customerCode);
                addList.add(customer);
                sendMessage(customer); //发送消息
            }else{
                updateList.add(customer);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            exampleCustomerMapper.batchInsert(addList);
        }

        if (CollectionUtils.isNotEmpty(updateList)) {
            exampleCustomerMapper.batchUpdate(updateList);
        }

    }

    public void batchDeleteByPrimaryKey(List<ExampleCustomer> list){
        exampleCustomerMapper.batchDelete(list);
        for(ExampleCustomer customer : list){
            this.returnCustomerCode("customer","",customer,customer.getCustomerCode());
        }
    }

    private String getCustomerCode(String billObjCode,String pkAssign,ExampleCustomer customer,String customerCode){
        String billvo = JSONObject.toJSONString(customer);

        String getCodeUrl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")+"/billcoderest/getBillCode";

        Map<String,String> data = new HashMap<String,String>();
        data.put("billObjCode",billObjCode);
        data.put("pkAssign",pkAssign);
        data.put("billVo",billvo);

        JSONObject getBillCodeInfo = RestUtils.getInstance().doPost(getCodeUrl,data,JSONObject.class);
        logger.debug(getBillCodeInfo.toJSONString());

        String getFlag = getBillCodeInfo.getString("status");
        String billCode = getBillCodeInfo.getString("billcode");

        if ("failed".equalsIgnoreCase(getFlag)){
            String errMsg = getBillCodeInfo.getString("msg");
            logger.error("{billObjCode:" + billObjCode + ",pkAssign:" + pkAssign + ",billvo:" + billvo + "},错误信息:" + errMsg);
            throw new BusinessException("获取编码规则发生错误",errMsg);
        }
        return billCode;
    }

    private void returnCustomerCode(String billObjCode,String pkAssign,ExampleCustomer customer,String customerCode){
        String billVo = JSONObject.toJSONString(customer);
        String returnUrl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")+"/billcoderest/returnBillCode";
        Map<String,String> data = new HashMap<String,String>();
        data.put("billObjCode",billObjCode);
        data.put("pkAssign",pkAssign);
        data.put("billVo",billVo);
        data.put("billCode",customerCode);

        JSONObject returnBillCodeInfo = RestUtils.getInstance().doPost(returnUrl,data,JSONObject.class);

        logger.debug(returnBillCodeInfo.toJSONString());
        String returnFlag = returnBillCodeInfo.getString("status");

        if("failed".equalsIgnoreCase(returnFlag)){
            String errMsg = returnBillCodeInfo.getString("msg");
            logger.error("{billObjCode:" + billObjCode + ",pkAssign:" + pkAssign + ",billvo:" + billVo + "},错误信息:" + errMsg);
            throw new BusinessException("返回单据号失败",errMsg);
        }

    }

    private void sendMessage(ExampleCustomer customer){
        MessageEntity msg = new MessageEntity();
        String userid = InvocationInfoProxy.getUserid();

        msg.setSendman(userid);
        msg.setChannel(new String[]{WebappMessageConst.CHANNEL_SYS});
        msg.setRecevier(new String[]{InvocationInfoProxy.getUserid(),"U001"});
        msg.setTemplatecode("busidemomsg");
        msg.setBillid(customer.getCustomerCode());
        msg.setTencentid(InvocationInfoProxy.getTenantid());
        msg.setMsgtype(WebappMessageConst.MESSAGETYPE_NOTICE);
        msg.setSubject("站内消息标题");
        msg.setContent("您新建了一条单据,单据号为"+customer.getCustomerCode()+"单据名称为"+customer.getCustomerName());
        JSONObject busiData = new JSONObject();
        busiData.put("busientity.code",customer.getCustomerCode());
        busiData.put("busientity.name",customer.getCustomerName());

        String url_user = "/wbalone/userRest/getByIds";
        List<String> listUser_name = new ArrayList<String>();
        listUser_name.add(userid);
        Map<String,String> mapParameter4 = new ConcurrentHashMap<String,String>();
        mapParameter4.put("tenantId","tenant");
        mapParameter4.put("userIds",JSONArray.toJSON(listUser_name).toString());

        Map<String,String> mapUser_name = convertRefName(url_user,mapParameter4);
        String userName = InvocationInfoProxy.getUsername();
        userName = mapUser_name.get(userid);
        busiData.put("SENDMAN",userName);
        SimpleDateFormat df0 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        busiData.put("SYS_DATE",df1.format(System.currentTimeMillis()));
        busiData.put("SYS_TIME",df0.format(System.currentTimeMillis()));

        commonMessageSendService.sendTemplateMessage(msg,busiData);
        commonMessageSendService.sendTextMessage(msg,busiData);
    }

    private Map<String, String> convertRefName(String url, Map<String, String> mapParameter) {
        Map<String, String> mapRefName = new ConcurrentHashMap<String, String>();

        try
        {
            if (mapParameter != null && mapParameter.size() > 0)
            {
                int index = 0;

                for (Map.Entry<String, String> entry : mapParameter.entrySet()) {
                    String joinSymbol = null;

                    if (index == 0)
                    {
                        joinSymbol = "?";
                    }
                    else
                    {
                        joinSymbol = "&";
                    }

                    url = String.format("%s%s%s=%s", url, joinSymbol, entry.getKey(), entry.getValue());

                    index++;
                }

                url = PropertyUtil.getPropertyByKey("base.url") + url;

                JSONObject getbillcodeinfo = RestUtils.getInstance().doPost(url, null, JSONObject.class);

                Map<String, Object> mapJson = (Map<String, Object>)JSON.parse(getbillcodeinfo.toString());

                JSONArray array = (JSONArray)mapJson.get("data");

                for(Object obj : array)
                {
                    Map<String, String> map = (Map<String, String>)obj;

                    mapRefName.put(map.get("id"), map.get("name"));
                }

                return mapRefName;

            }
        }
        catch(Exception ex)
        {
            logger.error(ex.getMessage(), ex);

            mapRefName.clear();
        }

        return mapRefName;
    }
}
