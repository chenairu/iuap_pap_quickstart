package com.yonyou.iuap.example.common.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConvertField {
	
	/**
	 * 索引类型
	 * @return
	 */
	public String index();
	
	/**
	 * 输出属性名称
	 * @return
	 */
	public String target();

	/**
	 * the type of convertor
	 * @return
	 */
	public ConvertType type() default ConvertType.SPRING;

	/**
	 * the Convertor
	 * @return
	 */
	public String convertor() default "codeConvertor";

}