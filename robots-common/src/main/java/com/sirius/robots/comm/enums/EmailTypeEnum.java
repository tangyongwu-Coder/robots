package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/28
 */
@Getter
@AllArgsConstructor
public enum EmailTypeEnum {

    REGISTER("0", "注册","您正在【注册】操作，您的验证码为：{}"),
    FIND_PASS_WORD("1", "找回密码","您正在【找回密码】操作，您的验证码为：{}");

    /** 编码 */
    private String code;

    /**  描述 */
    private String desc;

    private String mode;
    public static EmailTypeEnum explain(String code) {

        for (EmailTypeEnum statusEnum : EmailTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }
}