package com.yonyou.iuap.example.supervise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.yonyou.iuap.example.supervise.dao.Ygdemo_yw_subMapper;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.mvc.type.SearchParams;

@Service
public class Ygdemo_yw_subService {

	@Autowired
	private Ygdemo_yw_subMapper ywSubMapper;
	
	@Autowired
	private Ygdemo_Ref_Service ygdemo_Ref_Service;

	public Page<Ygdemo_yw_sub> selectAllByPage(PageRequest pageRequest,
			SearchParams searchParams) {

		Page<Ygdemo_yw_sub> pageResult = ywSubMapper.selectAllByPage(
				pageRequest, searchParams).getPage();

		if (pageResult != null && pageResult.getContent() != null
				&& pageResult.getContent().size() > 0) {
			this.setRefName(pageResult.getContent());
		}

		return pageResult;
	}

	/** 参照id和显示字段 这里进行转换 */
	public void setRefName(List<Ygdemo_yw_sub> subList) {
		String url = "/wbalone/userRest/getByIds";
		String url2 = "/basedoc/peopledocRef/getByIds";
		
		List<String> listCreate_name = new ArrayList<String>();
		List<String> listUpdate_name = new ArrayList<String>();
		List<String> listZbr = new ArrayList<String>();
		
		for (Ygdemo_yw_sub item : subList) {				
			if (item.getCreate_name() != null && !listCreate_name.contains(item.getCreate_name()))
			{
				listCreate_name.add(item.getCreate_name());
			}
			
			if (item.getUpdate_name() != null && !listUpdate_name.contains(item.getUpdate_name()))
			{
				listUpdate_name.add(item.getUpdate_name());
			}
			
			if (item.getZbr() != null && !listZbr.contains(item.getZbr()))
			{
				listZbr.add(item.getZbr());
			}
		}
		
		Map<String, String> mapParameter1 = new ConcurrentHashMap<String, String>();
		mapParameter1.put("tenantId", "tenant");
		mapParameter1.put("userIds", JSONArray.toJSON(listCreate_name).toString());
		
		Map<String, String> mapParameter2 = new ConcurrentHashMap<String, String>();
		mapParameter2.put("tenantId", "tenant");
		mapParameter2.put("userIds", JSONArray.toJSON(listUpdate_name).toString());
		
		Map<String, String> mapParameter3 = new ConcurrentHashMap<String, String>();
		mapParameter3.put("tenantId", "tenant");
		mapParameter3.put("data", JSONArray.toJSON(listZbr).toString());
		
		Map<String, String> mapCreate_name = ygdemo_Ref_Service.convertRefName(url, mapParameter1);
		Map<String, String> mapUpdate_name = ygdemo_Ref_Service.convertRefName(url, mapParameter2);
		Map<String, String> mapZbr = ygdemo_Ref_Service.convertRefName(url2, mapParameter3);
		
		for (Ygdemo_yw_sub item : subList) {				
			if (item.getCreate_name() != null)
			{				
				item.setCreate_name_name(mapCreate_name.get(item.getCreate_name()));
			}
			
			if (item.getUpdate_name() != null)
			{
				item.setUpdate_name_name(mapUpdate_name.get(item.getUpdate_name()));
			}
			
			if (item.getZbr() != null)
			{
				item.setZbr_name(mapZbr.get(item.getZbr()));
			}
		}
	}

	public void deleteEntity(Ygdemo_yw_sub entity) {
		List<Ygdemo_yw_sub> list = new ArrayList<Ygdemo_yw_sub>();
		list.add(entity);

		ywSubMapper.batchDeleteByPK(list);
	}

}
