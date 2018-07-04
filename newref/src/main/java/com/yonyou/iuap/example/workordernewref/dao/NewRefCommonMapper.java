package com.yonyou.iuap.example.workordernewref.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

/**
 * 查询参照数据mybatis
 * @author xiadlc
 *
 * 
 */
@MyBatisRepository
public interface NewRefCommonMapper {

	/**
	 * 列表型参照数据查询
	 * @param pageRequest
	 * @param tablename
	 * @param idfield
	 * @param codefield
	 * @param namefield
	 * @param extcols
	 * @param condmap
	 * @return
	 */
	PageResult<Map<String,Object>> gridrefselectAllByPage(
			@Param("page") PageRequest pageRequest,
			@Param("tablename") String tablename,
			@Param("idfield") String idfield,
			@Param("codefield") String codefield,
			@Param("namefield") String namefield,
			@Param("extcols") List<String> extcols,
			@Param("condmap") Map<String,String> condmap,
			@Param("likefilter") String likefilter);
	
	/**
	 * 列表型参照数据查询(不加默认的dr条件)
	 * @param pageRequest
	 * @param tablename
	 * @param idfield
	 * @param codefield
	 * @param namefield
	 * @param extcols
	 * @param condmap
	 * @return
	 */
	PageResult<Map<String,Object>> tablerefselectAllByPage(
			@Param("page") PageRequest pageRequest,
			@Param("tablename") String tablename,
			@Param("idfield") String idfield,
			@Param("codefield") String codefield,
			@Param("namefield") String namefield,
			@Param("extcols") List<String> extcols,
			@Param("condmap") Map<String,String> condmap,
			@Param("likefilter") String likefilter);
	
	
	/**
	 * 树表型参照--表的数据查询
	 * @param pageRequest
	 * @param tablename
	 * @param idfield
	 * @param codefield
	 * @param namefield
	 * @param extcols
	 * @param condmap
	 * @return
	 */
	PageResult<Map<String,Object>> treerefselectAllByPage(
			@Param("page") PageRequest pageRequest,
			@Param("tablename") String tablename,
			@Param("idfield") String idfield,
			@Param("extcols") List<String> extcols,
			@Param("condmap") Map<String,String> condmap);
	/**
	 * 根据ids批量查询
	 * @param pageRequest
	 * @param tablename
	 * @param idfield
	 * @param codefield
	 * @param namefield
	 * @param extcols
	 * @param condmap
	 * @return
	 */
	List<Map<String, Object>> findUserListByIds(
			@Param("tablename") String tablename,
			@Param("idfield") String idfield,
			@Param("extcols") List<String> extcols,
			@Param("ids") List<String> ids);
	
	/**
	 * 树表型参照--树的数据查询
	 * @param pageRequest
	 * @param tablename
	 * @param idfield
	 * @param codefield
	 * @param namefield
	 * @param extColumns
	 * @param condition
	 * @return
	 */
	PageResult<Map<String,Object>> selectRefTree(
			@Param("page") PageRequest pageRequest,
			@Param("tablename") String tablename,
			@Param("idfield") String idfield,
			@Param("pidfield") String pidfield,
			@Param("codefield") String codefield,
			@Param("namefield") String namefield,
			@Param("extcols") List<String> extcols,
			@Param("condmap") Map<String, String> condmap);
}
