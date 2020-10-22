package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum TicketMsgTypeEnum implements BaseMsgTypeEnum{

     /**
     * 券码删除
     */
    TICKET_DELETE("TICKET_DELETE", "券码删除",3,false,"删除"),

    /**
     * 券码查询
     */
    TICKET_QUERY("TICKET_QUERY", "券码查询",5,false,"券码,查询"),

    /**
     * 今日券码
     */
    TICKET_TODAY("TICKET_TODAY", "今日券码",6,false,"今日,今日券码"),

    /**
     * 券码禁用
     */
    TICKET_DISABLE("TICKET_DISABLE", "券码禁用",7,true,"禁用,下线"),

    /**
     * 券码启用
     */
    TICKET_ENABLE("TICKET_ENABLE", "券码启用",8,true,"启用,上线"),

    /**
     * 券码已使用
     */
    TICKET_USER("TICKET_USER", "券码已使用",8,true,"已使用,使用,用完"),

    /**
     * 券码录入
     */
    TICKET_ADD("TICKET_ADD", "券码录入",9,false,""),
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

        for (TicketMsgTypeEnum statusEnum : TicketMsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}