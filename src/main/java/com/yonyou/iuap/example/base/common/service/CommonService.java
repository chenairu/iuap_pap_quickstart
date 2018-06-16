/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月13日
 */

package com.yonyou.iuap.example.base.common.service;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.example.base.common.entity.ProcessEntity;

/**
  * @description 公共业务接口
  * @author 姚春雷
  * @date 2018年6月13日 上午11:39:14
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public interface CommonService {

    public JSONObject submitFlow(ProcessEntity processEntity);
}
