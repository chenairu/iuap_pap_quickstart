package com.yonyou.iuap.example.common.support;

public interface FieldConvertor {
	
	/**
	 * 根据类型、值 转换为 名称
	 * @param index
	 * @param value
	 * @return
	 */
	public String convert(String index, Object value);

}