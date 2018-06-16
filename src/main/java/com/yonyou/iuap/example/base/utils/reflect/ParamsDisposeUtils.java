/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月5日
 */

package com.yonyou.iuap.example.base.utils.reflect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.common.BaseEntityUtils;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.BaseEntity;

/**
  * @description 参数处理工具类
  * @author 姚春雷
  * @date 2018年6月5日 下午6:13:45
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class ParamsDisposeUtils {

    private ParamsDisposeUtils() {

    }

    /**
      * 搜索参数时间转换
      * @param searchParams 搜索参数
      * @param filedName    处理字段
      * @param dateFormat   时间格式
     */
    public static void searchParamsDateConvert(SearchParams searchParams, String filedName, String dateFormat) {
        Object searchDspDateObj = searchParams.getSearchMap().get(filedName);
        if (null != searchDspDateObj) {
            String dspDate = searchDspDateObj.toString();
            SimpleDateFormat smdf = new SimpleDateFormat(dateFormat);
            String sdate = smdf.format(new Date(Long.parseLong(dspDate)));
            searchParams.getSearchMap().put(filedName, sdate);
        }
    }

    /**
      * 创建打印数据参数
      * @param dataObj  打印数据，数据承载实体必须集成BaseEntity
      * @return 参数字符串格式对象
     */
    public static <T extends BaseEntity> String createPrintDataParams(T dataObj) {
        JSONArray mainDataJson = new JSONArray();// 主实体数据
        List<String> objAttrList = dataObj.getAllAttributeNames();

        JSONObject mainData = new JSONObject();
        for (String attr : objAttrList) {
            if (BaseEntityUtils.lsAttrExclude.contains(attr)) {
                continue;
            }
            mainData.put(attr, dataObj.getAttribute(attr));
        }
        mainDataJson.add(mainData); //主表只有一行
        JSONObject boAttr = new JSONObject();
        boAttr.put("example_print", mainDataJson);
        return boAttr.toString();
    }
}
