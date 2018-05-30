package com.yonyou.iuap.example.contacts.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrganizationMapper extends GenericMapper<Organization>{
	
	public List<Organization> findAll();
	 
	public List<Organization> findByFid(String id);
	 
	public List<Organization> findByCode(String code);
	 
	public void batchInsert(List<Organization> addList);
	 
	public void batchUpdate(List<Organization> list);
	 
	public void batchDelete(List<Organization> list);

	public List<Organization> selectInstitByIds( @Param("set") Set<String> ids );
	
}