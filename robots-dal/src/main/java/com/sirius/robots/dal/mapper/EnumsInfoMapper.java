package com.sirius.robots.dal.mapper;

import com.sirius.robots.dal.model.EnumsInfo;

import java.util.List;

/**
 * 枚举信息表数据库操作
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-01-07
  */
public interface EnumsInfoMapper extends BaseMapper<EnumsInfo>{


    /**
     * 根据条件查询
     *
     * @return 查询结果
     */
    List<EnumsInfo> selectAllType();
}
