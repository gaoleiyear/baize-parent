package com.baize.base.common.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * DateUtil
 *
 */
@Slf4j
public class DateUtil {

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String T_DATETIME = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE1 = "yyyy-MM-dd";
    public static final String DATE2 = "yyyyMMdd";
    public static final String DEFAULT_DATE_CHINESE = "yyyy年MM月dd日";

    public static String formatDate(Date date) {
        return formatDate(date, DATE1);
    }

    public static String currDateTime() {
        return formatDateTime(new Date());
    }



    public static String formatDateTime(Date date) {
        return formatDate(date, DATETIME);
    }

    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }

        return DateFormatUtils.format(date, pattern, Locale.CHINA);
    }

    public static String formatDate(String dateStr, String srcPattern, String desPattern) {
        Date date = parseDate(dateStr, srcPattern);
        if (date == null) {
            return null;
        }
        return formatDate(date, desPattern);
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, DATE1);
    }

    public static Date parseDateTime(String dateStr) {
        return parseDate(dateStr, new String[]{DATETIME, "yyyy-MM-dd HH:mm:ss.SSS",});
    }

    public static Date parseDate(String dateStr, String pattern) {
        return parseDate(StringUtils.trim(dateStr), new String[]{pattern});
    }

    public static Date parseDate(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(dateStr, patterns);
        } catch (Exception e) {
            log.error("日期转换错误, dateStr={}, pattern={}", dateStr, StringUtils.join(patterns, ","));
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static boolean test(String dateStr, String pattern) {
        return test(dateStr, new String[]{pattern});
    }

    public static boolean test(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return false;
        }
        try {
            DateUtils.parseDateStrictly(dateStr, patterns);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDateBetween(Date date, Date start, Date end) {
        return date.getTime() >= start.getTime() && date.getTime() <= end.getTime();
    }

    /**
     * 两个时间相隔天数 time1-time2
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long diffDays(Date time1, Date time2) {
        if (time1 == null || time2 == null) {
            return 0;
        }
        return (time1.getTime() - time2.getTime()) / 1000 / 60 / 60 / 24;
    }



    public static Date addYears(Date date, Integer years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }


    public static Date addMonths(Date date, Integer months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static Date addHours(Date date, Integer hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, Integer minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static Date addSeconds(Date date, Integer seconds) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public static boolean isInputDateNormalString(String input) {
        return input.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    public static boolean isInputDateNormalStringTime(String input) {
        return input.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    }
    /**
     *  获取当月的 天数
     *
     * @return
     */
    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * <p>Title: getWeekOfYear</p>
     * <p>Description: 获取指定时间所在年的周数（返回带格式的年-周，如仅需返回周数请调用 getWeekOfYear(Date date)方法）</p>
     * @param date
     * @return 返回格式为：2017-1、2018-52
     */
    public static String getWeekOfYearPattern(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(c.YEAR) + "-" + c.get(Calendar.WEEK_OF_YEAR);
    }

    public static Date getThisHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }

    /**
     * 获取当天0时
     * @param date
     * @return
     */
    public static Date getThisDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return cal.getTime();
    }
    /**
     *
     *            字符串日期
     * @return Date
     */
    public static int compare(Date date1, Date date2) {
        long result = date1.getTime() - date2.getTime();
        if (result > 0) {
            return 1;
        } else if (result == 0) {
            return 0;
        } else {
            return -1;
        }
    }
}