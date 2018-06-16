
package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
  * @description TODO 添加类/接口功能描述
  * @author 姚春雷
  * @date 2018年3月17日 下午9:16:51
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@SuppressWarnings("serial")
public class ActionMap {

    public static final Map<String, Integer> mapping;
    //获取配置请求
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    static {
        mapping = new HashMap<String, Integer>() {
            {
                put("config", ActionMap.CONFIG);
                put("uploadimage", ActionMap.UPLOAD_IMAGE);
                put("uploadscrawl", ActionMap.UPLOAD_SCRAWL);
                put("uploadvideo", ActionMap.UPLOAD_VIDEO);
                put("uploadfile", ActionMap.UPLOAD_FILE);
                put("catchimage", ActionMap.CATCH_IMAGE);
                put("listfile", ActionMap.LIST_FILE);
                put("listimage", ActionMap.LIST_IMAGE);
            }
        };
    }

    public static int getType(String key) {
        return ActionMap.mapping.get(key);
    }
}
