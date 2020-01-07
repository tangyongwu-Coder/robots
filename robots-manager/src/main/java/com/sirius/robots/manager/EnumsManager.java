package com.sirius.robots.manager;

import com.sirius.robots.dal.mapper.EnumsInfoMapper;
import com.sirius.robots.dal.model.EnumsInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 枚举信息管理
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020-01-07
 */
@Slf4j
@Component
public class EnumsManager {

    @Autowired
    private EnumsInfoMapper enumsInfoMapper;

    /**
     * 新增
     *
     * @param enumsInfo  枚举信息表实体类
     */
    public void add(EnumsInfo enumsInfo){
        enumsInfoMapper.insert(enumsInfo);
    }

    /**
     * 修改
     *
     * @param enumsInfo  枚举信息表实体类
     */
    public void edit(EnumsInfo enumsInfo){
        enumsInfoMapper.updateByPrimaryKeySelective(enumsInfo);
    }

    /**
     * 删除
     *
     * @param enumsInfo  枚举信息表实体类
     */
    public void delete(EnumsInfo enumsInfo){
        enumsInfoMapper.delete(enumsInfo);
    }

    /**
     * 批量新增
     *
     * @param list  枚举信息表实体类
     */
    public void insertBatch(List<EnumsInfo> list){
        enumsInfoMapper.insertBatch(list);
    }

    /**
     * 查询
     * @param query 查询条件
     * @return      查询结果
     */
    public List<EnumsInfo> selectBySelective(EnumsInfo query){
        return enumsInfoMapper.selectBySelective(query);
    }

    /**
     * 主键查询
     * @param id    主键
     * @return      查询结果
     */
    public EnumsInfo selectByPrimaryKey(Long id){
        return enumsInfoMapper.selectByPrimaryKey(id);
    }

}
