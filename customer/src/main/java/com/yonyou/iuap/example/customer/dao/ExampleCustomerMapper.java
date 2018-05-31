package com.yonyou.iuap.example.customer.dao;

import com.yonyou.iuap.example.customer.entity.ExampleCustomer;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

@MyBatisRepository
public interface ExampleCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExampleCustomer record);

    int insertSelective(ExampleCustomer record);

    ExampleCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExampleCustomer record);

    int updateByPrimaryKey(ExampleCustomer record);

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