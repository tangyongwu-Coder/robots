package com.sirius.robots.manager;

import com.sirius.robots.dal.mapper.UserInfoMapper;
import com.sirius.robots.dal.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/26
 */
@Slf4j
@Component
public class UserManager {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfo selectByLoginUser(String loginName){
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginName(loginName);
        List<UserInfo> userInfos = userInfoMapper.selectBySelective(userInfo);
        if(CollectionUtils.isEmpty(userInfos)){
            return null;
        }
        return userInfos.get(0);
    }

    public void updateUser(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    public void addUser(UserInfo userInfo){
        userInfoMapper.insert(userInfo);
    }

}