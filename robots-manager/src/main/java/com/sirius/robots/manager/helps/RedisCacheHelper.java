package com.sirius.robots.manager.helps;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis 缓存帮助类
 * @author lijing
 * @date 2017/12/13 0013.
 */
@Slf4j
@Component
public class RedisCacheHelper {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /** 默认超时时间 3day */
    private static Long DEFAULT_TIME_OUT = 259200L;

    /**
     * redis新增记录(默认超时时间 72小时)
     * @param key 新增key
     * @param obj 新增对象
     */
    public void insertObject(String key,Object obj){
       insertObject(key,obj,DEFAULT_TIME_OUT);
    }

    /**
     * redis新增记录
     * @param key 新增key
     * @param obj 新增对象
     * @param timeOut 超时时间(单位:s 负数表示永不过期)
     */
    public void insertObject(String key,Object obj,Long timeOut){
        String jsonValue = JSON.toJSONString(obj);
        if(timeOut>0) {
            redisTemplate.opsForValue().set(key, jsonValue, timeOut, TimeUnit.SECONDS);
            return;
        }
        redisTemplate.opsForValue().set(key, jsonValue);
    }
    /**
     * redis新增记录
     * @param key 新增key
     * @param str 新增对象
     * @param timeOut 超时时间(单位:s 负数表示永不过期)
     */
    public void insertString(String key,String str,Long timeOut){
        if(timeOut>0) {
            redisTemplate.opsForValue().set(key, str, timeOut, TimeUnit.SECONDS);
            return;
        }
        redisTemplate.opsForValue().set(key, str);
    }


    /**
     * 获取redis记录
     * @param key 查询key
     * @param beanClass 需要转换对象的class
     * @param <T> 泛型class
     * @return 查询结果
     */
    public <T> T queryObject(String key,Class<T> beanClass ){
        String jsonValue = null;
        try {
            jsonValue = redisTemplate.opsForValue().get(key);
            if(StringUtils.isBlank(jsonValue)){
                 return null;
            }
            return JSON.parseObject(jsonValue,beanClass);
        }catch (Exception e){
            log.error("redis 获取数据异常，获取到结果:{},转换对象:{}",jsonValue,beanClass);
            return null;
        }
    }

    /**
     * 获取redis记录
     * @param key 查询key
     * @return 查询结果
     */
    public String queryString(String key){
        String jsonValue = null;
        try {
            jsonValue = redisTemplate.opsForValue().get(key);
            if(StringUtils.isBlank(jsonValue)){
                return null;
            }
            return jsonValue;
        }catch (Exception e){
            log.error("redis 获取数据异常，获取到结果:{}",jsonValue);
            return null;
        }
    }

    /**
     * 删除redis key
     * @param key redis key
     */
    public void deleteKey(String key){
        try {
            redisTemplate.delete(key);
        }catch (Exception e){
            log.error("redis key:{},删除异常",key);
        }
    }

    /**
     * 设置redis 锁
     * @param key 锁key
     * @param value 值
     * @param timeOut 超时时间
     * @return true 获取锁成功 false 获取锁失败
     */
    public boolean lock(String key,String value,Long timeOut){
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            redisTemplate.expire(key,timeOut,TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 解除redis 锁
     * @param key 锁key
     * @param value 值
     */
    public void unLock(String key, String value) {
        String redisValue = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(redisValue)) {
            return;
        }
        if (value.equals(redisValue)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 更新过期时间
     *
     * @param keyEnum key
     * @param timeout 时间
     */
    public void expire(String keyEnum, long timeout) {
        if (timeout > 0) {
            redisTemplate.expire(keyEnum, timeout, TimeUnit.MINUTES);
        }
    }

    /**
     * 获取过期时间
     *
     * @param keyEnum key
     */
    public Long getExpire(String keyEnum) {
        return redisTemplate.getExpire(keyEnum);
    }

}
