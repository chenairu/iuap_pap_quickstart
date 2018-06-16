
package com.baidu.ueditor.define;

import java.util.HashMap;
import java.util.Map;

/**
  * @description TODO 添加类/接口功能描述
  * @author 姚春雷
  * @date 2018年3月17日 下午9:22:03
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class FileType {

    public static final String JPG = "JPG";

    @SuppressWarnings("serial")
    private static final Map<String, String> types = new HashMap<String, String>() {
        {
            put(FileType.JPG, ".jpg");
        }
    };

    public static String getSuffix(String key) {
        return FileType.types.get(key);
    }

    /**
     * 根据给定的文件名,获取其后缀信息
     * @param filename
     * @return
     */
    public static String getSuffixByFilename(String filename) {

        return filename.substring(filename.lastIndexOf(".")).toLowerCase();

    }
}
