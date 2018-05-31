package com.yonyou.iuap.example.common.utils;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.mvc.type.SearchParams;

public class CommonUtils {
	
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static String localIp = null;

    public static void decode(SearchParams searchParams) {
        try {
            for (Map.Entry<String, Object> entry : searchParams.getSearchMap().entrySet()) {
                entry.setValue(java.net.URLDecoder.decode((String) entry.getValue(), "UTF-8"));
            }
        } catch (Exception ex) {
        }
    }

    public static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写

        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加

        String[] badStrs = badStr.split("\\|");

        for (int i = 0; i < badStrs.length; i++) {
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }

        return false;
    }



}
