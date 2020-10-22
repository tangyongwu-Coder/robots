package com.sirius.robots.manager.util;

import com.sirius.robots.comm.enums.*;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.dal.model.WoolInfo;
import com.sirius.robots.manager.model.DateBO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
public class WoolUtil {


    private final static String EVERY = "每";
    private final static String[] BANKS = {
            "建行","招行","交行","中信","邮储","浦发","农行",
            "平安","工行","网商","中行","光大","广发","民生",
            "兴业"};

    private static String[] PATTERN = new String[] {};

    static {
        PATTERN = new String[] {};
        PATTERN = getPattern();
    }
    /**
     * 从字符串中截取出正确的时间
     *
     * 周四10点
     * 10号11点
     * 10号
     * 11月10号11点
     * 2020年11月10号11点
     * 2020年11月
     *
     */
    private static String findDate(WoolInfo woolInfo,String data) {
        for (WoolDateTypeEnum woolDateTypeEnum : WoolDateTypeEnum.values()) {
            String temp = match(woolDateTypeEnum.getReg(), data);
            if (StringUtils.isNotEmpty(temp)) {
                woolInfo.setWoolDate(temp);
                woolInfo.setDateType(woolDateTypeEnum.getCode());
                return temp;
            }
        }
        return null;
    }
    public static String match(String reg, String stringTime) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(stringTime);
        String s = "";
        if (m.find()) {
            s += m.group();
        }
        return s;
    }


    public static WoolInfo getWool(String woolMsg){
        WoolInfo woolInfo = new WoolInfo();
        woolInfo.setWoolType(getWoolType(woolMsg).getCode());
        woolInfo.setWoolMsg(woolMsg);
        getDate(woolInfo,woolMsg);
        return woolInfo;
    }

    private static WoolTypeEnum getWoolType(String msg){
        for (String bank : BANKS) {
            if(msg.contains(bank)){
                return WoolTypeEnum.BANK;
            }
        }
        return WoolTypeEnum.OTHER;
    }

    private static void getDate(WoolInfo woolInfo,String msg){
        Boolean isEvery = msg.trim().startsWith(EVERY);
        if(isEvery){
            msg = msg.substring(1,msg.length());
        }
        String date = findDate(woolInfo, msg);
        if(StringUtils.isNotEmpty(date)){
            String title = msg.replaceAll(date, StringUtils.EMPTY).trim();
            woolInfo.setWoolTitle(title);
            woolInfo.setWoolDate(date);
            DateBO dateBO = processDate(date, woolInfo.getDateType());
            log.debug("dateBO:{}",dateBO);
            if(Objects.nonNull(dateBO)){
                Date nextDate = dateBO.getNextDate();
                log.debug("nextDate:{}", com.sirius.robots.comm.util.DateUtils.format(nextDate, com.sirius.robots.comm.util.DateUtils.allPattern));
                woolInfo.setNextTime(nextDate);
            }
            return;
        }
        woolInfo.setWoolTitle(msg);
    }

    private static DateBO processDate(String woolDate,String dateType){
        WoolDateTypeEnum explain = WoolDateTypeEnum.explain(dateType);
        DateBO dateBO = new DateBO();
        dateBO.setIsWeek(Boolean.FALSE);
        dateBO.setDateTypeEnum(explain);
        Calendar c = null;
        if(!WoolDateTypeEnum.WEEK.equals(explain)){
            Date date = formatDate(woolDate);
            c = Calendar.getInstance();
            if(Objects.isNull(date)) {
                return null;
            }
            c.setTime(date);
            dateBO.setFormatDate(date);
        }

        switch (explain){
            case WEEK:
                processWeek(woolDate,dateBO);
                break;
            case YEAR_SECOND:
                dateBO.setYear(c.get(Calendar.YEAR));
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                dateBO.setHour(c.get(Calendar.HOUR_OF_DAY));
                dateBO.setMinute(c.get(Calendar.MINUTE));
                dateBO.setSecond(c.get(Calendar.SECOND));
                break;
            case YEAR_MINUTE:
                dateBO.setYear(c.get(Calendar.YEAR));
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                dateBO.setHour(c.get(Calendar.HOUR_OF_DAY));
                dateBO.setMinute(c.get(Calendar.MINUTE));
                break;
            case YEAR_HOUR:
                dateBO.setYear(c.get(Calendar.YEAR));
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                dateBO.setHour(c.get(Calendar.HOUR_OF_DAY));
                break;
            case YEAR_DAY:
                dateBO.setYear(c.get(Calendar.YEAR));
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                break;
            case YEAR_MONTH:
                dateBO.setYear(c.get(Calendar.YEAR));
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                break;
            case MONTH_HOUR:
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                dateBO.setHour(c.get(Calendar.HOUR_OF_DAY));
                break;
            case MONTH_DAY:
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                break;
            case MONTH:
                dateBO.setMonth(c.get(Calendar.MONTH)+1);
                break;
            case DAY_HOUR:
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                dateBO.setHour(c.get(Calendar.HOUR_OF_DAY));
                break;
            case DAY:
                dateBO.setDay(c.get(Calendar.DAY_OF_MONTH));
                break;
            default:
                break;
        }
        return dateBO;
    }

    /**
     *
     * @Description: 把String格式的时间转化为date
     * @param woolDate
     * @return:
     * @throws
     */
    private static Date formatDate(String woolDate) {
        Date date = null;
        if (StringUtils.isNotBlank(woolDate)) {
            try {
                date = DateUtils.parseDate(woolDate, PATTERN);
            } catch (ParseException e) {
               log.error("格式转换错误,woolDate:{}",woolDate);
            }
        }
        return date;

    }

    private static String[] getPattern(){
        String[] year = new String[] {"yyyy年","yy年"};
        String[] month = new String[] {"MM月"};
        String[] day = new String[] {"dd日","dd号"};
        String[] empty = new String[] {" ",""};
        String[] hour = new String[] {"HH时","HH点"};

        String[] minute = new String[] {"mm分"};

        String[] second = new String[] {"ss秒"};

        Map<Integer,String[]> map = new HashMap<>();
        int i = 1;
        map.put(i++,year);
        map.put(i++,month);
        map.put(i++,day);
        map.put(i++,empty);
        map.put(i++,hour);
        map.put(i++,minute);
        map.put(i,second);
        Set<String> list = new HashSet<>();
        Set<String> array = array(1, list, map);
        System.out.println(array);
        return array.toArray(new String[array.size()]);
    }

    private static Set<String> array(Integer idx,Set<String> list,Map<Integer,String[]> map){
        if(idx>map.size()){
            return list;
        }
        Set<String> newList = new HashSet<>();
        String[] arr1 = map.get(idx);
        //加入到老值
        for (String sb : list) {
            List<String> arr = arr(sb, arr1);
            newList.addAll(arr);
        }
        //作为新的起始值
        for (String s : arr1) {
            if(StringUtils.isNotEmpty(s.trim())){
                newList.add(s);
            }
        }
        Set<String> array = array(idx + 1, newList, map);
        newList.addAll(array);
        return newList;
    }

    private static List<String> arr(String str,String[] arr1){
        List<String> list = new ArrayList<>();
        for (String s : arr1) {
            list.add(str+s);
        }
        return list;
    }

    private static void processWeek(String woolDate,DateBO dateBO){
        //周四11   星期五10
        boolean isWeek1 = woolDate.contains(DateTypeEnum.WEEK1.getMsg());
        Integer weekIdx = null;
        if(isWeek1){
            weekIdx = woolDate.indexOf(DateTypeEnum.WEEK1.getMsg());
        }
        boolean isWeek2 = woolDate.contains(DateTypeEnum.WEEK2.getMsg());
        if(isWeek2){
            weekIdx = woolDate.indexOf(DateTypeEnum.WEEK2.getMsg());
        }
        if(Objects.isNull(weekIdx)){
            return;
        }
        char c = woolDate.charAt(weekIdx + 1);
        String s = Character.toString(c);
        Integer weekDate = WeekTypeEnum.explain(s);
        String date = woolDate.substring(weekIdx + 2,woolDate.length()-1);
        dateBO.setIsWeek(Boolean.TRUE);
        dateBO.setWeek(weekDate);
        dateBO.setHour(Integer.valueOf(date));
    }

    public static void main(String[] args) {
        String msg = "周四10点招行霸王餐";
        String msg2 = "新增周六11点建行龙卡抢券";

        String msg3 = "新增10号11点建行龙卡抢券";

        String msg4 = "新增11月10号11点建行龙卡抢券";
        String msg5 = "2";
        WoolInfo wool2 = getWool(msg);
        System.out.println("wool:"+wool2);

    }

    /**
     * (1)能匹配的年月日类型有：
     *    2014年4月19日
     *    2014年4月19号
     *    2014-4-19
     *    2014/4/19
     *    2014.4.19
     * (2)能匹配的时分秒类型有：
     *    15:28:21
     *    15:28
     *    5:28 pm
     *    15点28分21秒
     *    15点28分
     *    15点
     * (3)能匹配的年月日时分秒类型有：
     *    (1)和(2)的任意组合，二者中间可有任意多个空格
     * 如果dateStr中有多个时间串存在，只会匹配第一个串，其他的串忽略
     * @param
     * @return
     */
    private static String matchDateString(String dateStr) {
        try {
            List matches = null;
            Pattern p = Pattern.compile(
                    "(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)", Pattern.CASE_INSENSITIVE|Pattern.MULTILINE);
            Matcher matcher = p.matcher(dateStr);
            if (matcher.find() && matcher.groupCount() >= 1) {
                matches = new ArrayList();
                for (int i = 1; i <= matcher.groupCount(); i++) {
                    String temp = matcher.group(i);
                    matches.add(temp);
                }
            } else {
                matches = Collections.EMPTY_LIST;
            }
            if (matches.size() > 0) {
                return ((String) matches.get(0)).trim();
            } else {
            }
        } catch (Exception e) {
            return "";
        }

        return dateStr;
    }


}