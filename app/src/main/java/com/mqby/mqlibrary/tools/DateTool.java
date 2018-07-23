package com.mqby.mqlibrary.tools;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author MaQiang
 * @time 2018/6/9 0009 15:22
 * @QQ 1033785970
 * @class 日期时间工具类
 */
public class DateTool {

    public static final String FULL_DATE = "yyyy-MM-dd HH:mm:ss";//年-月-日 时:分:秒
    public static final String YTDTD_DATE = "yyyy-MM-dd HH:mm";//年-月-日 时:分
    public static final String YTD_DATE = "yyyy-MM-dd";//年-月-日
    public static final String YT_DATE = "yyyy-MM";//年-月
    public static final String MD_DATE = "MM-dd";//月-日
    public static final String TDS_DATE = "HH:mm:ss";//时:分:秒
    public static final String TD_DATE = "HH:mm";//时:分
    public static final String FULL_TEXT_DATE = "yyyy年MM月dd日 HH时mm分ss秒";//年-月-日 时:分:秒
    public static final String YTD_TEXT_DATE = "yyyy年MM月dd日";//年-月-日
    public static final String NYR_DATE = "yyyyMMdd";//年月日


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({FULL_DATE, YTD_DATE, YT_DATE, TDS_DATE, TD_DATE, MD_DATE, YTDTD_DATE,
            FULL_TEXT_DATE, YTD_TEXT_DATE, NYR_DATE})
    @interface DateType {
    }

