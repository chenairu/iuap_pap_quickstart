package com.yonyou.iuap.example.common.support;

import java.lang.reflect.Field;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONBuilder {

	public JSONObject buildJSON(Object entity) {
		JSONObject json = (JSONObject)JSON.toJSON(entity);
		Field[] fields = entity.getClass().getDeclaredFields();
		try {
			for(int i=0; i<fields.length; i++) {
				ConvertField annotation = fields[i].getDeclaredAnnotation(ConvertField.class);
				if(annotation!=null) {
					fields[i].setAccessible(true);
					Object value = fields[i].get(entity);
					if(annotation.type() == ConvertType.SPRING) {
						Object targetValue = ConvertorHolder.convert(annotation, value);
						json.put(annotation.target(), targetValue);
					}
				}
			}
			return json;
		}catch(Exception exp) {
			throw new RuntimeException(exp);
		}
	}

}