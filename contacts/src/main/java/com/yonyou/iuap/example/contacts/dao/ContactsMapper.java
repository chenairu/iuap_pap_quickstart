package com.yonyou.iuap.example.contacts.dao;

import com.yonyou.iuap.example.contacts.entity.Contacts;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;


@MyBatisRepository
public interface ContactsMapper {
	
	List<Contacts> findByCode( String peocode );

	PageResult<Contacts> selectAllByPage(@Param("page") PageRequest pageRequest ,@Param("condition") Map<String, Object> searchParams );
	//PageResult<Contacts> selectAllByPage(@Param("page") PageRequest pageRequest ,@Param("condition") SearchParams searchParams );
	
	List<Contacts> queryList(@Param("condition") Map<String,Object> params);
	
	void batchInsert( List<Contacts> addList );
	
	void batchUpdate( List<Contacts> updateList );
	
	void batchDelete( List<Contacts> list );
	
	List<Contacts> findByid( String institid );
	
}