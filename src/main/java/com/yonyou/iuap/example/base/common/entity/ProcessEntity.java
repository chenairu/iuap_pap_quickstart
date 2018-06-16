/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月13日
 */

package com.yonyou.iuap.example.base.common.entity;

import java.io.Serializable;
import java.util.List;

import yonyou.bpm.rest.request.RestVariable;

/**
  * @description 流程实体
  * @author 姚春雷
  * @date 2018年6月13日 下午1:44:27
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class ProcessEntity implements Serializable {

    /**
      *
      */
    private static final long serialVersionUID = 1L;

    private String processDefinitionKey; //流程定义KEY

    private String processInstanceName; //流程实例名称

    private String businessPk; //业务主键

    private String businessCode; //业务编码

    private String orgId; //组织ID

    private String operatorId; //操作者ID

    private String showTile; //在任务中心显示标题

    private String modelUrl; //模块js地址

    private String serviceUrl; //执行审批流之后的业务处理请求

    private String businessTable; //业务表名称

    private String processStatus; //流程状态

    private String businessPkFiled; //主键字段

    List<RestVariable> otherVariables; //参数变量

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    public String getBusinessPk() {
        return businessPk;
    }

    public void setBusinessPk(String businessPk) {
        this.businessPk = businessPk;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getShowTile() {
        return showTile;
    }

    public void setShowTile(String showTile) {
        this.showTile = showTile;
    }

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getBusinessPkFiled() {
        return businessPkFiled;
    }

    public void setBusinessPkFiled(String businessPkFiled) {
        this.businessPkFiled = businessPkFiled;
    }

    public List<RestVariable> getOtherVariables() {
        return otherVariables;
    }

    public void setOtherVariables(List<RestVariable> otherVariables) {
        this.otherVariables = otherVariables;
    }

}
