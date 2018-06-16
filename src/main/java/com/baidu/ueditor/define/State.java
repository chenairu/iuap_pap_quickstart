
package com.baidu.ueditor.define;

/**
  * @description TODO 添加类/接口功能描述
  * @author 姚春雷
  * @date 2018年3月17日 下午9:20:07
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public interface State {

    public boolean isSuccess();

    public void putInfo(String name, String val);

    public void putInfo(String name, long val);

    public String toJSONString();
}
