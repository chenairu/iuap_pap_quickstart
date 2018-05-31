package com.yonyou.iuap.example.article.dao;

import com.yonyou.iuap.example.article.entity.ExampleArticle;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

@MyBatisRepository
public interface ExampleArticleMapper {
    int insert(ExampleArticle record);

    int insertSelective(ExampleArticle record);

    PageResult<ExampleArticle> selectAllByPage(@Param("page") PageRequest pageRequest,
                                               @Param("search") Map<String, Object> searchParams);
    int deleteByPrimaryKey(String id);

    int updateByPrimaryKey(ExampleArticle exampleArticle);
}