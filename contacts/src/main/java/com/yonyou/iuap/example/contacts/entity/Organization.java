package com.yonyou.iuap.example.contacts.entity;

import java.util.Date;

import com.yonyou.iuap.example.common.entity.AbsGenericEntity;

public class Organization extends AbsGenericEntity{
	
	private Date creationtime;
	private String short_name;
	private  String  instit_name;
	private  String email;
	private  String institid;
	private  String creator;
	private  String instit_type;
	private  String parent_id;
	private  String instit_code;
	
	
	public Date getCreationtime() {
		return creationtime;
	}
	public void setCreationtime(Date creationtime) {
		this.creationtime = creationtime;
	}
	
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	
	public String getInstit_name() {
		return instit_name;
	}
	public void setInstit_name(String instit_name) {
		this.instit_name = instit_name;
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
	
	public String getInstit_type() {
		return instit_type;
	}
	public void setInstit_type(String instit_type) {
		this.instit_type = instit_type;
	}
	
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	
	public String getInstit_code() {
		return instit_code;
	}
	public void setInstit_code(String instit_code) {
		this.instit_code = instit_code;
	}
	
}