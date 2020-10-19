package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    NORMAL("NORMAL", "正常"),
    DISABLE("DISABLE", "禁用");

    /** 编码 */
    private String code;

    /**  描述 */
    private String desc;
    public static String explain(String code) {

        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getDesc();
            }
        }
        return null;
    }
}
