package com.yonyou.iuap.example.sanysupplier.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.entity.AbsModel;
import com.yonyou.iuap.baseservice.entity.Model;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SanySupplier extends AbsModel implements Model {

	private String suppliername;
	private String firmnature;
	private String unisocialcode;
	private String contactname;
	private String phonenum;
	private String email;
	private String identifycode;
	private String engname;
	private String componyurl;
	private String companyphone;
	private Date establishtime;
	private String country;
	private String province;
	private String city;
	private String addressdetail;
	private String entrepresent;
	private String totalemplyee;
	private String amount;
	private String regmeasure;
	private String fixedassets;
	private String fixedmeasure;
	private String trancur;
	private String annualsales;
	private String annualmeasure;
	private String supplycategory;
	private String mainproduct;
	private String agency;
	private String brand;
	private String totalfunds;
	private String totalmeasure;
//	private String totalfunds;
	private String agengcyqualify;
	private String maincustomer;
	private String secmaincus;
	private String thirdmaincus;
	private String banktype;
	private String bankpro;
	private String bankcity;
	private String bankname;
	private String bankconnum;
	private String accountnum;
	private String accountname;
	private String detection;
	private String businesslicense;
	private String remark;
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getFirmnature() {
		return firmnature;
	}
	public void setFirmnature(String firmnature) {
		this.firmnature = firmnature;
	}
	public String getUnisocialcode() {
		return unisocialcode;
	}
	public void setUnisocialcode(String unisocialcode) {
		this.unisocialcode = unisocialcode;
	}
	public String getContactname() {
		return contactname;
	}
	public void setContactname(String contactname) {
		this.contactname = contactname;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdentifycode() {
		return identifycode;
	}
	public void setIdentifycode(String identifycode) {
		this.identifycode = identifycode;
	}
	public String getEngname() {
		return engname;
	}
	public void setEngname(String engname) {
		this.engname = engname;
	}
	public String getComponyurl() {
		return componyurl;
	}
	public void setComponyurl(String componyurl) {
		this.componyurl = componyurl;
	}
	public String getCompanyphone() {
		return companyphone;
	}
	public void setCompanyphone(String companyphone) {
		this.companyphone = companyphone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddressdetail() {
		return addressdetail;
	}
	public void setAddressdetail(String addressdetail) {
		this.addressdetail = addressdetail;
	}
	public String getEntrepresent() {
		return entrepresent;
	}
	public void setEntrepresent(String entrepresent) {
		this.entrepresent = entrepresent;
	}
	public String getTotalemplyee() {
		return totalemplyee;
	}
	public void setTotalemplyee(String totalemplyee) {
		this.totalemplyee = totalemplyee;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRegmeasure() {
		return regmeasure;
	}
	public void setRegmeasure(String regmeasure) {
		this.regmeasure = regmeasure;
	}
	public String getFixedassets() {
		return fixedassets;
	}
	public void setFixedassets(String fixedassets) {
		this.fixedassets = fixedassets;
	}
	public String getFixedmeasure() {
		return fixedmeasure;
	}
	public void setFixedmeasure(String fixedmeasure) {
		this.fixedmeasure = fixedmeasure;
	}
	public String getTrancur() {
		return trancur;
	}
	public void setTrancur(String trancur) {
		this.trancur = trancur;
	}
	public String getAnnualsales() {
		return annualsales;
	}
	public void setAnnualsales(String annualsales) {
		this.annualsales = annualsales;
	}
	public String getAnnualmeasure() {
		return annualmeasure;
	}
	public void setAnnualmeasure(String annualmeasure) {
		this.annualmeasure = annualmeasure;
	}
	public String getSupplycategory() {
		return supplycategory;
	}
	public void setSupplycategory(String supplycategory) {
		this.supplycategory = supplycategory;
	}
	public String getMainproduct() {
		return mainproduct;
	}
	public void setMainproduct(String mainproduct) {
		this.mainproduct = mainproduct;
	}
	public String getAgency() {
		return agency;
	}
	public void setAgency(String agency) {
		this.agency = agency;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getTotalfunds() {
		return totalfunds;
	}
	public void setTotalfunds(String totalfunds) {
		this.totalfunds = totalfunds;
	}
	public String getTotalmeasure() {
		return totalmeasure;
	}
	public void setTotalmeasure(String totalmeasure) {
		this.totalmeasure = totalmeasure;
	}
	public String getAgengcyqualify() {
		return agengcyqualify;
	}
	public void setAgengcyqualify(String agengcyqualify) {
		this.agengcyqualify = agengcyqualify;
	}
	public String getMaincustomer() {
		return maincustomer;
	}
	public void setMaincustomer(String maincustomer) {
		this.maincustomer = maincustomer;
	}
	public String getSecmaincus() {
		return secmaincus;
	}
	public void setSecmaincus(String secmaincus) {
		this.secmaincus = secmaincus;
	}
	public String getThirdmaincus() {
		return thirdmaincus;
	}
	public void setThirdmaincus(String thirdmaincus) {
		this.thirdmaincus = thirdmaincus;
	}
	public String getBanktype() {
		return banktype;
	}
	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}
	public String getBankpro() {
		return bankpro;
	}
	public void setBankpro(String bankpro) {
		this.bankpro = bankpro;
	}
	public String getBankcity() {
		return bankcity;
	}
	public void setBankcity(String bankcity) {
		this.bankcity = bankcity;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBankconnum() {
		return bankconnum;
	}
	public void setBankconnum(String bankconnum) {
		this.bankconnum = bankconnum;
	}
	public String getAccountnum() {
		return accountnum;
	}
	public void setAccountnum(String accountnum) {
		this.accountnum = accountnum;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public String getDetection() {
		return detection;
	}
	public void setDetection(String detection) {
		this.detection = detection;
	}
	public String getBusinesslicense() {
		return businesslicense;
	}
	public void setBusinesslicense(String businesslicense) {
		this.businesslicense = businesslicense;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getEstablishtime() {
		return establishtime;
	}
	public void setEstablishtime(Date establishtime) {
		this.establishtime = establishtime;
	}
	

}