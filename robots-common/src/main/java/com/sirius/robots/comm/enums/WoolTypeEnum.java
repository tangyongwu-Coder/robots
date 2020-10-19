package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Getter
@AllArgsConstructor
public enum WoolTypeEnum {

    /**
     * 银行
     */
    BANK("BANK", "银行"),
    /**
     * 其他
     */
    OTHER("OTHER", "其他"),
    ;

    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;



    public static WoolTypeEnum explain(String msg) {

        for (WoolTypeEnum statusEnum : WoolTypeEnum.values()) {
            if (statusEnum.getMsg().equals(msg)) {
                return statusEnum;
            }
        }
        return null;
    }
}