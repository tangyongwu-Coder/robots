package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum WeekTypeEnum {
    /**
     * 一
     */
    WEEK1("一", 1),
    /**
     * 二
     */
    WEEK2("二", 2),
    /**
     * 三
     */
    WEEK3("三", 3),
    /**
     * 四
     */
    WEEK4("四", 4),
    /**
     * 五
     */
    WEEK5("五", 5),

    /**
     * 六
     */
    WEEK6("六", 6),
    /**
     * 日
     */
    WEEK7("日", 7),
    /**
     * 天
     */
    WEEK8("天", 7),
    ;

    /**
     * 描述
     */
    private String msg;
    /**
     * code
     */
    private Integer code;


    public static Integer explain(String msg) {

        for (WeekTypeEnum statusEnum : WeekTypeEnum.values()) {
            if (statusEnum.getMsg().equals(msg)) {
                return statusEnum.getCode();
            }
        }
        return Integer.valueOf(msg);
    }



}