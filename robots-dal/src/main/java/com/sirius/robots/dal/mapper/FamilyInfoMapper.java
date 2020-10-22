package com.sirius.robots.dal.mapper;

import com.sirius.robots.dal.model.FamilyInfo;
import java.util.List;
/**
 * FamilyInfo数据库操作
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-21
  */
public interface FamilyInfoMapper {

 /**
     * 添加对象所有字段
     *
     * @param record 插入字段对象
     * @return 新增记录数
     */
    int insert(FamilyInfo record);

    /**
     * 删除对象
     *
     * @param record 删除对象
     * @return
     */
    int delete(FamilyInfo record);

    /**
     * 根据ID修改对应字段
     *
     * @param record 修改字段对象
     * @return 修改记录数
     */
    int updateByPrimaryKeySelective(FamilyInfo record);

    /**
     * 批量新增规则
     * @param rules 需要新增的规则集合
     * @return 新增成功记录数
     */
    int insertBatch(List<FamilyInfo> rules);

    /**
     * 根据条件查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    List<FamilyInfo> selectBySelective(FamilyInfo query);

   /**
    * 根据条件查询
    *
    * @param name 查询条件
    * @return 查询结果
    */
   FamilyInfo selectByName(String name);
    /**
     * 根据ID查询
     *
     * @param id 主键ID
     * @return 查询结果
     */
    FamilyInfo selectByPrimaryKey(Integer id);
}
