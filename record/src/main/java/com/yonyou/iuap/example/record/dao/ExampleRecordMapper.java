package com.yonyou.iuap.example.record.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.record.entity.ExampleRecord;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface ExampleRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExampleRecord record);

    int insertSelective(ExampleRecord record);

    ExampleRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExampleRecord record);

    int updateByPrimaryKey(ExampleRecord record);
    
    
    List<ExampleRecord> findAll();
	 
	 List<ExampleRecord> findByCode(String code);
	 
	 PageResult<ExampleRecord> selectAllByPage(@Param("page") PageRequest pageRequest,@Param("search") Map<String, Object> searchParams);
	 
	 void batchInsert(List<ExampleRecord> addList);
	 
	 void batchUpdate(List<ExampleRecord> list);
	 
	 void batchDelete(List<ExampleRecord> list);

	 List<ExampleRecord> selectInstitByIds( @Param("set") Set<String> ids );
}