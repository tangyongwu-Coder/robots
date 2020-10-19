package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/16
 */
@Getter
@AllArgsConstructor
public enum WoolDateTypeEnum {

    /**
     * 一
     */
    WEEK("WEEK","(周|星期|礼拜)[1-7一二三四五六七天日][\\s\\S]\\d{1,2}(时|点)"),
    /**
     * 二
     */
    YEAR_SECOND("YEAR_SECOND","\\d{2,4}年\\d{1,2}月\\d{1,2}(日|号|)[\\s\\S]d{1,2}(时|点)\\d{1,2}分\\d{1,2}秒"),
    /**
     * 三
     */
    YEAR_MINUTE("YEAR_MINUTE","\\d{2,4}年\\d{1,2}月\\d{1,2}(日|号|)[\\s\\S]\\d{1,2}(时|点)\\d{1,2}分"),
    /**
     * 四
     */
    YEAR_HOUR("YEAR_HOUR", "\\d{2,4}年\\d{1,2}月\\d{1,2}(日|号|)[\\s\\S]\\d{1,2}(时|点)"),

    /**
     * 六
     */
    YEAR_DAY("YEAR_DAY", "\\d{2,4}年\\d{1,2}月\\d{1,2}(日|号|)"),
    /**
     * 日
     */
    YEAR_MONTH("YEAR_MONTH", "\\d{2,4}年\\d{1,2}月"),

    /**
     * 五
     */
    MONTH_HOUR("MONTH_HOUR", "\\d{1,2}月\\d{1,2}(日|号|)\\d{1,2}(时|点)"),

    /**
     * 五
     */
    MONTH_DAY("MONTH_DAY", "\\d{1,2}月\\d{1,2}(日|号|)"),
    /**
     * 五
     */
    MONTH("MONTH", "\\d{1,2}月"),

    /**
     * 天
     */
    DAY_HOUR("DAY_HOUR", "\\d{1,2}(日|号|)\\d{1,2}(时|点)"),
    /**
     * 天
     */
    DAY("DAY", "\\d{1,2}(日|号|)"),

    ;

    /**
     * code
     */
    private String code;
    /**
     * reg
     */
    private String reg;

    public static WoolDateTypeEnum explain(String code) {

        for (WoolDateTypeEnum statusEnum : WoolDateTypeEnum.values()) {
            if (statusEnum.getCode().equals(code)) {
                return statusEnum;
            }
        }
        return null;
    }


}