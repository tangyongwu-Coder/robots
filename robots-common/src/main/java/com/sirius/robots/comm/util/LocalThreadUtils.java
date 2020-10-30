package com.sirius.robots.comm.util;

import com.sirius.robots.comm.constants.HeaderConstants;
import org.slf4j.MDC;

/**
 * 本地线程工具类
 *
 * @author lijing
 * @date 2017/11/29 0029
 */
public class LocalThreadUtils {

    /**
     * 获取本地线程属性
     * @return 属性值
     */
    public static String getToken(){
        String systemType = getLocalThreadProperties(HeaderConstants.HEADER_SYSTEM_TYPE);
        String headerUserToken = HeaderConstants.HEADER_USER_TOKEN;
        return MDC.get(systemType+"_"+ headerUserToken);
    }

    public static void updateToken(String systemType,String value){
        String headerUserToken = HeaderConstants.HEADER_USER_TOKEN;
        MDC.put(systemType+"_"+ headerUserToken,value);
    }
    /**
     * 获取本地线程属性
     * @param propertiesKey 属性key
     * @return 属性值
     */
    public static String getLocalThreadProperties(String propertiesKey){
        return MDC.get(propertiesKey);
    }

    /**
     * 设置本地线程属性
     * @param key 属性key
     * @param value 属性值
     */
    public static void updateLocalThreadProperties(String key,String value){
        MDC.put(key, value);
    }


}
