package com.yonyou.iuap.example.contacts.dao;

import com.yonyou.iuap.example.common.dao.GenericMapper;
import com.yonyou.iuap.example.contacts.entity.Contacts;
import com.yonyou.iuap.persistence.mybatis.anotation.MyBatisRepository;

import java.util.List;


@MyBatisRepository
public interface ContactsMapper extends GenericMapper<Contacts>{
	
	List<Contacts> findByCode( String peocode );

	void batchInsert( List<Contacts> addList );
	
	void batchUpdate( List<Contacts> updateList );
	
	void batchDelete( List<Contacts> list );
	
	List<Contacts> findByid( String institid );
	
}