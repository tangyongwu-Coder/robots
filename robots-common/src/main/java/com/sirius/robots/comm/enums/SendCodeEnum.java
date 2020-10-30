package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/28
 */
@Getter
@AllArgsConstructor
public enum SendCodeEnum {

    EMAIL("EMAIL", "邮箱"),
    PHONE("PHONE", "手机");

    /** 编码 */
    private String code;

    /**  描述 */
    private String desc;
    public static String explain(String code) {

        for (SendCodeEnum statusEnum : SendCodeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getDesc();
            }
        }
        return null;
    }
}