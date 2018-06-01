package com.yonyou.iuap.example.cache.support;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.cache.CacheManager;
import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;

@Component
public class CodeCache {
	
	private Logger logger = LoggerFactory.getLogger(CodeCache.class);

	/**
	 * 启动时自动加载字典
	 */
	@PostConstruct
	public void loadAllCodes() {
		List<Dictionary> list = service.findAll();
		for(Dictionary entity: list) {
			this.setCode("code_test_001", entity.getCode(), entity);
		}
		logger.info("加载数据字典缓存，共计："+list.size());
	}
	
	/**
	 * 将字典数据加载至redis缓存
	 * @param codeType
	 * @param code
	 * @param entity
	 */
	public void setCode(String codeType, String code, Dictionary entity) {
		cacheManager.hset(codeType, code, JSON.toJSONString(entity));
	}
	
	/**
	 * 从redis缓存中获取字典数据
	 * @param codeType
	 * @param code
	 * @return
	 */
	public Dictionary getCode(String codeType, String code) {
		String strCode = cacheManager.hget(codeType, code);
		if(!StringUtils.isEmpty(strCode)) {
			return JSON.parseObject(strCode, Dictionary.class);
		}else {
			return null;
		}
	}
	
	/***************************************************/
	@Autowired
	private DictionaryService service;
	@Autowired
	private CacheManager cacheManager;
	
}