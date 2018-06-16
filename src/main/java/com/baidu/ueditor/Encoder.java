
package com.baidu.ueditor;

/**
  * @description TODO 添加类/接口功能描述
  * @author 姚春雷
  * @date 2018年3月17日 下午9:11:50
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class Encoder {

    public static String toUnicode(String input) {

        StringBuilder builder = new StringBuilder();
        char[] chars = input.toCharArray();

        for (char ch : chars) {

            if (ch < 256) {
                builder.append(ch);
            } else {
                builder.append("\\u" + Integer.toHexString(ch & 0xffff));
            }

        }

        return builder.toString();

    }
}
