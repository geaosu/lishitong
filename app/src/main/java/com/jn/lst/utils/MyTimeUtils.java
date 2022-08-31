package com.jn.lst.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTimeUtils {
    /**
     * 根据年月日时间获取毫秒数
     *
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static String getDateTime(String dateTime) throws ParseException {
//        String dateTime = "2016-12-31 12:30:45 123";

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(dateTime));
        String timeInMillis = String.valueOf(calendar.getTimeInMillis());
        System.out.println("日期[" + dateTime + "]对应毫秒：" + timeInMillis);
        return timeInMillis;
    }

    public static long getFormateDateMillis(String time, String formate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = dateFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 获取格式化后的日期： 隔年显示年月日，隔天显示月日，当天显示时分，当天分上午下午
     *
     * @param time    时间
     * @param formate 格式化类型
     * @return 格式化后的日期字符串
     */

    public static String getFormatedDateStrTime(String time, String formate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = dateFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        Calendar today = Calendar.getInstance();
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(date);
        // 判断当年
        if (today.get(Calendar.YEAR) == thatDay.get(Calendar.YEAR)) {
            // 判断当天
            int diffDays = today.get(Calendar.DAY_OF_MONTH) - thatDay.get(Calendar.DAY_OF_MONTH);
            if (diffDays == 0) {
                if (today.getTimeInMillis() - thatDay.getTimeInMillis() < 3600000) {
                    //一小时内，显示多少分钟前
                    if (today.getTimeInMillis() - thatDay.getTimeInMillis() <= 60000) {
                        return "刚刚";
                    } else {
                        return (today.getTimeInMillis() - thatDay.getTimeInMillis()) / 60000 + " 分钟前";
                    }
                } else {
                    // 当天大于一小时，显示时分
                    return new SimpleDateFormat("HH:mm").format(date);
                }
            } else if (diffDays <= 7) {
                // 昨天的显示显示  昨天 时：分
                if (diffDays == 1) {
                    return "昨天" + new SimpleDateFormat(" HH:mm").format(date);
                } else {
                    // 超过两天显示星期几 时：分
                    return getDayOfWeek(thatDay.get(Calendar.DAY_OF_WEEK)) + new SimpleDateFormat(" HH:mm").format(date);
                }
            } else {
                // 隔天，显示月日
                return new SimpleDateFormat("MM月dd日 HH:mm").format(date);
            }
        }
        // 隔年,显示年月日
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(date);
    }

    /**
     * 获取格式化后的日期： 隔年显示年月日，隔天显示月日，当天显示时分
     *
     * @param time    时间
     * @param formate 格式化类型
     * @return 格式化后的日期字符串
     */

    public static String getFormatedDateStr(String time, String formate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formate);
        Date date = null;
        try {
            date = dateFormat.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        Calendar today = Calendar.getInstance();
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(date);
        // 判断当年
        if (today.get(Calendar.YEAR) == thatDay.get(Calendar.YEAR)) {
            // 判断当天
            if (today.get(Calendar.DAY_OF_MONTH) == thatDay.get(Calendar.DAY_OF_MONTH)) {
                // 当天，显示时分
                return new SimpleDateFormat("HH:mm").format(date);
            }
            // 隔天，显示月日
            return new SimpleDateFormat("MM-dd").format(date);
        }
        // 隔年,显示年月日
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    /**
     * 获取当前时间的日期格式
     *
     * @return
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间的日期格式
     *
     * @return
     */
    public static String getCurrentDates() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间的后1分钟
     */
    public static String getCurrentDateLeter() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 1);
        date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取当前时间的后一天
     */
    public static String getTomorrowDate() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }


    /***
     * 获取星期数
     */
    private static String getDayOfWeek(int date) {
        String data1 = "星期一";
        String data2 = "星期二";
        String data3 = "星期三";
        String data4 = "星期四";
        String data5 = "星期五";
        String data6 = "星期六";
        String data7 = "星期日";
        String[] weeks = {data7, data1, data2, data3, data4, data5, data6};
        if (date < 0) {
            date = 0;
        }
        return weeks[date - 1];
    }

    public static String StringToDDDate(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat("dd");
            String s = format1.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String StringToMMDate(String time) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = format.parse(time);
            SimpleDateFormat format1 = new SimpleDateFormat("MM");
            String s = format1.format(date);
            return s;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


    public static String StringToMonth() {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
            String s = format1.format(System.currentTimeMillis());
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String StringToYear() {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy");
            String s = format1.format(System.currentTimeMillis());
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    // 今日前时间
    public static long TimeBeforeToday(String oldDateStr) {
        try {
            // String oldDateStr = "2022-07-03";
            String todayDateStr = getTodayDateStr();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date oldDate = format.parse(oldDateStr);
            Date todayDate = format.parse(todayDateStr);

            long daysBetween = (todayDate.getTime() - oldDate.getTime() + 1000000) / (60 * 60 * 24 * 1000);
            return daysBetween;
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getTodayDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        return dateStr;
    }

}
