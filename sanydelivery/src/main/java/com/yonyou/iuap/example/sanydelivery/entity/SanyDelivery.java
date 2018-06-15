package com.yonyou.iuap.example.sanydelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanyDelivery extends AbsGenericEntity implements GenericEntity {

	
	private String orderCode;
	private String orderId;
	private Integer orderNumber;
	private String prodbatch;
	private String materialCode;
	private String materialName;
	private Integer receNumber;
	private Integer deliveNumber;
	private String unit;
	private String remark;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getProdbatch() {
		return prodbatch;
	}
	public void setProdbatch(String prodbatch) {
		this.prodbatch = prodbatch;
	}
	public String getMaterialCode() {
		return materialCode;
	}
	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public Integer getReceNumber() {
		return receNumber;
	}
	public void setReceNumber(Integer receNumber) {
		this.receNumber = receNumber;
	}
	public Integer getDeliveNumber() {
		return deliveNumber;
	}
	public void setDeliveNumber(Integer deliveNumber) {
		this.deliveNumber = deliveNumber;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}


}