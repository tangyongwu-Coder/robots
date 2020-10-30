package com.sirius.robots.comm.constants;

/**
 * 系统静态常量
 * @author lijing
 * @date 2018/1/4 0004.
 */
public class SystemConstants {


    /**
     * 构建登录用户redis缓存key
     *
     * @param id 用户id
     * @return 缓存key
     */
    public static String buildLoginUserRedisKey(Long id,String systemType) {
        return ServiceConstants.LOGIN_USER_REDIS_KEY + id + systemType;
    }

    /**
     * 构建登录用户redis缓存key
     *
     * @param id 用户id
     * @return 缓存key
     */
    public static String buildLoginUserRefreshRedisKey(String id,String systemType) {
        return ServiceConstants.LOGIN_USER_REFRESH_REDIS_KEY + id + systemType;
    }

}
