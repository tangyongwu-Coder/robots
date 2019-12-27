package com.sirius.robots.comm.util;

import com.sirius.robots.comm.enums.DeleteFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 列表编辑工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/6/28
 */
@Slf4j
public class EditListUtil {
    /**
     * 新增
     */
    public static final String ADD = "ADD";
    /**
     * 删除
     */
    public static final String DELETE = "DELETE";
    /**
     * 修改
     */
    public static final String EDIT = "EDIT";
    /**
     * 创建人字段名
     */
    private static final String CREATED_BY = "createdBy";

    /**
     * 编号
     */
    private static final String ID = "id";
    /**
     * 更新人字段名
     */
    private static final String UPDATED_BY = "updatedBy";
    /**
     * 失效字段名
     */
    private static final String USABLE_FLAG = "deleteFlag";

    /**
     * 处理分离
     *
     * @param existList     数据库存在的列表
     * @param records       新列表
     * @param fieldCodes     唯一字段(多个以,分割)
     * @param operator      操作员
     * @param <T>           对象
     * @return              分离后结果
     */
    public static <T> Map<String,List<T>> editSeparate(List<T> existList, List<T> records,
                                                       String fieldCodes,
                                                       String operator){
        return EditListUtil.editSeparate(
                existList, records, fieldCodes, USABLE_FLAG, operator);
    }
    /**
     * 处理分离
     *
     * @param existList     数据库存在的列表
     * @param records       新列表
     * @param fieldCodes     唯一字段
     * @param usableCode    失效字段
     * @param operator      操作员
     * @param <T>           对象
     * @return              分离后结果
     */
    public static <T> Map<String,List<T>> editSeparate(List<T> existList, List<T> records,
                                                       String fieldCodes, String usableCode,
                                                       String operator ){
        Map<String,List<T>> map = new HashMap<>(3);
        //如果原有为空,则全部为新增
        if(CollectionUtils.isEmpty(existList)){
            for (T t : records) {
                BeanUtil.setProperty(t,CREATED_BY,operator);
                BeanUtil.setProperty(t,usableCode, DeleteFlagEnum.NORMAL.getCode());
            }
            map.put(ADD,records);
            return map;
        }
        Map<String, T> listMap = groupingByUniqueKey(existList,fieldCodes);
        List<T> addList = new ArrayList<>();
        List<T> editList = new ArrayList<>();
        for (T t : records) {
            String uniqueKey = getUniqueKey(t, fieldCodes);
            T exist = listMap.get(uniqueKey);
            if(Objects.isNull(exist)){
                BeanUtil.setProperty(t,CREATED_BY,operator);
                BeanUtil.setProperty(t,usableCode, DeleteFlagEnum.NORMAL.getCode());
                addList.add(t);
            }else{
                BeanUtil.setProperty(t,usableCode, DeleteFlagEnum.NORMAL.getCode());
                BeanUtil.setProperty(t,UPDATED_BY,operator);
                String id = BeanUtil.getPropertyStr(exist, ID);
                BeanUtil.setProperty(t,ID,id);
                editList.add(t);
                listMap.remove(uniqueKey);
            }
        }
        List<T> deleteList = new ArrayList<>();
        for (Map.Entry<String, T> entry : listMap.entrySet()) {
            BeanUtil.setProperty(entry.getValue(),UPDATED_BY,operator);
            deleteList.add(entry.getValue());
        }
        map.put(ADD,addList);
        map.put(DELETE,deleteList);
        map.put(EDIT,editList);
        return map;
    }

    /**
     * 根据唯一key分组
     *
     * @param existList     数据库存在的列表
     * @param fieldCodes    唯一字段
     * @param <T>           对象
     * @return              分组后结果
     */
    private static<T> Map<String, T> groupingByUniqueKey(List<T> existList,String fieldCodes){
        if(CollectionUtils.isEmpty(existList)){
            return new HashMap<>(1);
        }
        Map<String, T> map = new HashMap<>(existList.size());
        for (T t : existList) {
            String uniqueKey = getUniqueKey(t,fieldCodes);
            map.put(uniqueKey,t);
        }
        return map;
    }

    /**
     * 根据key分组(两层)
     *
     * @param existList     数据库存在的列表
     * @param fieldCodes    唯一字段
     * @param <T>           对象
     * @return              分组后结果
     */
    public static<T> Map<String, TreeMap<String,T>> groupingByKey(List<T> existList,String fieldCodes,String fieldCodes2){
        if(CollectionUtils.isEmpty(existList)){
            return new HashMap<>(1);
        }
        Map<String, TreeMap<String,T>> map = new HashMap<>(existList.size());
        for (T t : existList) {
            String key1 = getUniqueKey(t,fieldCodes);
            TreeMap<String,T> map2 = map.get(key1);
            if(Objects.isNull(map2)){
                map2 = new TreeMap<>();
                map.put(key1,map2);
            }
            String key2 = getUniqueKey(t, fieldCodes2);
            map2.put(key2,t);
        }

        return map;
    }

    /**
     * 根据key分组
     *
     * @param existList     数据库存在的列表
     * @param fieldCodes    唯一字段
     * @param <T>           对象
     * @return              分组后结果
     */
    public static<T> Map<String, List<T>> groupingByKey(List<T> existList,String fieldCodes){
        if(CollectionUtils.isEmpty(existList)){
            return new HashMap<>(1);
        }
        Map<String, List<T>> map = new HashMap<>(existList.size());
        for (T t : existList) {
            String key = getUniqueKey(t,fieldCodes);
            List<T> list = map.get(key);
            if(CollectionUtils.isEmpty(list)){
                list = new ArrayList<>();
                map.put(key,list);
            }
            list.add(t);
        }
        return map;
    }

    /**
     * 获取唯一key
     *
     * @param t             数据
     * @param fieldCodes    唯一字段
     * @param <T>           对象
     * @return              唯一key
     */
    private static <T> String getUniqueKey(T t,String fieldCodes){
        StringBuilder uniqueKey = new StringBuilder();
        String[] fieldCodeList = fieldCodes.split(",");
        for (String fieldCode : fieldCodeList) {
            String propertyStr = getPropertyStr(t, fieldCode);
            uniqueKey.append(propertyStr).append(",");
        }
        return uniqueKey.substring(0,uniqueKey.length()-1);
    }

    /**
     * 获取值
     *
     * @param t             数据
     * @param fieldCode     字段
     * @param <T>           对象
     * @return              值
     */
    private static <T> String getPropertyStr(T t,String fieldCode){
        String propertyStr = BeanUtil.getPropertyStr(t, fieldCode);
        if(StringUtils.isEmpty(propertyStr)){
            propertyStr = "ALL";
        }
        return propertyStr;
    }
}