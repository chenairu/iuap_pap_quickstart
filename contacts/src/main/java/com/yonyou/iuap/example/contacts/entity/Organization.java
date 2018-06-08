package com.yonyou.iuap.example.contacts.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Organization extends AbsGenericEntity{
	
	private Date creationtime;
	private String shortname;
	private  String  institname;
	private  String email;
	private  String institid;
	private  String creator;
	private  String instittype;
	private  String parentid;
	private  String institcode;
	
	public Date getCreationtime() {
		return creationtime;
	}
	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getInstitname() {
		return institname;
	}
	public void setInstitname(String institname) {
		this.institname = institname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInstitid() {
		return institid;
	}
	public void setInstitid(String institid) {
		this.institid = institid;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getInstittype() {
		return instittype;
	}
	public void setInstittype(String instittype) {
		this.instittype = instittype;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getInstitcode() {
		return institcode;
	}
	public void setInstitcode(String institcode) {
		this.institcode = institcode;
	}
	
	
	
}