package com.yonyou.iuap.example.attachment.dao;

import com.yonyou.iuap.example.attachment.entity.ExampleAttachment;
import com.yonyou.iuap.example.template.entity.ExampleTemplate;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;


@MyBatisRepository
public interface ExampleAttachmentMapper {
	
	//单个增删改查
	int insert(ExampleAttachment record);

	int insertSelective(ExampleAttachment record);
	
	int updateByPrimaryKeySelective(ExampleAttachment record);

    int updateByPrimaryKey(ExampleAttachment record);

    int deleteByPrimaryKey(String pk);

    ExampleAttachment selectByPrimaryKey(String pk);
    
    
	//PageResult<ExampleAttachment> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
    
	PageResult<ExampleAttachment> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") Map<String, Object> searchParams);
	
   //批量操作
    void batchInsert(List<ExampleAttachment> addList);

    void batchUpdate(List<ExampleAttachment> updateList);

    void batchDeleteByPrimaryKey(List<ExampleAttachment> list);
    
}
