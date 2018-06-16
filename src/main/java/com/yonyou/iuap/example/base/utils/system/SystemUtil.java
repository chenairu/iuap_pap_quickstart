/*
 * （C）2015 泰尔重工股份有限公司. taier Group, All right reserved.
 *
 * 项目名称 : trcommon
 * 创建日期 : 2018年3月17日
 */

package com.yonyou.iuap.example.base.utils.system;

/**
  * @description 系统工具类
  * @author 姚春雷
  * @date 2018年3月17日 下午8:29:28
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class SystemUtil {

    private SystemUtil() {

    }

    /**
      * 获得服务器操作系统名称
      * @return 操作系统名称
     */
    public static String getOperationSystemName() {
        return System.getProperty("os.name");
    }

    /**
      * 获得服务器当前登录用户
      * @return 用户名
     */
    public static String getOperationSystemCurrentLoginUser() {
        return System.getProperty("user.name");
    }

}
