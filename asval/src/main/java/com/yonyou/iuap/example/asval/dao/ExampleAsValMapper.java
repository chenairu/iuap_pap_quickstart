package com.yonyou.iuap.example.asval.dao;

import com.yonyou.iuap.example.asval.entity.ExampleAsVal;

public interface ExampleAsValMapper {
    int insert(ExampleAsVal record);

    int insertSelective(ExampleAsVal record);
}