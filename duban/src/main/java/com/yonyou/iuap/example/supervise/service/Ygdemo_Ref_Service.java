package com.yonyou.iuap.example.supervise.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.utils.PropertyUtil;

import iuap.ref.base.context.RefPlatform;

@Service
public class Ygdemo_Ref_Service {
	
	private Logger logger = LoggerFactory.getLogger(Ygdemo_yw_infoService.class);
	
	public Map<String, String> convertRefName(String url, Map<String, String> mapParameter) {
		Map<String, String> mapRefName = new ConcurrentHashMap<String, String>();
		
		try
		{			
			if (mapParameter != null && mapParameter.size() > 0)
			{
				int index = 0;
			
				for (Map.Entry<String, String> entry : mapParameter.entrySet()) {			    
				    String joinSymbol = null;
				    
				    if (index == 0)
				    {
				    	joinSymbol = "?";
				    }
				    else
				    {
				    	joinSymbol = "&";
				    }
				    
				    url = String.format("%s%s%s=%s", url, joinSymbol, entry.getKey(), entry.getValue());
				    
				    index++;
				}
				
				url = PropertyUtil.getPropertyByKey("base.url") + url;
				
				JSONObject getbillcodeinfo = RestUtils.getInstance().doPost(url, null, JSONObject.class);
				
				Map<String, Object> mapJson = (Map<String, Object>)JSON.parse(getbillcodeinfo.toString());
				
				JSONArray array = (JSONArray)mapJson.get("data");
				
				for(Object obj : array)
				{
					Map<String, String> map = (Map<String, String>)obj;
					
					mapRefName.put(map.get("id"), map.get("name"));
				}
				
				return mapRefName;
				
			}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			
			mapRefName.clear();
		}
		
		return mapRefName;
	}

}
