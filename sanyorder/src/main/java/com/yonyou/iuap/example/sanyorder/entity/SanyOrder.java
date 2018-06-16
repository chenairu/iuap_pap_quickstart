package com.yonyou.iuap.example.sanyorder.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanyOrder extends AbsGenericEntity implements GenericEntity {

	private String orderCode;//订单编号
	private String orderName;//订单名称
	private String supplier;//供应商
	private String supplierName;//供应商名称
	private String type;//类型
	
	private String purchasing;//采购组织
	private String purchasingGroup;//采购组
	private Date voucherDate;//凭证日期
	private Integer approvalState;//审批状态
	private Integer confirmState;//确认状态
	private Integer closeState;//关闭状态
	
	
	private String type_name;//类型名称
	private String approvalState_name;//关闭状态
	private String confirmState_name;//关闭状态
	private String closeState_name;//关闭状态
	
	

	private String remark;

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPurchasing() {
		return purchasing;
	}

	public void setPurchasing(String purchasing) {
		this.purchasing = purchasing;
	}

	public String getPurchasingGroup() {
		return purchasingGroup;
	}

	public void setPurchasingGroup(String purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public Integer getApprovalState() {
		return approvalState;
	}

	public void setApprovalState(Integer approvalState) {
		this.approvalState = approvalState;
	}

	public Integer getConfirmState() {
		return confirmState;
	}

	public void setConfirmState(Integer confirmState) {
		this.confirmState = confirmState;
	}

	public Integer getCloseState() {
		return closeState;
	}

	public void setCloseState(Integer closeState) {
		this.closeState = closeState;
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

	public String getApprovalState_name() {
		return approvalState_name;
	}

	public void setApprovalState_name(String approvalState_name) {
		this.approvalState_name = approvalState_name;
	}

	public String getConfirmState_name() {
		return confirmState_name;
	}

	public void setConfirmState_name(String confirmState_name) {
		this.confirmState_name = confirmState_name;
	}

	public String getCloseState_name() {
		return closeState_name;
	}

	public void setCloseState_name(String closeState_name) {
		this.closeState_name = closeState_name;
	}

	

}