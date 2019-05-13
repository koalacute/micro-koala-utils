package com.koalacute.microkoala.utils.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @ClassName: DateFormatUtil
 * @Description:与Date相关的公用类
 * @author dk
 * @date 2014年12月5日 下午2:29:31
 *
 */
public class DateFormatUtil {
    private static SimpleDateFormat sdf                 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String      ENG_DATE_FROMAT     = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String      YYYYMMDDHHMMSSSSS   = "yyyyMMddHHmmssSSS";
    public static final String      YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String      YYYY_MM_DD_HH_MM    = "yyyy-MM-dd HH:mm";
    public static final String      MM_DD_HH_MM         = "MM-dd HH:mm";
    public static final String      MM_DD_HH_MM_CN      = "MM月dd日 HH:mm";
    public static final String      YYYY_MM_DD          = "yyyy-MM-dd";
    public static final String      YYYY                = "yyyy";
	public static final String      YYYYMMDD            = "yyyyMMdd";
    public static final String      YYYYMM           = "yyyyMM";
	public static final String      HH_MM_SS                  = "HH:mm:ss";
    public static final String      MM                  = "MM";
    public static final String      DD                  = "dd";
    public static final String      HH                  = "HH";
    public static final String      YYYYMMDDHHMMSS    = "yyyyMMddHHmmss";

    /**
     * @Title: dateToString
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param @param date
     * @param @param formate
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     */
    public static String dateToString(Date date, String formate) {
        if (date == null) {
            return "";
        }
        DateFormat dateFormat = new SimpleDateFormat(formate);
        return dateFormat.format(date);
    }

    public static String datetime2String(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
    * 获取当期星期几，1-星期一，2-星期二，。。。6-星期六，7-星期日
    *
    * @Title: getCurDayOfWeek
    * @Description: 获取当期星期几，1-星期一，2-星期二，。。。6-星期六，7-星期日
    * @return int    返回类型
    * @throws
    * @author wanjian
    */
    public static int getCurDayOfWeek(){
    	Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
    	return dayofweek==0?7:dayofweek;
    }

    /**
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date, String formatStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp, String formatStr) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    public static Date timestampStr2Date(String timestampStr) throws ParseException{
        Long time = Long.parseLong(timestampStr);
        String dateStr = sdf.format(time);
        return sdf.parse(dateStr);
    }

    /**
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString, String formatStr) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(formatStr);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }

    /**
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * @描述 —— 获得当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    public static int getMonthsBetween(String before, String after) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(before));
        c2.setTime(sdf.parse(after));
        return (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12
               + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
    }

    /**
     * 小时
     */
    public static String getNowHour() {
        SimpleDateFormat sdf = new SimpleDateFormat(HH);
        return sdf.format(new Date());
    }

    public static String getNowHourMinute() {
        SimpleDateFormat sdf = new SimpleDateFormat(HH + "" + MM);
        return sdf.format(new Date());
    }

    /**
     * @描述 —— 获得当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * @描述 —— 获得当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    public static String getMonthsBetweenTwoDate(Date d1,Date d2){
    	Long t1 = d1.getTime();
    	Long t2 = d2.getTime();
    	Long t ;
    	t= (t1>t2)?(t1-t2):(t2-t1);
    	Long month = t / 1000 / 60/ 60 / 24 / 30 + 1;
    	return month.toString();
    }
    /**
     * @描述 —— 指定时间距离当前时间的中文信息
     */
    public static String getLnow(long time) {
        Calendar cal = Calendar.getInstance();
        long timel = cal.getTimeInMillis() - time;
        if (timel / 1000 < 60) {
            return "1分钟以内";
        } else if (timel / 1000 / 60 < 60) {
            return timel / 1000 / 60 + "分钟前";
        } else if (timel / 1000 / 60 / 60 < 24) {
            return timel / 1000 / 60 / 60 + "小时前";
        } else {
            return timel / 1000 / 60 / 60 / 24 + "天前";
        }
    }

    /**
     * 当前时间一星期前的时间
     *
     * @return
     */
    public static final String getLastWeek() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -7);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format
     *            指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:mm"
     * @return
     */
    @Deprecated
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(YYYY_MM_DD_HH_MM_SS);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    public static String getCurrentDate() {
        return dateToString(new Date(), "yyyy-MM-dd");
    }

    public static Boolean checkIsTime(String time) {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            format.parse(time);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 比较日期先后
     *
     * @param date
     * @param interval
     * @return boolean
     */
    public static boolean isExpired(Date date, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, interval);
        Calendar now = Calendar.getInstance();
        return calendar.before(now);
    }

    public static Date string2date(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return string2date(value, "yyyy-MM-dd");
    }

