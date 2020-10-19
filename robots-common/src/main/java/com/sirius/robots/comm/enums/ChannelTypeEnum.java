package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Getter
@AllArgsConstructor
public enum ChannelTypeEnum {

    /**
     * 系统
     */
    SYSTEM("SYSTEM", "系统"),

    /**
     * 用户
     */
    USER("USER", "用户"),
    ;


    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
}