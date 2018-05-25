package com.yonyou.iuap.example.reference.goods.dao;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.reference.goods.entity.Goods;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

@MyBatisRepository
public interface GoodsMapper extends GenericMapper<Goods>{
    public PageResult<Goods> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("condition") SearchParams searchParams, @Param("sql") String sql);

}