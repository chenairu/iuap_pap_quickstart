package com.yonyou.iuap.example.sysRefer.dao;

import com.yonyou.iuap.example.sysRefer.entity.ExampleEquip;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface ExampleEquipMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExampleEquip record);

    int insertSelective(ExampleEquip record);
    
    int batchInsert(List<ExampleEquip> exampleEquips);
    int batchUpdate(List<ExampleEquip> exampleEquips);

    ExampleEquip selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExampleEquip record);

    int updateByPrimaryKey(ExampleEquip record);

    PageResult<ExampleEquip> selectAllByPage(
            @Param("page") PageRequest pageRequest,
            @Param("search") Map<String, Object> searchParams,
            @Param("sql") String sql);

    List<ExampleEquip> findByCode(String code);
}