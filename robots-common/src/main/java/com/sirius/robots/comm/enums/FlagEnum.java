package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@AllArgsConstructor
public enum FlagEnum {

    /**
     * 是
     */
    TRUE(1, "1","是"),
    /**
     * 否
     */
    FALSE(0, "0", "否");


    /** code */
    private Integer code;
    /** code */
    private String codeStr;
    /** 描述 */
    private String msg;
}