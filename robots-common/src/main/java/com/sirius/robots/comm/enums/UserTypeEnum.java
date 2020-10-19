package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    /**
     * 超级管理员
     */
    SUPER("SUPER", "超级管理员"),
    /**
     * 管理员
     */
    MANAGER("MANAGER", "管理员"),
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

    public static String explain(String code) {

        for (UserTypeEnum statusEnum : UserTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }
}