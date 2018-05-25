package com.yonyou.iuap.example.dictionary.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

@MyBatisRepository
public interface DictionaryMapper extends GenericMapper<Dictionary> {
	
	PageResult<Dictionary> selectAllByPage(@Param("page") PageRequest pageRequest,
			@Param("condition") Map<String, Object> searchParams);
	
	List<Dictionary> queryList(@Param("condition") Map<String,Object> params);

	void batchInsert(List<Dictionary> addList);

	void batchUpdate(List<Dictionary> list);

	void batchDelete(List<Dictionary> list);

	List<Dictionary> selectInstitByIds(@Param("set") Set<String> ids);
	
	int deleteByPrimaryKey(String id);

	int insert(Dictionary record);

	int insertSelective(Dictionary record);

	Dictionary selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Dictionary record);

	int updateByPrimaryKey(Dictionary record);

	List<Dictionary> findAll();

	List<Dictionary> findByCode(String code);
	 
}