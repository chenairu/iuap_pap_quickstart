package com.yonyou.iuap.example.workorder.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;

public interface WorkorderMapper extends GenericMapper<Workorder>{

	public PageResult<Workorder> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") Map<String, Object> map);
	
}
