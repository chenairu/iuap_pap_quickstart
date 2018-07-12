package com.yonyou.iuap.example.sanyorder.dao;

import com.yonyou.iuap.baseservice.persistence.mybatis.mapper.GenericExMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrderContract;
import com.yonyou.iuap.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface SanyOrderContractMapper extends GenericExMapper<SanyOrderContract> {
}
