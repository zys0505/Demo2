package com.osp.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * @author liudonghe  2017年5月10日 下午4:11:43 
 *
 */
public class DateUtil {

    /***
     * 一天的毫秒值
     */
    public static int ONE_DAY = 24 * 60 * 60 * 1000;
    public static int ONE_mm = 60 * 1000;

    private DateUtil() {

    }

    /**
     * 根据日期字符串得到2个字符串之间相差几天
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int getDaysOfTwoDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDateTime = null;
        Date endDateTime = null;
        long days = 0;
        try {
            startDateTime = sdf.parse(startDate);
            endDateTime = sdf.parse(endDate);
            return getDaysOfTwoDate(startDateTime, endDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) days;
    }

    /**
     * 根据日期字符串得到2个字符串之间相差几个月
     * @param startDate 开始时间, yyyy-mm
     * @param endDate 结束时间，, yyyy-mm
     * @return
     * @throws ParseException 
     */
    public static int getMonthsOfTwoDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar sCal = Calendar.getInstance();
        Calendar eCal = Calendar.getInstance();
        sCal.setTime(sdf.parse(startDate));
        eCal.setTime(sdf.parse(endDate));
        int result = eCal.get(Calendar.MONTH) - sCal.get(Calendar.MONTH)
            + (eCal.get(Calendar.YEAR) - sCal.get(Calendar.YEAR)) * 12;
        return result;
    }
    
    /**
     * 根据日期字符串得到2个字符串之间相差几年
     * @param startDate 开始时间, yyyy
     * @param endDate 结束时间，, yyyy
     * @return
     * @throws ParseException 
     */
    public static int getYearsOfTwoDate(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar sCal = Calendar.getInstance();
        Calendar eCal = Calendar.getInstance();
        sCal.setTime(sdf.parse(startDate));
        eCal.setTime(sdf.parse(endDate));
        int result = eCal.get(Calendar.YEAR) - sCal.get(Calendar.YEAR);
        return result;
    }

    /**
     * 根据日期字符串得到2个字符串之间相差几天
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public static int getDaysOfTwoDate(Date startDate, Date endDate) {
        long days = 0;
        days = (endDate.getTime() - startDate.getTime()) / ONE_DAY;
        return (int) days;
    }

    /**
     * 获取当前日期年的第一天
     * @return
     */
    public static Date getTheFirstDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }
    /**
     * 得到几个月后
     * @param date
     * @param n
     * @return
     */
    public static Date getLaterMonth(Date date,int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
    /**
     * 获取当前日期月的第一天 
     * @param date
     * @return
     */
    public static String getTheFirstDayOfMonth(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date newDate = null;
        int year = 0;
        int month = 0;
        try {
            newDate = sdf.parse(date);
            cal.setTime(newDate);
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (month > 9) {
            return String.valueOf(year) + "-" + String.valueOf(month) + "-01";
        } else {
            return String.valueOf(year) + "-0" + String.valueOf(month) + "-01";
        }
    }

    /**
     * 获取当前时间所在的月份  
     * @param date
     * @return
     */
    public static String getMonthOfDay(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date newDate = null;
        int month = 0;
        try {
            newDate = sdf.parse(date);
            cal.setTime(newDate);
            month = cal.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(month + 1);
    }

    /**
     * 得到后n天日期(格式是MM-dd)
     * @param startDate
     * @param n 几天
     * @return
     */
    public static String getNDaysLater(String startDate, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(startDate);
            date = getNDaysLater(date, n);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    /**
     * 得到后n天日期(格式是MM-dd)
     * @param startDate 开始时间
     * @param n 几天
     * @return
     */
    public static Date getNDaysLater(Date startDate, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_YEAR, n);
        return cal.getTime();
    }

    /**
     * 获取当前时间的字符串 （时分）
     * @return
     */
    public static String getCurrentTimeString() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 两个日期字符串之间相差的时间为多少分钟
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMinOfTwoDateString(Date startDate, Date endDate) {
        long startMill = startDate.getTime();
        long endMill = endDate.getTime();
        return (int) ((endMill - startMill) / 1000 / 60);
    }

    /**
     * 两个日期字符串之间相差的时间为多少分钟 
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getMinOfTwoDateString(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            long startMill = sdf.parse(startDate).getTime();
            long endMill = sdf.parse(endDate).getTime();
            return (int) ((endMill - startMill) / 1000 / 60);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取96点每个时间间隔的，从00:15开始以后的95个点的值
     * @param i
     * @return
     */
    public static String getNFifTimeString(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String startTime = "00:15";
        try {
            Date d = new Date((sdf.parse(startTime).getTime() + i * 15 * 60 * 1000));
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 截取日期字符串前10位 即年月日 
     * @param date
     * @return
     */
    public static String getYMDString(String date) {
        if (date != null && date.length() > 10) {
            return date.substring(0, 10);
        } else {
            return date;
        }

    }

    /**
     * 获取当前时间戳
     * @param formatString
     * @return
     */
    public static String getTimeStampString(String pattern) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String time = format.format(date);
        return time;
    }
}
