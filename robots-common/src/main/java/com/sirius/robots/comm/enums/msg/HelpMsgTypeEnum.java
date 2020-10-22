package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum HelpMsgTypeEnum implements BaseMsgTypeEnum{
    /**
     * 帮助
     */
    HELP("HELP", "帮助",1,false,"帮助,help,HELP"),
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

        for (HelpMsgTypeEnum statusEnum : HelpMsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}