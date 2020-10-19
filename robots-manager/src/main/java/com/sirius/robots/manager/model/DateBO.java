package com.sirius.robots.manager.model;

import com.sirius.robots.comm.enums.DateTypeEnum;
import com.sirius.robots.comm.enums.WoolDateTypeEnum;
import com.sirius.robots.manager.util.DateUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/16
 */
@Data
public class DateBO {

    private Integer year;

    private Integer month;

    private Integer week;

    private Integer day;

    private Integer hour;

    private Integer minute;

    private Integer second;

    private Boolean isWeek;

    private WoolDateTypeEnum dateTypeEnum;

    private Date formatDate;


    public String getWoolDate(){
        if(isWeek){
           return getWeekStr() + getHourStr();
        }else{
            return getYearStr() + getMonthStr() + getDayStr() + getHourStr();
        }

    }

    public Date getNextDate(){
        LocalDate now = LocalDate.now();
        switch (dateTypeEnum){
            case WEEK:
                return DateUtil.getNextWeekHour(week,hour);
            case YEAR_SECOND:
                return formatDate;
            case YEAR_MINUTE:
                return formatDate;
            case YEAR_HOUR:
                return formatDate;
            case YEAR_DAY:
                return formatDate;
            case YEAR_MONTH:
                return formatDate;
            case MONTH_HOUR:
                year = now.getMonthValue()>month ? now.getYear()+1 : now.getYear();
                return initDate();
            case MONTH_DAY:
                year = now.getMonthValue()>month ? now.getYear()+1 : now.getYear();
                return initDate();
            case MONTH:
                year = now.getMonthValue()>month ? now.getYear()+1 : now.getYear();
                return initDate();
            case DAY_HOUR:
                year = now.getMonthValue()==12 ? now.getYear()+1 : now.getYear();
                month = now.getDayOfMonth()>day ? now.getMonthValue()+1 : now.getMonthValue();
                return initDate();
            case DAY:
                year = now.getMonthValue()==12 ? now.getYear()+1 : now.getYear();
                month = now.getDayOfMonth()>day ? now.getMonthValue()+1 : now.getMonthValue();
                return initDate();
        }

        return null;
    }

    private  Date initDate(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month-1);
        c.set(Calendar.DAY_OF_MONTH,Objects.isNull(day)?1:day);
        c.set(Calendar.HOUR_OF_DAY,Objects.isNull(hour)?0:hour);
        c.set(Calendar.MINUTE,Objects.isNull(minute)?0:minute);
        c.set(Calendar.SECOND,Objects.isNull(second)?0:second);
        return c.getTime();
    }


    private String getSecondStr(){
        if(Objects.isNull(second)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.SECOND.getCode()+second;
    }

    private String getMinuteStr(){
        if(Objects.isNull(minute)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.MINUTE.getCode()+minute;
    }
    private String getHourStr(){
        if(Objects.isNull(hour)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.HOUR.getCode()+hour;
    }

    private String getDayStr(){
        if(Objects.isNull(day)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.DAY1.getCode()+day;
    }

    private String getWeekStr(){
        if(Objects.isNull(week)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.WEEK1.getCode()+week;
    }

    private String getMonthStr(){
        if(Objects.isNull(month)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.MONTH.getCode()+month;
    }

    private String getYearStr(){
        if(Objects.isNull(year)){
            return StringUtils.EMPTY;
        }
        return DateTypeEnum.YEAR.getCode()+year;
    }
}