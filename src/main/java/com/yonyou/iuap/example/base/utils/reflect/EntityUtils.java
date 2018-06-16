/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月11日
 */

package com.yonyou.iuap.example.base.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import yonyou.bpm.rest.request.RestVariable;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.base.utils.date.DateUtil;

/**
  * @description 实体工具类
  * @author 姚春雷
  * @date 2018年6月11日 上午9:18:22
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class EntityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityUtils.class);

    private EntityUtils() {

    }

    /**
      * 给新对象的公共属性字段进行赋值
      * @param obj  新对象
     */
    public static void commonAttrAssiNewObject(Object obj) {
        try {
            Date date = new Date();

            Method drSetMethod = obj.getClass().getMethod("setDr", Integer.class);
            drSetMethod.invoke(obj, 0);

            Method createUserSetMethod = obj.getClass().getMethod("setCreateUser", String.class);
            createUserSetMethod.invoke(obj, InvocationInfoProxy.getUserid());

            Method createTimeSetMethod = obj.getClass().getMethod("setCreateTime", Date.class);
            createTimeSetMethod.invoke(obj, date);

            commonAttrAssiUpdateObject(obj);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
      * 给更新对象的公共属性字段进行赋值
      * @param obj  更新对象
     */
    public static void commonAttrAssiUpdateObject(Object obj) {
        try {
            Date date = new Date();
            //设置更新时间
            Method tsSetMethod = obj.getClass().getMethod("setTs", Date.class);
            tsSetMethod.invoke(obj, date);

            //设置版本号
            Integer version = (Integer) obj.getClass().getMethod("getVersion").invoke(obj);
            Method versionSetMethod = obj.getClass().getMethod("setVersion", Integer.class);
            if (version == null) {
                version = 0;
            }
            versionSetMethod.invoke(obj, (version + 1));

            Method lastModifyUserSetMethod = obj.getClass().getMethod("setLastModifyUser", String.class);
            lastModifyUserSetMethod.invoke(obj, InvocationInfoProxy.getUserid());

            Method lastModifiedSetMethod = obj.getClass().getMethod("setLastModified", Date.class);
            lastModifiedSetMethod.invoke(obj, date);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static List<RestVariable> objectToRestVariableList(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<RestVariable> restVariableList = new ArrayList<RestVariable>();
        for (Field field : fields) {
            String fieldName = field.getName();
            if ("serialVersionUID".equals(fieldName)) {
                continue;
            }
            Object fieldValue = getObjectFieldValue(obj, field);
            if (null == fieldValue) {
                continue;
            }
            RestVariable restVariable = new RestVariable();
            restVariable.setName(fieldName);
            restVariable.setValue(getObjectFieldValue(obj, field));
            restVariableList.add(restVariable);
        }
        return restVariableList;
    }

    public static Object getObjectFieldValue(Object obj, Field field) {
        String fieldType = field.getGenericType().toString();
        try {
            if ("class java.util.Date".equals(fieldType)) {
                Method getMethod = obj.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                if (null != getMethod.invoke(obj) && StringUtils.isNotBlank(getMethod.invoke(obj).toString())) {
                    return DateUtil.objDateFieldToStr(getMethod.invoke(obj).toString(), "yyyy-MM-dd HH:mm:ss");
                }
            } else {
                Method getMethod = obj.getClass().getMethod("get" + StringUtils.capitalize(field.getName()));
                return getMethod.invoke(obj);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
