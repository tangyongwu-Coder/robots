package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@AllArgsConstructor
public enum DeleteFlagEnum {

    /**
     * 删除
     */
    DELETE(1, "删除"),
    /**
     * 正常
     */
    NORMAL(0, "正常");


    /** code */
    private Integer code;

    /** 描述 */
    private String msg;
}