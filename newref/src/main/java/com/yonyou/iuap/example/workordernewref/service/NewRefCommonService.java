package com.yonyou.iuap.example.workordernewref.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.workordernewref.dao.NewRefCommonMapper;


@Service
public class NewRefCommonService {

	@Autowired
	private NewRefCommonMapper mapper;
	
	public Page<Map<String,Object>> getGridRefData(PageRequest pageRequest,String tablename,String idfield,String codefield,String namefield,
			Map<String, String> condition, List<String> extColumns,String likefilter) {

		Page<Map<String,Object>> result = mapper.gridrefselectAllByPage(pageRequest,tablename,idfield,codefield,namefield, extColumns,condition,likefilter).getPage();
		
		return result;
	}

	public Page<Map<String, Object>> getTreeRefData(PageRequest pageRequest,
			String tablename, String idfield,String pidfield, Map<String, String> condition,List<String> extColumns) {
		
		Page<Map<String,Object>> result = mapper.treerefselectAllByPage(pageRequest,tablename,idfield,pidfield, extColumns,condition).getPage();
		return result;
	}

	public Page<Map<String, Object>> getTableRefData(PageRequest pageRequest,
			String tablename, String idfield, String codefield,
			String namefield, Map<String, String> condition,
			List<String> extColumns, String likefilter) {
		
        Page<Map<String,Object>> result = mapper.tablerefselectAllByPage(pageRequest,tablename,idfield,codefield,namefield, extColumns,condition,likefilter).getPage();
		return result;
	}
	
	public Page<Map<String, Object>> selectRefTree(PageRequest pageRequest,
			String tablename, String idfield, String pidfield,
			String codefield, String namefield, Map<String, String> condition,List<String> extColumns) {
		
		Page<Map<String,Object>> result = mapper.selectRefTree(pageRequest,tablename,idfield,pidfield,codefield,namefield, extColumns,condition).getPage();
		return result;
	}

}
