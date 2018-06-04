package com.yonyou.iuap.example.common.support;

import org.springframework.stereotype.Component;

@Component("codeConvertor")
public class CodeConvertor implements FieldConvertor{

	@Override
	public String convert(String index, Object value) {
		return null;
	}

}