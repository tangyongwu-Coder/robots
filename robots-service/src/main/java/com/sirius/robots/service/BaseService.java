package com.sirius.robots.service;

import com.sirius.robots.comm.bo.WxUserRoleBO;
import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.RoleTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.manager.WxRoleManager;
import com.sirius.robots.manager.WxUserInfoManager;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
@Service
public class BaseService {

    @Autowired
    protected WxUserInfoManager wxUserInfoManager;

    @Autowired
    protected WxRoleManager wxRoleManager;

    /**
     * 获取用户信息
     *
     * @param fromUser 用户code
     * @return         用户信息
     */
    protected WxUserBO getUserBO(String fromUser){
        WxUserInfo wxUser = wxUserInfoManager.getWxUser(fromUser);
        if(Objects.isNull(wxUser)){
            log.error("用户信息不存在");
            throw new RobotsServiceException(MsgErrorEnum.USER_NO_EXIST);
        }

        Integer userId = wxUser.getId();
        WxUserRoleBO wxUserRoleBO = wxRoleManager.queryUserRole(userId);
        if(Objects.isNull(wxUserRoleBO)){
            log.error("用户角色不存在");
            throw new RobotsServiceException(MsgErrorEnum.USER_ROLE_NO_EXIST);
        }
        WxUserBO wxUserBO = new WxUserBO();
        wxUserBO.setUserId(userId);
        wxUserBO.setUserCode(wxUser.getUserCode());
        String userName = wxUser.getUserName();
        wxUserBO.setUserName(userName);
        wxUserBO.setUserType(wxUser.getUserType());
        wxUserBO.setMobile(wxUser.getMobile());
        wxUserBO.setEmail(wxUser.getEmail());
        wxUserBO.setRoleId(wxUserRoleBO.getRoleId());
        wxUserBO.setRoleName(wxUserRoleBO.getRoleName());
        String roleType = wxUserRoleBO.getRoleType();
        wxUserBO.setRoleType(roleType);
        wxUserBO.setRoleCategory(wxUserRoleBO.getRoleCategory());
        wxUserBO.setRolePowerType(wxUserRoleBO.getRolePowerType());
        wxUserBO.setIsManager(RoleTypeEnum.SUPER.getCode().equals(roleType) || RoleTypeEnum.MANAGER.getCode().equals(roleType));
        wxUserBO.setIsManager(RoleTypeEnum.SUPER.getCode().equals(roleType));
        wxUserBO.setIsAuth(Objects.nonNull(userName));
        log.debug("wxUserBO:{}",wxUserBO);
        return wxUserBO;
    }
}