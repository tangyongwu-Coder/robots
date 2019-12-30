package com.sirius.robots.manager.util;

import com.sirius.robots.comm.util.EditListUtil;
import com.sirius.robots.dal.mapper.BaseMapper;
import com.sirius.robots.dal.model.BaseDO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 汇总更新工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/30
 */
public class CountEditUtil {

    public static <T extends BaseDO> void editConfig(BaseMapper<T> mapper, Map<String, List<T>> map){
        List<T> addList = map.get(EditListUtil.ADD);
        List<T> editList = map.get(EditListUtil.EDIT);
        List<T> deleteList = map.get(EditListUtil.DELETE);
        insertBatch(mapper,addList);
        updateBatch(mapper,editList);
        deleteBatch(mapper,deleteList);
    }

    /**
     * 批量插入
     * @param list
     */
    private static <T extends BaseDO> void insertBatch(BaseMapper<T> mapper, List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        mapper.insertBatch(list);
    }


    private static <T extends BaseDO>  void deleteBatch(BaseMapper<T> mapper, List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(mapper::delete);
    }

    private static <T extends BaseDO>  void updateBatch(BaseMapper<T> mapper, List<T> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        list.forEach(mapper::updateByPrimaryKeySelective);
    }
}
