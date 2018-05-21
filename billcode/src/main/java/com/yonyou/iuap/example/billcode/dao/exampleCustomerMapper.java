package com.yonyou.iuap.example.billcode.dao;

import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
import com.yonyou.iuap.example.billcode.entity.exampleCustomer;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

@MyBatisRepository
public interface exampleCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(exampleCustomer record);

    int insertSelective(exampleCustomer record);

    exampleCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(exampleCustomer record);

    int updateByPrimaryKey(exampleCustomer record);

    List<ExampleCustomer> findAll();

    List<ExampleCustomer> findByCode(String code);

    PageResult<ExampleCustomer> selectAllByPage(
            @Param("page") PageRequest pageRequest,
            @Param("search") Map<String, Object> searchParams);

    void batchInsert(List<ExampleCustomer> addList);

    void batchUpdate(List<ExampleCustomer> list);

    void batchDelete(List<ExampleCustomer> list);

    List<ExampleCustomer> selectCustomerByIds(@Param("set") Set<String> ids);
}