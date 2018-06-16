/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月12日
 */

package com.yonyou.iuap.example.base.utils.system;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.utils.PropertyUtil;

/**
  * @description 规则编码工具类
  * @author 姚春雷
  * @date 2018年6月12日 下午4:40:35
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class RulesCodeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RulesCodeUtils.class);

    private RulesCodeUtils() {

    }

    /**
      * 获得业务编码规则，由于业务编码规则支持一个模块多套编码规则，
      * 所以在业务编码在应用平台建立好以后，一定要设置一套默认的编码规则，否则会无法生成
      * @param billObjCode  应用节点编码，也就是pub_bcr_obj中的code字段
      * @param pkAssign 关联关系
      * @param obj  业务实体对象
      * @return 业务编码
     */
    public static String getBusinessCode(String billObjCode, String pkAssign, Object obj) {
        String billVo = JSONObject.toJSONString(obj);

        String getCodeUrl = PropertyUtil.getPropertyByKey("billcodeservice.base.url") + "/billcoderest/getBillCode";

        Map<String, String> data = new HashMap<String, String>();
        data.put("billObjCode", billObjCode);
        data.put("pkAssign", pkAssign);
        data.put("billVo", billVo);

        JSONObject getBillCodeInfo = RestUtils.getInstance().doPost(getCodeUrl, data, JSONObject.class);

        String getFlag = getBillCodeInfo.getString("status");
        String billCode = getBillCodeInfo.getString("billcode");
        //获取业务编码失败
        if ("failed".equalsIgnoreCase(getFlag)) {
            String errMsg = getBillCodeInfo.getString("msg");
            LOGGER.error("{billObjCode:" + billObjCode + ",pkAssign:" + pkAssign + ",billvo:" + billVo + "},错误信息:" + errMsg);
            throw new BusinessException("获取编码规则发生错误", errMsg);
        }
        return billCode;

    }
}
