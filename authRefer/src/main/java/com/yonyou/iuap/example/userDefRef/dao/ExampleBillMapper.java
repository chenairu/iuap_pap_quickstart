package com.yonyou.iuap.example.userDefRef.dao;

import com.yonyou.iuap.example.userDefRef.entity.ExampleBill;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

@MyBatisRepository
public interface ExampleBillMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExampleBill record);

    int insertSelective(ExampleBill record);

    ExampleBill selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExampleBill record);

    int updateByPrimaryKey(ExampleBill record);

    PageResult<ExampleBill> selectAllByPage(
            @Param("page") PageRequest pageRequest,
            @Param("search") Map<String, Object> searchParams,
            @Param("sql") String sql);
}