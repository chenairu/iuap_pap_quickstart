package com.yonyou.iuap.example.workorder.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
//public class Workorder extends BaseEntity implements GenericEntity {
public class Workorder extends AbsGenericEntity implements GenericEntity {

//	private static final long serialVersionUID = 7415955552838232647L;

	private String code;
	private String name;
	
	private String type;
	private String type_name;
	private Integer status;
	private String status_name;

	private String remark;
	
	private String content;
	private String applicant;
	private Date applyTime;
	private Date finishTime;

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

}