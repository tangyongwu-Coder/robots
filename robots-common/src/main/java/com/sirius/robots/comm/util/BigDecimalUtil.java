package com.sirius.robots.comm.util;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * BigDecimal工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2018/5/22
 */
public class BigDecimalUtil {

    /**
     * 金额保留位数
     */
    private static Integer AMT_SIZE = 100;
    /**
     * 费率保留位数
     */
    private static Integer RATE_SIZE = 1000000;

    /**
     *  获取精确金额
     *
     * @param amt   金额
     * @return      精确金额
     */
    public static BigDecimal getAmtBig(Long amt){
        if(Objects.isNull(amt)){
            return null;
        }
        return new BigDecimal(amt).divide(new BigDecimal(AMT_SIZE),2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *  获取金额
     *
     * @param amt   精确金额
     * @return      金额
     */
    public static Long getAmt(BigDecimal amt){
        if(Objects.isNull(amt)){
            return null;
        }
        return amt.multiply(new BigDecimal(AMT_SIZE)).setScale(0,BigDecimal.ROUND_HALF_EVEN).longValue();
    }

    /**
     *  获取精确费率
     *
     * @param rate  费率
     * @return      精确费率
     */
    public static BigDecimal getRateBig(Long rate){
        if(Objects.isNull(rate)){
            return null;
        }
        return new BigDecimal(rate).divide(new BigDecimal(RATE_SIZE),6, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *  获取费率
     *
     * @param rate  精确费率
     * @return      费率
     */
    public static Long getRate(BigDecimal rate){
        if(Objects.isNull(rate)){
            return null;
        }
        return new BigDecimal(RATE_SIZE).multiply(rate).setScale(0,BigDecimal.ROUND_HALF_EVEN).longValue();
    }


    /**
     *  获取百分比(预留两位数)
     *
     * @param denominator   分母/除数
     * @param numerator     分子/被除数
     * @return              百分比
     */
    public static BigDecimal getPercent(BigDecimal denominator, BigDecimal numerator){
        if(BigDecimal.ZERO.equals(numerator)){
            return BigDecimal.ZERO;
        }
        return denominator.multiply(new BigDecimal(100)).divide(numerator, 2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     *  获取百分比
     *
     * @param denominator   分母/除数
     * @param numerator     分子/被除数
     * @return              百分比
     */
    public static BigDecimal getPercent(Integer denominator, Integer numerator){
        return getPercent(new BigDecimal(denominator),new BigDecimal(numerator));
    }

    /**
     *  获取百分比
     *
     * @param denominator   分母/除数
     * @param numerator     分子/被除数
     * @return              百分比
     */
    public static BigDecimal getPercent(Long denominator, Long numerator){
        return getPercent(new BigDecimal(denominator),new BigDecimal(numerator));
    }

}
