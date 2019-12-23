package com.sirius.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * 工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/4/29
 */
@Slf4j
public class BeanUtil {

    /**
     * 参数获取属性值
     *
     * @param bean  实体
     * @param name  属性名称
     * @return      属性值
     */
    public static String getPropertyStr(Object bean, String name){
        Object property = getProperty(bean, name);
        if(Objects.isNull(property)){
            return null;
        }
        return property.toString();
    }
    /**
     * 参数获取属性值
     *
     * @param bean  实体
     * @param name  属性名称
     * @return      属性值
     */
    public static Object getProperty(Object bean, String name){
        try {
            if(StringUtils.isEmpty(name)){
                return bean;
            }
            return BeanUtils.getProperty(bean,name);
        } catch (IllegalAccessException e) {
            log.error("请求参数获取失败,bean:{},name:{},e1:{}",name,e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            log.error("请求参数获取失败,bean:{},name:{},e2:{}",name,e.getMessage());
            return null;
        } catch (NoSuchMethodException e) {
            log.error("请求参数获取失败,bean:{},name:{},e3:{}",name,e.getMessage());
            return null;
        }
    }

    /**
     * 参数设置属性值
     *
     * @param bean  实体
     * @param name  属性名称
     * @param value 属性值
     */
    public static void setProperty(Object bean, String name,Object value){
        try {
            if(Objects.isNull(value)){
                return;
            }
            BeanUtils.setProperty(bean,name,value);
        } catch (IllegalAccessException e) {
            log.error("请求参数获取失败,bean:{},name:{},e1:{}",name,e.getMessage());
        } catch (InvocationTargetException e) {
            log.error("请求参数获取失败,bean:{},name:{},e2:{}",name,e.getMessage());
        }
    }
}
