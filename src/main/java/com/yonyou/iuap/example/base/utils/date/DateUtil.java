/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2016年6月5日
 */

package com.yonyou.iuap.example.base.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
  * @description 时间工具类
  * @author 姚春雷
  * @date 2016年6月5日 下午6:39:45
  * @version 1.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
public class DateUtil {

    private DateUtil() {

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 时间修改
     * @param date  时间
     * @param day   修改天数
     * @param format    格式化字符串
     * @return  返回修改过的时间
     */
    public static String getModifiedDate(String date, int day, String format) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null != format) {
            smdf = new SimpleDateFormat(format);
        }
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(smdf.parse(date));
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + day);
            return smdf.format(calendar.getTime());
        } catch (ParseException e) {
            LOGGER.error("时间修改异常，异常信息为：" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 得到当前时间
     * @param format    格式化字符串
     * @return  返回系统当前时间
     */
    public static String getCurrentDate(String format) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        if (null != format) {
            smdf = new SimpleDateFormat(format);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
        return smdf.format(calendar.getTime());
    }

    /**
     * 把字符串转换成时间
     * @param date  时间
     * @param format    格式化字符串
     * @return  返回转换结果
     */
    public static Date stringToDate(String date, String format) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
        if (null != format) {
            smdf = new SimpleDateFormat(format);
        }
        try {
            newDate = smdf.parse(date);
        } catch (ParseException e) {
            LOGGER.error("字符串转换成时间异常，异常信息为：" + e.getMessage(), e);
        }
        return newDate;
    }

    /**
     * 时间比较
     * @param dateA 第一时间参数
     * @param dateB 第二时间参数
     * @return  返回判断结果
     */
    public static boolean dateCompare(Date dateA, Date dateB) {
        if (dateA.getTime() > dateB.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 时间比较
     * @param dateA 第一时间参数
     * @param dateB 第二时间参数
     * @return  返回判断结果
     */
    public static boolean dateCompare(String dateA, String dateB) {
        SimpleDateFormat smdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date tempDate1 = smdf.parse(dateA);
            Date tempDate2 = smdf.parse(dateB);
            if (tempDate1.getTime() > tempDate2.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            LOGGER.error("时间比较异常，异常信息为：" + e.getMessage(), e);
        }

        return false;
    }

    /**
     * 时间加减
     * @param day   天数
     * @param format 格式化输出
     * @return  返回加减后的结果
     */
    public static String dateAddDay(int day, String format) {
        String dateStr = "";
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.setTime(new Date());
        myCalendar.add(Calendar.DAY_OF_YEAR, day);
        dateStr = formatDateToStr(myCalendar.getTime(), format);
        return dateStr;
    }

    /**
     * 格式化
     * @param date  日期
     * @param format 格式化输出
     * @return  返回格式化以后的时间
     */
    public static String formatDateToStr(Date date, String format) {
        SimpleDateFormat thisSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (null != format) {
            thisSimpleDateFormat = new SimpleDateFormat(format);
        }
        return thisSimpleDateFormat.format(date);
    }

    /**
     * //TODO 添加方法功能描述
      * @param fieldValue
      * @param format
      * @return
     */
    public static String objDateFieldToStr(String fieldValue, String format) {
        SimpleDateFormat smdf = new SimpleDateFormat(format);
        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
        SimpleDateFormat smdfPatt = new SimpleDateFormat(pattern, Locale.US);
        try {
            return smdf.format(smdfPatt.parse(fieldValue));
        } catch (ParseException e) {
            LOGGER.error("实体的时间字段值转换错误，异常信息为：" + e.getMessage(), e);
        }
        return null;
    }
}
