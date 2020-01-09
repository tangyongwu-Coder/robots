package com.sirius.robots.manager;

import com.github.pagehelper.PageHelper;
import com.sirius.robots.comm.bo.PageDTO;
import com.sirius.robots.comm.req.PageQueryReqDTO;
import com.sirius.robots.dal.mapper.EnumsInfoMapper;
import com.sirius.robots.dal.model.EnumsInfo;
import com.sirius.robots.manager.util.CountEditUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
     * 分页查询
     * @param query 查询条件
     * @return      查询结果
     */
    public PageDTO<EnumsInfo> selectByPage(EnumsInfo query , PageQueryReqDTO pageQueryDTO){
        PageDTO pageDTO = pageQueryDTO.getPageDTO();
        pageDTO.checkPage();
        PageHelper.startPage(pageDTO.getPage(), pageDTO.getPageSize());
        List<EnumsInfo> data = enumsInfoMapper.selectBySelective(query);
        return new PageDTO<>(data);
    }
    /**
     * 主键查询
     * @param id    主键
     * @return      查询结果
     */
    public EnumsInfo selectByPrimaryKey(Long id){
        return enumsInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改
     *
     * @param map   编辑对象
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void editConfig(Map<String, List<EnumsInfo>> map){
        CountEditUtil.editConfig(enumsInfoMapper,map);
    }

}