    /**
     * 将字符串转为完整日期类型
     *
     * @param date
     * @return
     */
    public static Date toFullDate(String date) {
        return formatDate(date, FULL_DATE);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param type 格式
     * @return
     */
    public static Date formatDate(String date, @DateType String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        try {
            return format.parse(date);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param type 格式
     * @return
     */
    public static String formatDate(Date date, @DateType String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }


    /**
     * 时间戳转日期
     *
     * @param date
     * @param dateType 格式
     * @return
     */
    public static String formatDate(long date, @DateType String dateType) {
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        return format.format(new Date(date * 1000));
    }


    /**
     * 根据时间转换时间戳
     *
     * @param dateStr
     * @param dateType
     * @return
     */
    public static long getStamp(String dateStr, @DateType String dateType) {
        if (StringTool.isEmpty(dateStr)) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        try {
            return format.parse(dateStr).getTime() / 1000;
        } catch(ParseException e) {
            e.printStackTrace();
            LogTool.i("时间转换失败,格式错误.时间 :" + dateStr + ",类型:" + dateType);
        }
        return 0;
    }

    /**
     * 制定日期 添加/减少 时间
     *
     * @param time     日期
     * @param num      添加或减少的数
     * @param dateType 返回日期格式
     * @param field    增减规则常量 Calebdar.DAY
     * @return
     */
    public static String addDays(String time, int num, @DateType String dateType, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        calendar.add(field, num);
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        return format.format(calendar.getTime());
    }

    /**
     * 制定日期 添加/减少 时间
     *
     * @param date     日期
     * @param num      添加或减少的数
     * @param dateType 返回日期格式
     * @param field    增减规则常量 Calebdar.DAY
     * @return
     */
    public static String addDays(Date date, int num, @DateType String dateType, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, num);
        SimpleDateFormat format = new SimpleDateFormat(dateType);
        return format.format(calendar.getTime());
    }


    /**
     * 根据生日时间戳 获取年龄
     *
     * @param birthday
     * @return
     */
    public static String getAge(long birthday) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String b = format.format(new Date(birthday));
        String b2 = format.format(Calendar.getInstance().getTime());
        try {
            int age = Integer.valueOf(b2) - Integer.valueOf(b);
            if (age == 0)
                return "1";
            else
                return String.valueOf(age);
        } catch(Exception e) {
        }
        return null;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @param dateType 格式
     * @return
     */
    public static String friendlyDate(String sdate, @DateType String dateType) {
        Date time = null;
        if (isInEasternEightZones())
            time = toFullDate(sdate);
        else
            time = transformTime(toFullDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());

        if (time == null) {
            return "";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否在今天
        SimpleDateFormat dayFormat = new SimpleDateFormat(YTD_DATE);
        String curDate = dayFormat.format(cal.getTime());
        String paramDate = dayFormat.format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天 ";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = new SimpleDateFormat(dateType).format(time);
        }
        return ftime;
    }

    /**
     * 判断用户的设备时区是否为东八区（中国） 2014年7月31日
     *
     * @return
     */
    public static boolean isInEasternEightZones() {
        boolean defaultVaule = true;
        defaultVaule = TimeZone.getDefault() == TimeZone.getTimeZone("GMT+08");
        return defaultVaule;
    }

    /**
     * 根据不同时区，转换时间 2014年7月31日
     *
     * @return
     */
    public static Date transformTime(Date date, TimeZone oldZone, TimeZone newZone) {
        Date finalDate = null;
        if (date != null) {
            int timeOffset = oldZone.getOffset(date.getTime())
                    - newZone.getOffset(date.getTime());
            finalDate = new Date(date.getTime() - timeOffset);
        }
        return finalDate;
    }

    /**
     * 获取系统当前时间戳
     *
     * @return
     */
    public static long getCurrenTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 与系统时间计算时间差
     * 一分钟内显示"刚刚"
     * 一小时内显示"xx分钟前"
     * 一天内显示"xx小时前"
     * 七天内显示"xx天前"
     * 超过七天按照格式显示日期
     *
     * @param time
     * @param dateType
     * @return
     */
    public static String getTimeDifference(String time, @DateType String dateType) {
        if (!StringTool.isEmpty(time)) {
            long l = (long) Integer.parseInt(time);//传入的时间
            long timeStamp = getCurrenTimeStamp();//系统时间
            timeStamp = timeStamp - l;
            long timeResult = timeStamp / 60 / 60;
            if (timeResult > 0 && timeResult < 24) {
                return timeResult + "小时前";
            } else if (timeResult > 24) {
                if (timeResult / 24 < 7)
                    return formatDate(l, dateType);
                else
                    return timeResult / 24 + "天前";
            } else if (timeResult < 24) {
                if (timeStamp / 60 > 0) {
                    return timeStamp / 60 + "分钟前";
                } else {
                    return "刚刚";
                }
            }
        }
        return null;
    }

    /**
     * 获取当前日期是星期几
     *
     * @param time
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(String time) {
        return getWeekOfTime(new Date(time));
    }

    /**
     * 获取当前日期是星期几
     *
     * @param time
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(long time) {
        return getWeekOfTime(new Date(time));
    }

    //获取当前日期是星期几
    public static int getWeekOfTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return w;
    }


    /**
     * 获取日期为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(long date) {
        return getWeekOfYear(new Date(date));
    }

    /**
     * 获取日期为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(String date) {
        return getWeekOfYear(new Date(date));
    }

    /**
     * 获取日期为每年第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int week = c.get(Calendar.WEEK_OF_YEAR) - 1;
        week = week == 0 ? 52 : week;
        return week > 0 ? week : 1;
    }

    public static String friendly_time2(String sdate) {
        String res = "";
        if (StringTool.isEmpty(sdate))
            return "";

        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        //获取当前时间 月 日
        String currentData = formatDate(getCurrenTimeStamp(), MD_DATE);
        int currentDay = StringTool.toInt(currentData.substring(3));
        int currentMoth = StringTool.toInt(currentData.substring(0, 2));

        int sMoth = StringTool.toInt(sdate.substring(5, 7));
        int sDay = StringTool.toInt(sdate.substring(8, 10));
        int sYear = StringTool.toInt(sdate.substring(0, 4));
        Date dt = new Date(sYear, sMoth - 1, sDay - 1);

        if (sDay == currentDay && sMoth == currentMoth) {
            res = "今天 / " + weekDays[getWeekOfDate(getCurrenTimeStamp())];
        } else if (sDay == currentDay + 1 && sMoth == currentMoth) {
            res = "昨天 / " + weekDays[(getWeekOfDate(getCurrenTimeStamp()) + 6) % 7];
        } else {
            if (sMoth < 10) {
                res = "0";
            }
            res += sMoth + "/";
            if (sDay < 10) {
                res += "0";
            }
            res += sDay + " / " + weekDays[getWeekOfDate(dt.getTime())];
        }

        return res;
    }


    /**
     * 获取日期当天的开始时间
     */
    public static long getDayStartTime(long date) {
        return getDayStartTime(new Date(date));
    }

    /**
     * 获取日期当天的开始时间
     */
    public static long getDayStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //当天的开始时间
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis()/1000;
    }

    /**
     * 获取日期当天结束时间
     */
    public static long getDayEndTime(long date) {
        return getDayEndTime(new Date(date));
    }

    /**
     * 获取日期当天结束时间
     */
    public static long getDayEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //当天的结束时间
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTimeInMillis()/1000;
    }


}
