package com.yonyou.iuap.example.contacts.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface OrganizationMapper {
	
	 List<Organization> findAll();
	 
	 List<Organization> findByFid(String id);
	 
	 List<Organization> findByCode(String code);
	 
	 PageResult<Organization> selectAllByPage(@Param("page") PageRequest pageRequest,@Param("search") Map<String, Object> searchParams);
	 
	 void batchInsert(List<Organization> addList);
	 
	 void batchUpdate(List<Organization> list);
	 
	 void batchDelete(List<Organization> list);

	 List<Organization> selectInstitByIds( @Param("set") Set<String> ids );
}
