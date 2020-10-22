package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum UserAuthMsgTypeEnum implements BaseMsgTypeEnum{

    /**
     * 用户信息-姓名
     */
    NAME("NAME", "姓名",1,false,"姓名,用户名"),
    /**
     * 用户信息-手机
     */
    PHONE("PHONE", "手机",2,false,"手机,电话,手机号,电话号码"),
    /**
     * 用户信息-邮箱
     */
    EMAIL("EMAIL", "邮箱",3,false,"邮箱,email"),

    /**
     * 用户信息-家庭
     */
    FAMILY("FAMILY", "家庭",4,false,"家庭,家"),
    ;




    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;
    /**
     * 优先级
     */
    private Integer order;

    /**
     * 类型
     */
    private Boolean manager;

    /**
     * 关键字
     */
    private String keyWords;

    public static String explain(String code) {

        for (UserAuthMsgTypeEnum statusEnum : UserAuthMsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}