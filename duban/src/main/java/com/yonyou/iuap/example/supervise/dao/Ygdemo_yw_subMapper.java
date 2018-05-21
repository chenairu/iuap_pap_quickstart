package com.yonyou.iuap.example.supervise.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.mybatis.type.PageResult;

//@MyBatisRepository
public interface  Ygdemo_yw_subMapper {
	
	int insert(Ygdemo_yw_sub entity);
	
	int update(Ygdemo_yw_sub entity);
	
	int batchDeleteByPK(List<Ygdemo_yw_sub> list);
	
	int batchDeleteByFK(List<Ygdemo_yw_sub> list);
	
	PageResult<Ygdemo_yw_sub> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	List<Ygdemo_yw_sub> findYgdemo_yw_subByFK(String fk_id_ygdemo_yw_sub);
	
}