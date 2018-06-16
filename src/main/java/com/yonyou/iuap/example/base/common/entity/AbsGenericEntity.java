/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月4日
 */

package com.yonyou.iuap.example.base.common.entity;

import java.util.Date;

import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
  * @description 通用抽象类，继承了iuap的BaseEntity
  * @author 姚春雷
  * @date 2018年6月4日 上午10:07:20
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public abstract class AbsGenericEntity extends BaseEntity {

    /**
      *
      */
    private static final long serialVersionUID = 1L;

    protected Integer dr; //是否删除

    protected Date ts; //更新时间

    protected Integer version; //版本

    protected Date lastModified; //最后修改时间

    protected String lastModifyUser; //最后修改人

    protected String lastModifyUserName; //最后修改人

    protected Date createTime; //创建时间

    protected String createUser; //创建人

    protected String createUserName; //创建人

    private String processDefinitionKey; //流程定义KEY

    private String processInstanceName; //流程实例名称

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public String getLastModifyUserName() {
        return lastModifyUserName;
    }

    public void setLastModifyUserName(String lastModifyUserName) {
        this.lastModifyUserName = lastModifyUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

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

}
