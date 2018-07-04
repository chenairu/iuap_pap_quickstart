package com.yonyou.iuap.example.workorder.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.bpm.entity.AbsBpmModel;
import com.yonyou.iuap.baseservice.support.condition.Condition;
import com.yonyou.iuap.baseservice.support.generator.GeneratedValue;
import com.yonyou.iuap.baseservice.support.generator.Strategy;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;
import com.yonyou.iuap.persistence.vo.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name ="example_workorder_bpm")
public class Workorder extends AbsBpmModel {

	@Id
    @GeneratedValue(strategy=Strategy.UUID, module="bpm")
	@Column(name="id")
	@Condition
	protected String id;

	private String code;
	private String name;
	
	private String type;
	@Transient
	private String type_name;
	private Integer status;
	@Transient
	private String status_name;

	private String remark;
	
	private String content;
	private String applicant;
	@Column(name = "applytime")
	private Date applyTime;
    @Column(name = "finishtime")
	private Date finishTime;

    @Override
    public String getId() {
        return id;
    }
    //这个是为baseservice做数据保存时用的
    @Override
    public void setId(Serializable id) {
        this.id = id.toString();
        super.setId(id);
    }
    //这个是为mybatis做type映射用的
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String getBpmBillCode() {
		return null;
	}
}