package com.yonyou.iuap.example.billcode.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.example.billcode.dao.ExampleCustomerMapper;
import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
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

import java.util.*;


@Service
public class ExampleCustomerService {
    private Logger logger = LoggerFactory.getLogger(ExampleCustomerService.class);
    @Autowired
    private ExampleCustomerMapper exampleCustomerMapper;

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
}
