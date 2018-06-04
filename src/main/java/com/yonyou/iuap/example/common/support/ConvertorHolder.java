package com.yonyou.iuap.example.common.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ConvertorHolder implements ApplicationContextAware {

	public static Object convert(ConvertField annotation, Object value) {
		FieldConvertor convertor = ctx.getBean(annotation.convertor(), FieldConvertor.class);
		return convertor.convert(annotation.index(), value);
	}
	
	
	/************************************************/
	private static ApplicationContext ctx;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}
	
}