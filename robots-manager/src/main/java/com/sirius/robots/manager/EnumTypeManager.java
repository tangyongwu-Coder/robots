package com.sirius.robots.manager;

import com.sirius.robots.dal.mapper.EnumTypeInfoMapper;
import com.sirius.robots.dal.model.EnumTypeInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 枚举类型信息管理
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020-01-07
 */
@Slf4j
@Component
public class EnumTypeManager {

    @Autowired
    private EnumTypeInfoMapper enumTypeInfoMapper;

    /**
     * 新增
     *
     * @param enumTypeInfo  枚举类型信息表实体类
     */
    public void add(EnumTypeInfo enumTypeInfo){
        enumTypeInfoMapper.insert(enumTypeInfo);
    }

    /**
     * 修改
     *
     * @param enumTypeInfo  枚举类型信息表实体类
     */
    public void edit(EnumTypeInfo enumTypeInfo){
        enumTypeInfoMapper.updateByPrimaryKeySelective(enumTypeInfo);
    }

    /**
     * 删除
     *
     * @param enumTypeInfo  枚举类型信息表实体类
     */
    public void delete(EnumTypeInfo enumTypeInfo){
        enumTypeInfoMapper.delete(enumTypeInfo);
    }

    /**
     * 批量新增
     *
     * @param list  枚举类型信息表实体类
     */
    public void insertBatch(List<EnumTypeInfo> list){
        enumTypeInfoMapper.insertBatch(list);
    }

    /**
     * 查询
     * @param query 查询条件
     * @return      查询结果
     */
    public List<EnumTypeInfo> selectBySelective(EnumTypeInfo query){
        return enumTypeInfoMapper.selectBySelective(query);
    }

    /**
     * 主键查询
     * @param id    主键
     * @return      查询结果
     */
    public EnumTypeInfo selectByPrimaryKey(Long id){
        return enumTypeInfoMapper.selectByPrimaryKey(id);
    }

}
