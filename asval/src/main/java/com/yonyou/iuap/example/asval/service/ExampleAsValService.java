package com.yonyou.iuap.example.asval.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.asval.dao.ExampleAsValMapper;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.utils.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ExampleAsValService {
    private Logger logger = LoggerFactory.getLogger(ExampleAsValService.class);
    @Autowired
    private ExampleAsValMapper exampleAsValMapper;

    public List<ExampleAsVal> findAll(){
        return exampleAsValMapper.findAll();
    }

    public List<ExampleAsVal> findByClause(ExampleAsVal exampleAsVal){

        return exampleAsValMapper.findByClause(exampleAsVal);
    }



    public List<ExampleAsVal> likeSarch(String keyword){
        List<ExampleAsVal> list = new ArrayList<>();
        list = exampleAsValMapper.queryByClause(keyword);
        return list;
    }

    public List<ExampleAsVal> getByIds(String[] strArray) {
        String tenantId = InvocationInfoProxy.getTenantid();
        List<String> ids = new ArrayList<String>();
        for (String key : strArray) {
            ids.add(key);
        }
        return exampleAsValMapper.getByIds(tenantId,ids);
    }

    public List<ExampleAsVal> getByIds(String tenantId,List<String> ids){
        return exampleAsValMapper.getByIds(tenantId,ids);
    }

    public Page<ExampleAsVal> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleAsValMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
    }
    @Transactional(rollbackFor = Exception.class)
    public void batchAddOrUpdate(List<ExampleAsVal> list){
        if(list == null || list.size()==0){
            return;
        }
        for(ExampleAsVal exampleAsVal : list){
            this.save(exampleAsVal);
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<ExampleAsVal> list){
        if(list == null || list.size()==0){
            return;
        }
        for(ExampleAsVal asVal : list){
            this.delete(asVal);
        }
    }
    public void save(ExampleAsVal exampleAsVal){
        if(exampleAsVal.getId() == null){
            String uuid = UUID.randomUUID().toString();
            exampleAsVal.setId(uuid);
            //编码规则 获取编码
            String code = this.getCode("asval","",exampleAsVal);
            exampleAsVal.setCode(code);
            exampleAsVal.setLstdate(new Date());
            exampleAsValMapper.insert(exampleAsVal);
        }else{
            exampleAsVal.setLstdate(new Date());
            exampleAsValMapper.updateByPrimaryKey(exampleAsVal);
        }
    }
    public void delete(ExampleAsVal exampleAsVal){

        exampleAsValMapper.deleteByPrimaryKey(exampleAsVal.getId());
        //编码规则退号
        this.returnCode("asval","",exampleAsVal,exampleAsVal.getCode());
    }
    /**
     * 获取编码规则
     *
     * @param billObjCode 编码对象code
     * @param pkAssign 分配关系
     * @param entity
     * @return
     */
    private String getCode(String billObjCode,String pkAssign,ExampleAsVal entity){
        String billvo = JSONObject.toJSONString(entity);

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

    /**
     * 回退单据号，以保证单据号连号的业务需要
     *
     * @param billObjCode
     *            编码对象code
     * @param pkAssign
     *            分配关系
     * @param entity
     * @param code 编码字段
     * @return
     */
    private void returnCode(String billObjCode,String pkAssign,ExampleAsVal entity,String code){
        String billVo = JSONObject.toJSONString(entity);
        String returnUrl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")+"/billcoderest/returnBillCode";
        Map<String,String> data = new HashMap<String,String>();
        data.put("billObjCode",billObjCode);
        data.put("pkAssign",pkAssign);
        data.put("billVo",billVo);
        data.put("billCode",code);

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