    public static Date string2dateYmdhms(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return string2date(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static String string2string(String value, String from, String to) {
        return dateToString(string2date(value, from), to);
    }

    /**
     * 加1天
     *
     * @param value
     * @return Date
     */
    public static Date addOneDay(String value) {
        return addDays(value, 1);
    }

    public static Date addOneDay(Date value) {
        return addDays(value, 1);
    }

    public static Date addDays(String value, int num) {
        return addDays(string2date(value), num);
    }

    public static Date addDays(Date value, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.add(Calendar.DATE, num);
        return c.getTime();
    }

    public static Date addMonth(Date value, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.add(Calendar.MONTH, num);
        return c.getTime();
    }

    public static int getBetweenTwoDays(Date start, Date end) {
        if (start == null) {
            return 0;
        }
        if (end == null) {
            end = new Date();
        }
        long longs = (end.getTime() - start.getTime());
        int days = (int) (longs / (24 * 60 * 60 * 1000));
        return days;
    }

    public static int getBetweenTwoDaysUp(Date start, Date end) {
        if (start == null) {
            return 0;
        }
        if (end == null) {
            end = new Date();
        }
        long longs = (end.getTime() - start.getTime());
        int i = (int)(longs % (24 * 60 * 60 * 1000));
        int days;
        if(i == 0){
            days = (int) (longs / (24 * 60 * 60 * 1000));
        }else{
            days = (int) (longs / (24 * 60 * 60 * 1000)) + 1;
        }
        return days;
    }

    public static int getBetweenTwoDays(String start, String end) {
        return getBetweenTwoDays(string2date(start), string2date(end));
    }

    public static int getBetweenTwoMinutes(Date start, Date end){
        if (start == null) {
            return 0;
        }
        if (end == null) {
            end = new Date();
        }
        long longs = (end.getTime() - start.getTime());
        int minute = (int) (longs / (60 * 1000));
        return minute;
    }

    /**
     * 获取当前日期(只精确到天)
     */
    public static Date getNow() {
        return date2date(new Date(), "yyyy-MM-dd");
    }

    public static Date getNow(Date date) {
        return date2date(date, "yyyy-MM-dd");
    }

    /**
     * 获取明天日期（精确到天）
     */
    public static Date getTomorrow() {
        return addOneDay(getNow());
    }

    public static String now() {
        return datetime2String(getNow());
    }

    public static Date getLastMonth() {
        return addMonth(getNow(), -1);
    }

    public static Date getNextMonth() {
        return addMonth(getNow(), 1);
    }

    public static String nowTimeStamp() {
        return datetime2String(new Date());
    }
    /**
     *
     * @param yymonth 格式yyyy-mm
     * @return yyyy-mm-dd
     */
    public static String getMonthLastDay(String yymonth){
    	//当月最后一天 = 下月第一天减去一天;
    	String monthLastDay = dateToString(addDays(addMonth(string2date(yymonth+"-01"),1),-1),YYYY_MM_DD);
    	return monthLastDay;
    }

    /**
    * 获取指定日期的年份
    *
    * @Title: getYear
    * @Description: 获取指定日期的年份
    * @param date
    * @return int    返回类型
    * @throws
    * @author wanjian
    */
    public static int getYear(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return year;
    }

    /**
    * 获取指定日期的月份
    *
    * @Title: getMon
    * @Description: 获取指定日期的月份
    * @param date
    * @return int    返回类型
    * @throws
    * @author wanjian
    */
    public static int getMon(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH)+1;
		return month;
    }

    /**
    * 获取指定日期的日
    *
    * @Title: getDay
    * @Description:获取指定日期的日
    * @param date
    * @return int    返回类型
    * @throws
    * @author wanjian
    */
    public static int getDay(Date date){
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		return day;
    }


    /**
     * 判断     newDate(新时间)是不是过了    oldDate(原先时间)所属当天时间的24点
     * @param oldDate
     * @param newDate
     * @return boolean
     * @throws
     * @author yuechaolei
     */
    public static boolean isAfter24(Date oldDate,Date newDate){
    	Date old = date2date(oldDate, YYYY_MM_DD);
		Date new_ = date2date(newDate,YYYY_MM_DD);
		boolean result = new_.after(old);
    	return result;
    }
    /**
     * 获取当前时间距离今天结束剩余秒
     * @return
     */
    public static Long getTodayLastTime(){
        Long lastSecond = 86400000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);
        lastSecond /= 1000;
        return lastSecond;
    }

	/**
	 * 获取前一天日期
	 */
	public static Date getLastDay(){
		return addDays(getNow(), -1);
	}


    /*
     * 将时间转换为时间戳
     */
    public static String dateToStampStr(String s,String format) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 获取精确到秒的时间戳
     *
     * @param date
     * @return
     */
    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    /**
     * 加分钟
     *
     * @param value
     * @param num
     * @return
     */
    public static Date addMins(Date value, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(value);
        c.add(Calendar.MINUTE, num);
        return c.getTime();
    }

    /**
     * 将字符串YYDDMM转换为YY-DD-MM
     */
    public static String coverStringDateType(String ydm) {
        if (StringUtils.isBlank(ydm)) {
            return "";
        }
        if (8 != ydm.length()) {
            return "";
        }
        StringBuilder ydmString = new StringBuilder(ydm);
        StringBuilder backString = new StringBuilder();
        backString.append(ydmString.substring(0,4)).append("-").append(ydmString.substring(4,6)).append("-").append(ydmString.substring(6,8));
        return backString.toString();
    }

    /**
     * 将字符串YY-DD-MM转换为Date
     */
    public static Date coverYdmToDate(String ydm) throws Exception {
        String ydmString = coverStringDateType(ydm);
        if (StringUtils.isBlank(ydmString)) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").parse(ydmString);
    }
}
