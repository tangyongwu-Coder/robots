package com.sirius.robots.comm.util;

import java.util.regex.Pattern;

/**
 * 系统常量
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/26
 */
public class SystemConstant {

    /**
     * 超时时间 7day
     */
    public static final Long SEVEN_DAY_TIME_OUT = 604800L;


    /**
     * 系统
     */
    public static final String SYSTEM = "SYSTEM";


    /**
     * 编码
     */
    public static final String ENCODE = "UTF-8";

    /**
     * 数字格式
     */
    public static Pattern NUMBER_EXPRESS_PATTERN = Pattern.compile("(^(-?\\d+)(\\.\\d+)?$)");



}
