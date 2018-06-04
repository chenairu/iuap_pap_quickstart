package com.yonyou.iuap.example.asyncOrg.dao;

import com.yonyou.iuap.example.asyncOrg.entity.ExampleTree;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

@MyBatisRepository
public interface ExampleTreeMapper {

    PageResult<ExampleTree> selectAllByPage(@Param("page") PageRequest pageRequest,
                                            @Param("search") Map<String, Object> searchParams);
}
