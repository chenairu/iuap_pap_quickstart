package com.yonyou.iuap.example.asval.dao;

import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;
import com.yonyou.iuap.mybatis.type.PageResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface ExampleAsValMapper {
    int insert(ExampleAsVal record);

    int insertSelective(ExampleAsVal record);

    int updateByPrimayKey(ExampleAsVal exampleAsVal);

    int deleteByPrimayKey(String id);

    ExampleAsVal selectByPrimaryKey(ExampleAsVal exampleAsVal);

    List<ExampleAsVal> findAll();

    List<ExampleAsVal> findProvince(String valsetId);

    List<ExampleAsVal> getByIds(String tenantId,@Param("list") List<String> ids);

    List<ExampleAsVal> queryByClause(@Param("keyword") String keyword);

    PageResult<ExampleAsVal> selectAllByPage(@Param("page")PageRequest pageRequest, @Param("search") SearchParams searchParams);
}