package com.sirius.robots.manager;

import com.sirius.robots.dal.mapper.UserInfoMapper;
import com.sirius.robots.dal.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户信息管理
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019-12-30
 */
@Slf4j
@Component
public class UserManager {

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据登录名查询
     *
     * @param loginName 登录名
     * @return          用户信息表实体类
     */
    public UserInfo selectByLoginUser(String loginName){
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(loginName);
        List<UserInfo> userInfos = userInfoMapper.selectBySelective(userInfo);
        if(CollectionUtils.isEmpty(userInfos)){
            return null;
        }
        return userInfos.get(0);
    }
    /**
     * 新增
     *
     * @param userInfo  用户信息表实体类
     */
    public void add(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

    /**
     * 修改
     *
     * @param userInfo  用户信息表实体类
     */
    public void edit(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * 删除
     *
     * @param userInfo  用户信息表实体类
     */
    public void delete(UserInfo userInfo){
        userInfoMapper.delete(userInfo);
    }

    /**
     * 批量新增
     *
     * @param list  用户信息表实体类
     */
    public void insertBatch(List<UserInfo> list){
        userInfoMapper.insertBatch(list);
    }

    /**
     * 查询
     * @param query 查询条件
     * @return      查询结果
     */
    public List<UserInfo> selectBySelective(UserInfo query){
        return userInfoMapper.selectBySelective(query);
    }

    /**
     * 主键查询
     * @param id    主键
     * @return      查询结果
     */
    public UserInfo selectByPrimaryKey(Long id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

}
