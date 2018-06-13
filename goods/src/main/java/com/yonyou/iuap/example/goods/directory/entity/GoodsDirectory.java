package com.yonyou.iuap.example.goods.directory.entity;

import java.util.ArrayList;
import java.util.List;

import com.yonyou.iuap.example.common.entity.AbsGenericEntity;
import com.yonyou.iuap.example.common.entity.GenericEntity;

public class GoodsDirectory extends AbsGenericEntity implements GenericEntity {

	private String code;
	private String name;
	private String parentId;
	private String remark;
	private List<GoodsDirectory> children = new ArrayList<GoodsDirectory>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<GoodsDirectory> getChildren() {
		return children;
	}

	public void setChildren(List<GoodsDirectory> children) {
		this.children = children;
	}

}