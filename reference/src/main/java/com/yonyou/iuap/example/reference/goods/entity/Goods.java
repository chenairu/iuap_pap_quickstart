package com.yonyou.iuap.example.reference.goods.entity;

import com.yonyou.iuap.example.common.entity.AbsGenericEntity;

public class Goods extends AbsGenericEntity{

	private String goodsCode;
	private String goodsName;
	private String model;
	private Double price;
	private String currency;
	private String currency_name;
	private String remark;
	private String manufacturer;
	private String manufacturer_name;
	private String linkman;
	private String linkman_name;
	
	private String createUser_name;
	private String lastModifyUser_name;

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getCurrency_name() {
		return currency_name;
	}

	public void setCurrency_name(String currency_name) {
		this.currency_name = currency_name;
	}

	public String getManufacturer_name() {
		return manufacturer_name;
	}

	public void setManufacturer_name(String manufacturer_name) {
		this.manufacturer_name = manufacturer_name;
	}

	public String getLinkman_name() {
		return linkman_name;
	}

	public void setLinkman_name(String linkman_name) {
		this.linkman_name = linkman_name;
	}

	public String getCreateUser_name() {
		return createUser_name;
	}

	public void setCreateUser_name(String createUser_name) {
		this.createUser_name = createUser_name;
	}

	public String getLastModifyUser_name() {
		return lastModifyUser_name;
	}

	public void setLastModifyUser_name(String lastModifyUser_name) {
		this.lastModifyUser_name = lastModifyUser_name;
	}

}