package com.yonyou.iuap.example.print.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.persistence.vo.BaseEntity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamplePrint extends BaseEntity {
	
	private String id;
	private String code;
	private String name;
	private String remark;
	public ExamplePrint() {
	}
    

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String getMetaDefinedName() {
		return "example_print";
	}
	@Override
	public String getNamespace() {
		return "iuap_example";
	}

}