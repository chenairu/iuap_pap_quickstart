/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月12日
 */

package com.yonyou.iuap.example.base.common.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
  * @description 公共请求控制
  * @author 姚春雷
  * @date 2018年6月12日 上午7:30:51
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@RestController
@RequestMapping(value = "/commonweb")
public class CommonWeb {

    /**
      * 获取uuid
      * @param request  http请求
      * @return uuid
     */
    @RequestMapping(value = "/getuuid", method = RequestMethod.GET)
    public Object getUuid(HttpServletRequest request) {
        return UUID.randomUUID().toString();
    }

}
