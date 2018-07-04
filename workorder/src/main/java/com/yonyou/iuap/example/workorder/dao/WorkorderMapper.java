package com.yonyou.iuap.example.workorder.dao;

import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface WorkorderMapper extends GenericExMapper<Workorder> {

}
