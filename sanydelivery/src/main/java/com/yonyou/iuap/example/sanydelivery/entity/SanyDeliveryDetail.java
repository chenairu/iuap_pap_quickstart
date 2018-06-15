package com.yonyou.iuap.example.sanydelivery.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanyDeliveryDetail extends AbsGenericEntity implements GenericEntity {

	private Date arrivalDate;
	private String deliveryCode;
	private Date deliveryDate;
	private String consigneeAddress;
	private String deliveryman;
	private String ladingId;
	private String licenseNumber;
	private String phone;
	private String station;
	private String transportCompany;
	private String transportNumber;
	private String transportation;
	private Date voucherDate;

	private String remark;

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getDeliveryman() {
		return deliveryman;
	}

	public void setDeliveryman(String deliveryman) {
		this.deliveryman = deliveryman;
	}

	public String getLadingId() {
		return ladingId;
	}

	public void setLadingId(String ladingId) {
		this.ladingId = ladingId;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getTransportCompany() {
		return transportCompany;
	}

	public void setTransportCompany(String transportCompany) {
		this.transportCompany = transportCompany;
	}

	public String getTransportNumber() {
		return transportNumber;
	}

	public void setTransportNumber(String transportNumber) {
		this.transportNumber = transportNumber;
	}

	public String getTransportation() {
		return transportation;
	}

	public void setTransportation(String transportation) {
		this.transportation = transportation;
	}

	public Date getVoucherDate() {
		return voucherDate;
	}

	public void setVoucherDate(Date voucherDate) {
		this.voucherDate = voucherDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}