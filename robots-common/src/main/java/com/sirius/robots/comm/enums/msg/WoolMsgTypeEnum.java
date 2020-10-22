package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum WoolMsgTypeEnum implements BaseMsgTypeEnum{


    /**
     * 线报删除
     */
    WOOL_DELETE("WOOL_DELETE", "线报删除",3,false,"删除"),

    /**
     * 线报查询
     */
    WOOL_QUERY("WOOL_QUERY", "线报查询",5,false,"查询"),

    /**
     * 今日线报
     */
    WOOL_TODAY("WOOL_TODAY", "今日线报",6,false,"今日,今日线报"),

    /**
     * 线报禁用
     */
    WOOL_DISABLE("WOOL_DISABLE", "线报禁用",7,true,"禁用,下线"),

    /**
     * 线报启用
     */
    WOOL_ENABLE("WOOL_ENABLE", "线报启用",8,true,"启用,上线"),

    /**
     * 线报录入
     */
    WOOL_ADD("WOOL_ADD", "线报录入",9,false,""),

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

        for (WoolMsgTypeEnum statusEnum : WoolMsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}