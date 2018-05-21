package com.yonyou.iuap.example.supervise.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.mybatis.type.PageResult;


//@MyBatisRepository
public interface Ygdemo_yw_infoMapper {
	
	int insert(Ygdemo_yw_info entity);
	
	int update(Ygdemo_yw_info entity);
	
	int batchDeleteByPK(List<Ygdemo_yw_info> list);
	
	PageResult<Ygdemo_yw_info> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") Map<String, Object> search, @Param("querySql") String querySql);

	//PageResult<Ygdemo_yw_info> selectAllByPage(@Param("page") PageRequest pageRequest, @Param("search") SearchParams searchParams);
	
	PageResult<Ygdemo_yw_info> getByIds(List<String> ids);
	
}