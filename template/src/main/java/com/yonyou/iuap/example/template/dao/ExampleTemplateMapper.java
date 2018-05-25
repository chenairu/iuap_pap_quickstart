package com.yonyou.iuap.example.template.dao;
import com.yonyou.iuap.example.template.entity.ExampleTemplate;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@MyBatisRepository
public interface ExampleTemplateMapper {
	
	//单个增删改查
	int insert(ExampleTemplate record);
	
	int update(ExampleTemplate record);

	int insertSelective(ExampleTemplate record);
	
	int updateByPrimaryKeySelective(ExampleTemplate record);

    int updateByPrimaryKey(ExampleTemplate record);

    int deleteByPrimaryKey(String pk);

    ExampleTemplate selectByPrimaryKey(String pk);
    
    PageResult<ExampleTemplate> getByIds(List<String> ids);
    
    List<String> getIds();
    
	PageResult<ExampleTemplate> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
    
   //批量操作
    void batchInsert(List<ExampleTemplate> addList);

    void batchUpdate(List<ExampleTemplate> updateList);

    void batchDeleteByPrimaryKey(List<ExampleTemplate> list);
    
}
