package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum DateTypeEnum {
    /**
     * 秒
     */
    SECOND("ss", "秒"),
    /**
     * 点
     */
    MINUTE("mm", "分"),

    /**
     * 点
     */
    HOUR("HH", "点"),
    /**
     * 日
     */
    DAY1("dd", "日"),
    /**
     * 时
     */
    DAY2("dd", "时"),
    /**
     * 周
     */
    WEEK1("ww", "周"),
    /**
     * 周
     */
    WEEK2("ww", "星期"),
    /**
     * 月
     */
    MONTH("MM", "月"),

    /**
     * 年
     */
    YEAR("yyyy", "年"),
    ;


    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;



    public static DateTypeEnum explainMsg(String msg) {

        for (DateTypeEnum statusEnum : DateTypeEnum.values()) {
            if (statusEnum.getMsg().equals(msg)) {
                return statusEnum;
            }
        }
        return null;
    }



}