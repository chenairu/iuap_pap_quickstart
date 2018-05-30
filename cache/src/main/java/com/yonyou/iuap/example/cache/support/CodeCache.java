package com.yonyou.iuap.example.cache.support;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;

@Component
public class CodeCache {

	@PostConstruct
	public void loadAllCodes() {
		List<Dictionary> list = service.findAll();
		for(Dictionary entity: list) {
			Code code = new Code();
			code.setCode(entity.getCode());
			code.setName(entity.getName());
			code.setType("code_test_001");
			this.setCode("code_test_001", entity.getCode(), code);
		}
	}
	
	public void loadCode(String codeType, String code) {
		
	}
	
	public void setCode(String codeType, String code, Code codeVo) {
		cacheManager.hset(codeType, code, JSON.toJSONString(codeVo));
	}
	
	public Code getCode(String codeType, String code) {
		String strCode = cacheManager.hget(codeType, code);
		if(!StringUtils.isEmpty(strCode)) {
			return JSON.parseObject(strCode, Code.class);
		}else {
			return null;
		}
	}
	
	public List<Code> getCodes(String codeType){
		return null;
	}
	
	/***************************************************/
	@Autowired
	private DictionaryService service;
	@Autowired
	private CacheManager cacheManager;
	
}