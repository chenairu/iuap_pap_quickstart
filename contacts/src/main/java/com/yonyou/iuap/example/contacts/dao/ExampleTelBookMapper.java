package com.yonyou.iuap.example.contacts.dao;

import com.yonyou.iuap.example.contacts.entity.ExampleTelBook;
import com.yonyou.iuap.mybatis.type.PageResult;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;


@MyBatisRepository
public interface ExampleTelBookMapper {
	
	List<ExampleTelBook> findByCode( String peocode );

	PageResult<ExampleTelBook> selectAllByPage(@Param("page") PageRequest pageRequest ,@Param("search") Map<String, Object> searchParams );
	
	void batchInsert( List<ExampleTelBook> addList );
	
	void batchUpdate( List<ExampleTelBook> updateList );
	
	void batchDelete( List<ExampleTelBook> list );
	
	List<ExampleTelBook> findByid( String institid );
	
}