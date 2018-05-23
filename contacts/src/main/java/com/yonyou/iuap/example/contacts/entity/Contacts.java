package com.yonyou.iuap.example.contacts.entity;

import com.yonyou.iuap.example.common.entity.AbsGenericEntity;

public class Contacts extends AbsGenericEntity{
	
	private String id;

	private String peoname;

	private String peocode;

	private String institid;

	private String institname;

	private String worktel;

	private String email;

	private String sex;

	private String tel;

	private String office;

	private String countryzone;

	private String operate;

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getInstitid() {
		return institid;
	}

	public void setInstitid(String institid) {
		this.institid = institid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPeoname() {
		return peoname;
	}

	public void setPeoname(String peoname) {
		this.peoname = peoname;
	}

	public String getPeocode() {
		return peocode;
	}

	public void setPeocode(String peocode) {
		this.peocode = peocode;
	}

	public String getInstitname() {
		return institname;
	}

	public void setInstitname(String institname) {
		this.institname = institname;
	}

	public String getWorktel() {
		return worktel;
	}

	public void setWorktel(String worktel) {
		this.worktel = worktel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getCountryzone() {
		return countryzone;
	}

	public void setCountryzone(String countryzone) {
		this.countryzone = countryzone;
	}

}