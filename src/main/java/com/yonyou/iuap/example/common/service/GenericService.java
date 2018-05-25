package com.yonyou.iuap.example.common.service;

import java.util.*;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
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

    public List<T> findAll(){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	return this.queryList(queryParams);
    }
    
    public List<T> queryList(Map<String,Object> queryParams){
    	return this.ibatisMapper.queryList(queryParams);
    }
    
    public List<T> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    public T findUnique(String name, Object value) {
    	List<T> listData = this.queryList(name, value);
    	if(listData!=null && listData.size()==1) {
    		return listData.get(0);
    	}else {
    		throw new RuntimeException("检索数据不唯一, "+name + ":" + value);
    	}
    }
    
    public T findById(String id) {
    	return this.findUnique("id", id);
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

	public void setIbatisMapper(GenericMapper<T> ibatisMapper) {
		this.ibatisMapper = ibatisMapper;
	}

}