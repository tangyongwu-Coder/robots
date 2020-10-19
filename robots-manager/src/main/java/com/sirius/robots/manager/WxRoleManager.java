package com.sirius.robots.manager;

import com.sirius.robots.comm.bo.WxUserRoleBO;
import com.sirius.robots.dal.mapper.WxRoleInfoMapper;
import com.sirius.robots.dal.mapper.WxUserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
@Component
public class WxRoleManager {

    @Autowired
    private WxRoleInfoMapper wxRoleInfoMapper;


    @Autowired
    private WxUserRoleMapper wxUserRoleMapper;


    public WxUserRoleBO queryUserRole(Integer userId){
        return wxUserRoleMapper.selectUserRole(userId);
    }


}