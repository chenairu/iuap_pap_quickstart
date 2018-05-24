package com.yonyou.iuap.example.asval.dao;

import com.yonyou.iuap.example.asval.entity.ComboboxEntity;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisRepository
public interface ExampleAsValMapper {
    int insert(ExampleAsVal record);

    int insertSelective(ExampleAsVal record);

    ExampleAsVal selectByPrimaryKey(ExampleAsVal exampleAsVal);

    List<ExampleAsVal> findAll();

    List<ComboboxEntity> findProvince(String valsetId);

    List<ExampleAsVal> getByIds(String tenantId, @Param("list") List<String> ids);

}