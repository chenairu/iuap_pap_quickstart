package com.yonyou.iuap.example.common.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.common.entity.GenericEntity;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;

public interface GenericMapper<T extends GenericEntity> {
	
	public PageResult<T> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") SearchParams searchParams);
	
	public List<T> queryList(@Param("condition")Map<String,Object> params);

	public List<Map<String,Object>> queryListByMap(@Param("condition")Map<String,Object> params);

	public int insert(T entity);
	
	public int update(T entity);

	public int delete(@Param("condition")Map<String,Object> params);
	
}