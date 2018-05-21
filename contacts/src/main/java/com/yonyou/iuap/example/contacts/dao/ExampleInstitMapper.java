package com.yonyou.iuap.example.contacts.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.contacts.entity.ExampleInstit;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface ExampleInstitMapper {
	
	 List<ExampleInstit> findAll();
	 
	 List<ExampleInstit> findByFid(String id);
	 
	 List<ExampleInstit> findByCode(String code);
	 
	 PageResult<ExampleInstit> selectAllByPage(@Param("page") PageRequest pageRequest,@Param("search") Map<String, Object> searchParams);
	 
	 void batchInsert(List<ExampleInstit> addList);
	 
	 void batchUpdate(List<ExampleInstit> list);
	 
	 void batchDelete(List<ExampleInstit> list);

	 List<ExampleInstit> selectInstitByIds( @Param("set") Set<String> ids );
}
