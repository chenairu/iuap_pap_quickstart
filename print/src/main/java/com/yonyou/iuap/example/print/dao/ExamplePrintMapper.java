package com.yonyou.iuap.example.print.dao;

import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.print.entity.ExamplePrint;

import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;


@MyBatisRepository
public interface ExamplePrintMapper {
	
	//单个增删改查
	int insert(ExamplePrint record);

	int insertSelective(ExamplePrint record);
	
	int updateByPrimaryKeySelective(ExamplePrint record);

    int updateByPrimaryKey(ExamplePrint record);

    int deleteByPrimaryKey(String pk);

    ExamplePrint selectByPrimaryKey(String pk);
    
    PageResult<ExamplePrint> getByIds(List<String> ids);
    
    
//	PageResult<ExamplePrint> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	PageResult<ExamplePrint> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") Map<String, Object> searchParams);
	
    
   //批量操作
    void batchInsert(List<ExamplePrint> addList);

    void batchUpdate(List<ExamplePrint> updateList);

    void batchDeleteByPrimaryKey(List<ExamplePrint> list);
    
}
