package com.yonyou.iuap.example.common.service;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.common.entity.GenericEntity;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;

public abstract class GenericService<T extends GenericEntity> {
	
	/**
	 * 分页查询
	 * @param pageRequest
	 * @param searchParams
	 * @return
	 */
    public Page<T> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return ibatisMapper.selectAllByPage(pageRequest, searchParams).getPage();
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<T> findAll(){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	return this.queryList(queryParams);
    }
    
    /**
     * 根据参数查询List
     * @param queryParams
     * @return
     */
    public List<T> queryList(Map<String,Object> queryParams){
    	return this.ibatisMapper.queryList(queryParams);
    }
    
    /**
     * 根据字段名查询List
     * @param name
     * @param value
     * @return
     */
    public List<T> queryList(String name, Object value){
    	Map<String,Object> queryParams = new HashMap<String,Object>();
    	queryParams.put(name, value);
    	return this.queryList(queryParams);
    }
    
    /**
     * 根据参数查询List【返回值为List<Map>】
     * @param params
     * @return
     */
    public List<Map<String,Object>> queryListByMap(Map<String,Object> params){
    	return this.ibatisMapper.queryListByMap(params);
    }

    /**
     * 根据ID查询数据
     * @param id
     * @return
     */
    public T findById(String id) {
    	return this.findUnique("id", id);
    }

    /**
     * 查询唯一数据
     * @param name
     * @param value
     * @return
     */
    public T findUnique(String name, Object value) {
    	List<T> listData = this.queryList(name, value);
    	if(listData!=null && listData.size()==1) {
    		return listData.get(0);
    	}else {
    		throw new RuntimeException("检索数据不唯一, "+name + ":" + value);
    	}
    }
    
    /**
     * 保存数据
     * @param entity
     * @return
     */
	public T save(T entity) {
		if(StringUtils.isEmpty(entity.getId())) {
			return insert(entity);
		}else {
			return update(entity);
		}
	}
	
	/**
	 * 新增保存数据
	 * @param entity
	 * @return
	 */
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

	/**
	 * 更新保存数据
	 * @param entity
	 * @return
	 */
	public T update(T entity) {
		entity.setDr(0);
		entity.setLastModified(new Date());
		entity.setLastModifyUser(InvocationInfoProxy.getUserid());
		ibatisMapper.update(entity);
		return entity;
	}

	/**
	 * 删除数据
	 * @param entity
	 * @return
	 */
	public int delete(T entity) {
		return this.delete(entity.getId());
	}
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public int delete(String id) {
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("id", id);
		data.put("dr", 1);
		return ibatisMapper.delete(data);
	}

	/***************************************************/
	protected GenericMapper<T> ibatisMapper;

	public void setIbatisMapper(GenericMapper<T> ibatisMapper) {
		this.ibatisMapper = ibatisMapper;
	}

}