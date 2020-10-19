package com.sirius.robots.comm.util;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by moxuandeng on 2016/11/5.
 */
public class DateUtils {

    /**
     * 日期格式:yyyy-MM
     */
    public static final String dateMonthParrern = "yyyy-MM";
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String datePattern = "yyyyMMdd";
    /**
     * 日期格式：yyMMdd
     */
    public static final String shortDatePattern = "yyMMdd";
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    public static final String fullPattern = "yyyyMMddHHmmss";
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    public static final String readPattern = "yyyy-MM-dd HH:mm:ss,SSS";
    /**
     * 日期时间格式：yyyyMMddHHmmss
     */
    public static final String allPattern = "yyyy-MM-dd HH:mm:ss";
    public static final String allTimePattern = "HH:mm:ss";
    public static final String minutePattern = "yyyy-MM-dd HH:mm";

    /**
     * 日期时间格式：yyyyMM
     */
    public static final String mothPattern = "yyyyMM";

    /**
     * 日期格式：yyyyMMdd
     */
    public static final String monthDayPattern = "MM-dd";

    public static final String monthDayPlainPattern = "MMdd";

    /**
     * 日期时间格式：yyMMddHHmmss
     */
    public static final String partPattern = "yyMMddHHmmss";

    /**
     * 日期时间格式：dd
     */
    public static final String ddPattern = "dd";

    public static final String dPattern = "d";

    public static final String mmPattern = "MM";
    public static final String MPattern = "M";

    /**
     * 格式：HHmmss
     */
    public static final String timePattern = "HHmmss";

    public static final String timeSuffixStart = "000000";

    public static final String timeSuffixEnd = "235959";

    private static final String INVALID_PARAM_MSG = "The payDate could not be null!";

    /**
     * 时间全格式
     */
    public static final String ALL_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 日期格式
     */
    public static final String dayPattern = "yyyy-MM-dd";


    /**
     * 日期格式
     */
    public static final String yearPattern = "yyyy";

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static Date getCurrentDate() {
        return DateTime.now().toDate();
    }

    /**
     * 获取当前时间 格式： yyyyMMddHHmmss
     *
     * @return 字符日期 格式：yyyyMMddHHmmss
     */
    public static String getCurrent() {
        return getCurrent(fullPattern);
    }

    /**
     * 获取当前时间 格式： 自定义
     *
     * @param pattern 时间格式
     * @return 自定义格式的当前时间
     */
    public static String getCurrent(String pattern) {
        return DateTime.now().toString(pattern);
    }

    public static Date yesterday(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        return calendar.getTime();
    }

