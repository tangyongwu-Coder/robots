package com.sirius.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@AllArgsConstructor
public enum UsableFlagEnum {

    /** 可用 */
    ENABLE("ENABLE","正常"),

    /** 禁用 */
    DISABLE("DISABLE","禁用");

    /** code */
    private String code;

    /** 描述 */
    private String msg;
}