/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月13日
 */

package com.yonyou.iuap.example.base.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.bpm.service.BPMSubmitBasicService;
import com.yonyou.iuap.example.base.common.dao.CommonMapper;
import com.yonyou.iuap.example.base.common.entity.ProcessEntity;
import com.yonyou.iuap.example.base.common.service.CommonService;

/**
  * @description 公共业务接口实现
  * @author 姚春雷
  * @date 2018年6月13日 上午11:39:57
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    private CommonMapper commonMapper;

    @Autowired
    private BPMSubmitBasicService bpmSubmitBasicService;

    @Value("${project.base.url}")
    private String projectUrl;

    @Override
    public JSONObject submitFlow(ProcessEntity processEntity) {
        processEntity.setServiceUrl("/example/processweb");
        BPMFormJSON submitJson = new BPMFormJSON();
        submitJson.setProcessDefinitionKey(processEntity.getProcessDefinitionKey()); //流程定义KEY
        submitJson.setProcessInstanceName(processEntity.getProcessInstanceName()); //流程实例名称

        submitJson.setFormId(processEntity.getBusinessPk()); //业务主键
        submitJson.setBillNo(processEntity.getBusinessCode()); //业务编码
        submitJson.setOrgId(processEntity.getOrgId()); //组织ID
        submitJson.setBillMarker(processEntity.getOperatorId()); //制单人ID
        submitJson.setTitle(processEntity.getShowTile()); //在任务中心显示标题
        submitJson.setFormUrl(processEntity.getModelUrl()); //单据js地址
        submitJson.setServiceClass(processEntity.getServiceUrl()); //执行审批流之后的业务处理请求
        submitJson.setOtherVariables(processEntity.getOtherVariables()); //业务实体作为参数
        try {
            processEntity.setProcessStatus("已提交");
            commonMapper.updateProcessToBusinessTable(processEntity);
        } catch (Exception e) {
            /**
             * 此处抛出运行时异常，在流程提交以后我们会对当前业务进行更新处理
             * 因为流程和业务是解耦的，所以会存在事务不一致的问题，我们提前处理业务再处理流程
             * 以缓解事务不一致的问题，对业务操作，需要提供两个方法，
             * 一个是写入，一个是回退，否则流程出现异常无法回退。
             */
            LOGGER.error("提交流程操作业务表失败！", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return bpmSubmitBasicService.submit(submitJson);
    }
}
