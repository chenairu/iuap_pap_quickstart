package com.yonyou.iuap.example.supervise.utils;

import java.net.Socket;
import java.util.Map;
import java.text.*;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.mvc.type.SearchParams;

public class CommonUtils {
	
    private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

    private static String localIp = null;

    public static String getLocalIp() {
        final String TEST_DOMAIN = "www.baidu.com";
        final String LOCALHOST = "127.0.0.1";
        int TEST_TIMEOUT = 2 * 1000;

        if (localIp != null) {
            return localIp;
        }

        final Outer<String> address = new Outer<String>();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket(TEST_DOMAIN, 80);
                    String ip = s.getLocalAddress().getHostAddress();
                    s.close();
                    address.setValue(ip);
                } catch (Exception ex) {
                    address.setValue(LOCALHOST);
                }
            }
        });

        thread.start();

        try {
            thread.join(TEST_TIMEOUT);
        } catch (InterruptedException ex) {
        } catch (Exception ex) {
        }

        if (localIp == null) {
            localIp = address.value() != null ? address.value() : LOCALHOST;
        }

        return localIp;
    }

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

    /**
     * 云打印时候格式化数据用
     * 父表
     */
    public static JSONObject formatDataForPrint(Ygdemo_yw_info vo, String attr, JSONObject mainData) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        switch (attr) {
            case "zb_dw":
                mainData.put(attr, vo.getAttribute("zb_dw_name"));
                break;
            case "zr_dw":
                mainData.put(attr, vo.getAttribute("zr_dw_name"));
                break;
            case "xb_dw":
                mainData.put(attr, vo.getAttribute("xb_dw_name"));
                break;
            case "zbr":
                mainData.put(attr, vo.getAttribute("zbr_name"));
                break;
            case "create_name":
                mainData.put(attr, vo.getAttribute("create_name_name"));
                break;
            case "update_name":
                mainData.put(attr, vo.getAttribute("update_name_name"));
                break;
            case "unitid":
                mainData.put(attr, vo.getAttribute("unitid_name"));
                break;
            case "begin_date":
                if (null!=vo.getAttribute("begin_date")){
                    String begin_date = formatter.format(vo.getAttribute("begin_date"));
                    mainData.put(attr, begin_date);
            }
                break;
            case "end_date":
                if (null!=vo.getAttribute("end_date")){
                    String end_date = formatter.format(vo.getAttribute("end_date"));
                    mainData.put(attr, end_date);
                }
                break;
            case "create_time":
                if (null!=vo.getAttribute("create_time")){
                    String create_time = formatter.format(vo.getAttribute("create_time"));
                    mainData.put(attr, create_time);
                }
                break;
            case "update_time":
                if(null!=vo.getAttribute("update_time")){
                    String update_time = formatter.format(vo.getAttribute("update_time"));
                    mainData.put(attr, update_time);
                }
                break;

            case "ly_code"://督办来源
                if (vo.getAttribute("ly_code").equals("1")) {
                    mainData.put(attr, "领导交办");
                } else if (vo.getAttribute("ly_code").equals("2")) {
                    mainData.put(attr, "会议纪要");
                } else {
                    mainData.put(attr, "其他");
                }
                break;

            case "zy_cd"://重要程度
                if (vo.getAttribute("zy_cd").equals("1")) {
                    mainData.put(attr, "重要");
                } else {
                    mainData.put(attr, "一般");
                }
                break;

            case "kpi_flag"://是否KPI
                if (vo.getAttribute("kpi_flag").equals("1")) {
                    mainData.put(attr, "是");
                } else {
                    mainData.put(attr, "否");
                }
                break;
            case "kpi_level":
                if (vo.getAttribute("kpi_level").equals("1")) {
                    mainData.put(attr, "是");
                } else {
                    mainData.put(attr, "否");
                }
                break;
            case "state":
                if (vo.getAttribute("state").equals("1")) {
                    mainData.put(attr, "执行中");
                } else if (vo.getAttribute("state").equals("2")) {
                    mainData.put(attr, "已办结");
                } else if (vo.getAttribute("state").equals("3")) {
                    mainData.put(attr, "终止");
                } else {
                    mainData.put(attr, "待确认");
                }
                break;
            default:
                break;
        }
        return mainData;

    }

    /**
     * 云打印时候格式化数据用
     * 子表
     */
    public static JSONObject formatSubDataForPrint(Ygdemo_yw_sub vo, String attr, JSONObject mainData) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        switch (attr) {
            case "zb_dw":
                mainData.put(attr, vo.getAttribute("zb_dw_name"));
                break;
            case "zr_dw":
                mainData.put(attr, vo.getAttribute("zr_dw_name"));
                break;
            case "xb_dw":
                mainData.put(attr, vo.getAttribute("xb_dw_name"));
                break;
            case "zbr":
                mainData.put(attr, vo.getAttribute("zbr_name"));
                break;
            case "create_name":
                mainData.put(attr, vo.getAttribute("create_name_name"));
                break;
            case "update_name":
                mainData.put(attr, vo.getAttribute("update_name_name"));
                break;
            case "unitid":
                mainData.put(attr, vo.getAttribute("unitid_name"));
                break;
            case "begin_date":
                if (null!=vo.getAttribute("begin_date")){
                    String begin_date = formatter.format(vo.getAttribute("begin_date"));
                    mainData.put(attr, begin_date);
                }
                break;
            case "end_date":
                if (null!=vo.getAttribute("end_date")){
                    String end_date = formatter.format(vo.getAttribute("end_date"));
                    mainData.put(attr, end_date);
                }
                break;
            case "create_time":
                if (null!=vo.getAttribute("create_time")){
                    String create_time = formatter.format(vo.getAttribute("create_time"));
                    mainData.put(attr, create_time);
                }

                break;
            case "update_time":
                if (null!=vo.getAttribute("update_time")){
                    String update_time = formatter.format(vo.getAttribute("update_time"));
                    mainData.put(attr, update_time);
                }
                break;

            case "ly_code"://督办来源
                if (vo.getAttribute("ly_code").equals("1")) {
                    mainData.put(attr, "领导交办");
                } else if (vo.getAttribute("ly_code").equals("2")) {
                    mainData.put(attr, "会议纪要");
                } else {
                    mainData.put(attr, "其他");
                }
                break;

            case "zy_cd"://重要程度
                if (vo.getAttribute("zy_cd").equals("1")) {
                    mainData.put(attr, "重要");
                } else {
                    mainData.put(attr, "一般");
                }
                break;

            case "kpi_flag"://是否KPI
                if (vo.getAttribute("kpi_flag").equals("1")) {
                    mainData.put(attr, "是");
                } else {
                    mainData.put(attr, "否");
                }
                break;
            case "kpi_level":
                if (vo.getAttribute("kpi_level").equals("1")) {
                    mainData.put(attr, "是");
                } else {
                    mainData.put(attr, "否");
                }
                break;
            case "state":
                if (vo.getAttribute("state").equals("1")) {
                    mainData.put(attr, "执行中");
                } else if (vo.getAttribute("state").equals("2")) {
                    mainData.put(attr, "已办结");
                } else if (vo.getAttribute("state").equals("3")) {
                    mainData.put(attr, "终止");
                } else {
                    mainData.put(attr, "待确认");
                }
                break;
            default:
                break;
        }
        return mainData;

    }
}
