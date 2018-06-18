package com.yonyou.iuap.example.attachment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * mybatis方式
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExampleAttachment {
	
	private String id;
	private String code;
	private String name;
	public ExampleAttachment() {
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

}