package com.sirius.robots.comm.enums.msg;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Getter
@AllArgsConstructor
public enum SpendMsgTypeEnum implements BaseMsgTypeEnum{
    /**
     * 开支-删除
     */
    DELETE("DELETE", "删除",1,false,"删除"),
    /**
     * 开支-收入
     */
    INCOME("INCOME", "收入",2,false,"收入,工资,余额,+"),
    /**
     * 开支-支出
     */
    PAY("PAY", "支出",3,false,"支出,消费,还款,-"),
    /**
     * 开支-查询
     */
    QUERY("QUERY", "查询",4,false,"查询"),

    /**
     * 开支-日期查询
     */
    DATE("DATE", "日期查询",5,false,"日期,时间"),

    /**
     * 开支-总计
     */
    COUNT("COUNT", "总计",6,false,"总计,统计"),

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

        for (SpendMsgTypeEnum statusEnum : SpendMsgTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum.getMsg();
            }
        }
        return null;
    }

}