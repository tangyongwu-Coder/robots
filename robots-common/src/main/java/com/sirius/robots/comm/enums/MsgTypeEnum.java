package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum MsgTypeEnum{

    /**
     * 用户信息
     */
    USER_AUTH("USER_AUTH", "用户授权",1,false,"姓名,用户名"),
    /**
     * 活动录入
     */
    WOOL_ADD("WOOL_ADD", "活动录入",2,false,"录入,新增"),
    /**
     * 活动删除
     */
    WOOL_DELETE("WOOL_DELETE", "活动删除",3,false,"删除"),

    /**
     * 活动更新
     */
    WOOL_EDIT("WOOL_EDIT", "活动更新",4,false,"更新,修改"),

    /**
     * 活动查询
     */
    WOOL_QUERY("WOOL_QUERY", "活动查询",5,false,"活动,查询,活动查询"),

    /**
     * 今日活动
     */
    WOOL_TODAY("WOOL_TODAY", "今日活动",6,false,"今日,今日活动"),

    /**
     * 活动禁用
     */
    WOOL_DISABLE("WOOL_DISABLE", "活动禁用",7,true,"禁用,下线"),

    /**
     * 活动启用
     */
    WOOL_ENABLE("WOOL_ENABLE", "活动启用",8,true,"启用,上线"),


    /**
     * 帮助
     */
    HELP("HELP", "帮助",999,false,"帮助,help"),

    /**
     * 无
     */
    NULL("NULL", "无",9999,false,""),
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

        for (MsgTypeEnum statusEnum : MsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}