    /**
     * 将字符串转换成固定格式时间
     *
     * @param date    日期
     * @param pattern 自定义格式
     * @return 转换后日期
     */
    public static Date parse(String date, String pattern) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        DateTime dateTime = parseTime(date, pattern);
        if (dateTime == null) {
            return null;
        }
        return dateTime.toDate();
    }

    public static DateTime parseTime(String date, String pattern) {
        return DateTimeFormat.forPattern(pattern).parseDateTime(date);
    }


    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(pattern);
    }

    public static String convert(String date, String targetPattern) {
        return convert(date, fullPattern, targetPattern);
    }

    public static String convert(String date, String originPattern, String targetPattern) {
        Date originDate = parse(date, originPattern);
        return format(originDate, targetPattern);
    }

    /**
     * 获取当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate(String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime().toString(pattern), format).toDate();
    }

    /**
     * 根据 pattern 将 dateTime 时间进行格式化
     * <p/>
     * 用来去除时分秒，具体根据结果以 pattern 为准
     *
     * @param date payDate 时间
     * @return payDate 时间
     */
    public static Date formatToDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return DateTime.parse(new DateTime(date).toString(pattern), format).toDate();
    }

    /**
     * 日期增减，负数为减
     *
     * @param dayNum 天数
     * @return 时间
     */
    public static Date plusDays(int dayNum) {
        return new DateTime().plusDays(dayNum).toDate();
    }

    /**
     * 日期增减，负数为减
     *
     * @param dayNum 天数
     * @return 时间
     */
    public static Date plusDays(Date date, int dayNum) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayNum);
        return calendar.getTime();
    }

    /**
     * 日期增减，负数为减
     *
     * @param monthNum 月份
     * @return 时间
     */
    public static Date plusOrAddMonth(Date sourceDate, int monthNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.MONTH, monthNum);
        return calendar.getTime();
    }

    /**
     * 按秒偏移,根据{@code source}得到{@code seconds}秒之后的日期<Br>
     *
     * @param source  , 要求非空
     * @param seconds , 秒数,可以为负
     * @return 新创建的Date对象
     */
    public static Date addSeconds(Date source, int seconds) {
        return addDate(source, Calendar.SECOND, seconds);
    }

    private static Date addDate(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException(INVALID_PARAM_MSG);
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 判断两个日期是否在同一天
     *
     * @param day1 日期1
     * @param day2 日期2
     * @return boolean
     */
    public static boolean isSameDay(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        return ds1.equals(ds2);
    }

    /**
     * 判断两个日期是否在同月
     *
     * @param day1
     * @param day2
     * @return
     */
    public static boolean isSameMonth(Date day1, Date day2) {
        SimpleDateFormat sdf = new SimpleDateFormat(mothPattern);
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算给定的两个日期的差值
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int compareDate(Date beforeDate, Date afterDate) {
        DateTime before = new DateTime(beforeDate);
        DateTime after = new DateTime(afterDate);
        Days days = Days.daysBetween(before.withTimeAtStartOfDay(), after.withTimeAtStartOfDay());
        return days.getDays();
    }

    /**
     * 计算给定的两个日期的差值
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int compareDate(String beforeDate, String afterDate, String pattern) {
        DateTime before = new DateTime(parseTime(beforeDate, pattern));
        DateTime after = new DateTime(parseTime(afterDate, pattern));
        Days days = Days.daysBetween(before.withTimeAtStartOfDay(), after.withTimeAtStartOfDay());
        return days.getDays();
    }

    /**
     * 计算给定的两个日期相差的月份
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int calMonthDiff(Date beforeDate, Date afterDate) {
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(beforeDate);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(afterDate);
        return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
    }
    /**
     * 计算给定的两个日期相差的月份
     *
     * @param beforeDate
     * @param afterDate
     * @return
     */
    public static int calMonthDiff(String beforeDate, String afterDate,String pattern) {
        Date parse = parse(beforeDate, pattern);
        Date parse1 = parse(afterDate, pattern);
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(parse);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(parse1);
        return (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12 + cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
    }
    /**
     * 获取某个月的第一天
     */
    public static Date getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, (month - 1));
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某一月份第一天
     *
     * @param dateMonthStr yyyy-MM
     * @return
     */
    public static Date getMonthFirstDay(String dateMonthStr) {
        String[] strs = dateMonthStr.split("-");
        int year = Integer.valueOf(strs[0]);
        int month = Integer.valueOf(strs[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 获取某一月份最后一天
     *
     * @param dateMonthStr yyyy-MM
     * @return
     */
    public static Date getMonthLastDay(String dateMonthStr) {
        String[] strs = dateMonthStr.split("-");
        int year = Integer.valueOf(strs[0]);
        int month = Integer.valueOf(strs[1]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 0, 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 是否在当前时间之前
     *
     * @param dateTime
     * @return
     */
    public static boolean isBeforeCurrentTime(Date dateTime) {
        return getCurrentDate().compareTo(dateTime) > 0;
    }

    /**
     * 是否在当前时间之后
     *
     * @param dateTime
     * @return
     */
    public static boolean isAfterCurrentTime(Date dateTime) {
        return getCurrentDate().compareTo(dateTime) < 0;
    }

    public static int daysBetween(Date beforeDate, Date afterDate) {
        DateTime before = new DateTime(beforeDate);
        DateTime after = new DateTime(afterDate);
        Days days = Days.daysBetween(before.withTimeAtStartOfDay(), after.withTimeAtStartOfDay());
        return days.getDays();
    }

    public static int getDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    public static int getLastDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    public static Date getFirstDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().withMinimumValue().toDate();

    }

    public static Date getLastDate(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().withMaximumValue().toDate();
    }

    public static boolean isBeforeOrEqualseCurrentDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        String currentDate = simpleDateFormat.format(new Date());
        return Integer.valueOf(dateStr) <= Integer.valueOf(currentDate);
    }

    public static boolean isAfterOrEqualseCurrentDay(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String dateStr = simpleDateFormat.format(date);
        String currentDate = simpleDateFormat.format(new Date());
        return Integer.valueOf(dateStr) >= Integer.valueOf(currentDate);
    }

    public static boolean isAfter(Date dateTime) {
        return new DateTime().isAfter(dateTime.getTime());
    }

    /**
     * 计算给定的两个日期的差值(end-start)
     *
     * @param start
     * @param end
     * @return
     */
    public static int calcDiffDays(Date start, Date end) {
        DateTime startDate = new DateTime(start);
        DateTime endDate = new DateTime(end);
        Days days = Days.daysBetween(startDate.withTimeAtStartOfDay(), endDate.withTimeAtStartOfDay());
        return days.getDays();
    }

    /**
     * 计算给定的两个日期的差值(end-start)
     *
     * @param start
     * @param end
     * @return
     */
    public static int calcDiffDays(String start, String end,String pattern ) {
        DateTime startDate = new DateTime(parse(start,pattern));
        DateTime endDate = new DateTime(parse(end,pattern));
        Days days = Days.daysBetween(startDate.withTimeAtStartOfDay(), endDate.withTimeAtStartOfDay());
        return days.getDays();
    }

    /**
     * 当前时间是否在{@code dateTime}之前
     *
     * @param dateTime 时间
     * @return 当前时间是否在{@code dateTime}之前
     */
    public static boolean isBefore(Date dateTime) {
        return new DateTime().isBefore(dateTime.getTime());
    }

    /**
     * 获取当前时间前一天的最后时间
     *
     * @return
     */
    public static String getYesterdayLastTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(allPattern);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取本周的第一天日期和最后一天日期
     *
     * @return arr[0] 第一天日期 ；arr[1]最后一天日期
     */
    public static Date[] getWeekStartAndEndDate(Date date) {
        Date[] arr = new Date[2];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        //所在周开始日期
        arr[0] = cal.getTime();
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //所在周结束日期
        arr[1] = cal.getTime();
        return arr;
    }

    /**
     * 当天时间一周后时间
     *
     * @param beginDate
     * @return
     */
    public static String getWeekDate(String beginDate) {
        String weekEndDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date currdate = format.parse(beginDate);
            Calendar ca = Calendar.getInstance();
            ca.setTime(currdate);
            ca.add(Calendar.DATE, 6);
            currdate = ca.getTime();
            weekEndDate = format.format(currdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekEndDate;
    }

    /**
     * 后一天时间
     *
     * @return
     */
    public static String getNextDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(DateUtils.plusDays(sdf.parse(date), 1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 前一天时间
     *
     * @return
     */
    public static String getBeforeDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(dayPattern);
        try {
            return sdf.format(plusDays(sdf.parse(date), -1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串时间转Date
     *
     * @param str
     * @return
     */
    public static Date StringToDate(String str) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得月份与当前月的差异月份数   日期格式 yyyy-MM
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int getDiffMonth(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        int monthsDiff = Math.abs(yearInterval * 12 + monthInterval);
        return monthsDiff;
    }

    /**
     * 获取当月14几号
     *
     * @param date
     * @return
     */
    public static String getCurrentMonthDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), day);
        Date time = cal2.getTime();
        return format(time, dayPattern);
    }

    /**
     * 获取上个月几号
     *
     * @param date
     * @return
     */
    public static String getLastMonthDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) - 1, day);
        Date time = cal2.getTime();
        return format(time, dayPattern);
    }


    /**
     * 获取大的日期
     *
     */
    public static Date getBigDate(Date date, Date date2) {
        if (null == date) {
            return date2;
        }
        if (null == date2) {
            return date;
        }
        return date.getTime() > date2.getTime() ? date : date2;
    }

    public static void main(String[] args) {

        Date d1 = DateUtils.parse("2020-03-20", DateUtils.dayPattern);
        Date d2 = DateUtils.parse("2019-08-02 01:00:00", DateUtils.allPattern);
        System.out.println(DateUtils.format(new Date(), DateUtils.yearPattern));
        int i = calcDiffDays(d1, d2);
        System.out.println(i);
    }

}

