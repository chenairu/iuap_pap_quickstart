package com.yonyou.iuap.example.sanyorder.util;

import com.alibaba.fastjson.JSONObject;

public class WorkFlowUtils {
	
	public static boolean isSuccess(JSONObject resultJson){
		return resultJson.get("flag")!=null && resultJson.get("flag").equals("success") || 
				resultJson.get("success")!=null && resultJson.get("success").equals("success");
	}
	
	public static boolean isFail(JSONObject resultJson){
		return resultJson.get("flag")!=null && !resultJson.get("flag").equals("success");
	}

}