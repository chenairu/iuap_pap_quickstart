package com.yonyou.iuap.example.common.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.common.entity.GenericEntity;
import com.yonyou.iuap.mvc.type.SearchParams;

public abstract class GenericService<T extends GenericEntity> {
	
    public Page<T> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return ibatisMapper.selectAllByPage(pageRequest, searchParams).getPage();
    }
    
    public T getById(String id) {
    	Map<String,Object> params = new HashMap<String, Object>();
    	params.put("id", id);
    	List<T> listResult = ibatisMapper.queryList(params);
    	if(listResult.size() == 1) {
    		return listResult.get(0);
    	}else {
    		throw new RuntimeException("根据ID获取数据出错:id="+id);
    	}
    }

	public T save(T entity) {
		if(StringUtils.isEmpty(entity.getId())) {
			return insert(entity);
		}else {
			return update(entity);
		}
	}
	
	public T insert(T entity) {
		entity.setId(UUID.randomUUID().toString());
		entity.setCreateTime(new Date());
		entity.setCreateUser(InvocationInfoProxy.getUserid());
		entity.setLastModified(new Date());
		entity.setLastModifyUser(InvocationInfoProxy.getUserid());
		entity.setDr(0);
		entity.setTs(new Date());
		ibatisMapper.insert(entity);
		return entity;
	}

	public T update(T entity) {
		entity.setDr(0);
		entity.setLastModified(new Date());
		entity.setLastModifyUser(InvocationInfoProxy.getUserid());
		ibatisMapper.update(entity);
		return entity;
	}
	
	public int delete(String id) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("id", id);
		data.put("dr", 1);
		return ibatisMapper.delete(data);
	}

	public int delete(T entity) {
		entity.setDr(1);
		entity.setLastModified(new Date());
		entity.setLastModifyUser(InvocationInfoProxy.getUserid());
		return ibatisMapper.update(entity);
	}
	
	/***************************************************/
	protected GenericMapper<T> ibatisMapper;

}