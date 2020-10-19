package com.sirius.robots.manager.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/16
 */
@Slf4j
public class DateUtil {

    public static Date getNextWeekHour(Integer week,Integer hour){
        LocalDate with = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.of(week)));
        hour = Objects.isNull(hour) ? 0 : hour;
        LocalDateTime localDateTime = with.atTime(hour, 0);
        return asDate(localDateTime);
    }


    //LocalDate -> Date
    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    //LocalDateTime -> Date
    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    //Date -> LocalDate
    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    //Date -> LocalDateTime
    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 格式化
     * @Title: DateToString
     * @Description:
     * @param time
     * @return:
     * @throws
     */
    public static String DateToString(Date time) {
        String newDate = DateFormatUtils.format(time,"yyyy-MM-dd HH:mm:ss");
        return newDate;
    }
}