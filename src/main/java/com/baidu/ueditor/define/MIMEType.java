
package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
  * @description TODO 添加类/接口功能描述
  * @author 姚春雷
  * @date 2018年3月17日 下午9:23:28
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class MIMEType {

    @SuppressWarnings("serial")
    public static final Map<String, String> types = new HashMap<String, String>() {
        {
            put("image/gif", ".gif");
            put("image/jpeg", ".jpg");
            put("image/jpg", ".jpg");
            put("image/png", ".png");
            put("image/bmp", ".bmp");
        }
    };

    public static String getSuffix(String mime) {
        return MIMEType.types.get(mime);
    }
}
