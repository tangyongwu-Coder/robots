package com.sirius.robots.manager;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.*;
import com.sirius.robots.dal.mapper.WxUserInfoMapper;
import com.sirius.robots.dal.mapper.WxUserRoleMapper;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.dal.model.WxUserRole;
import com.sirius.robots.manager.util.WoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Component
public class WxUserInfoManager {

    @Autowired
    private WxUserInfoMapper wxUserInfoMapper;

    @Autowired
    private WxUserRoleMapper wxUserRoleMapper;


    public String auth(String fromUser,String msg, MsgTypeEnum msgType){
        String userName = WoolUtil.getWoolMsg(msg, msgType);
        WxUserInfo query = new WxUserInfo();
        query.setUserCode(fromUser);
        List<WxUserInfo> wxUserInfos = wxUserInfoMapper.selectBySelective(query);
        if(CollectionUtils.isEmpty(wxUserInfos)){
            add(fromUser,userName);
        }else{
            WxUserInfo wxUserInfo = wxUserInfos.get(0);
            if(DeleteFlagEnum.DELETE.getCode().equals(wxUserInfo.getDeleteFlag())
                    ||StatusEnum.DISABLE.getCode().equals(wxUserInfo.getUserStatus())){
                wxUserInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
                wxUserInfo.setUserStatus(StatusEnum.NORMAL.getCode());
            }
            wxUserInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            wxUserInfo.setUserName(userName);
            wxUserInfoMapper.updateByPrimaryKeySelective(wxUserInfo);
        }
        return "欢迎你,"+userName;
    }
    public WxUserInfo getWxUser(String fromUser){
        WxUserInfo query = new WxUserInfo();
        query.setUserCode(fromUser);
        List<WxUserInfo> wxUserInfos = wxUserInfoMapper.selectBySelective(query);
        if(CollectionUtils.isEmpty(wxUserInfos)){
            return add(fromUser,null);
        }
        WxUserInfo wxUserInfo = wxUserInfos.get(0);
        if(DeleteFlagEnum.DELETE.getCode().equals(wxUserInfo.getDeleteFlag())
                ||StatusEnum.DISABLE.getCode().equals(wxUserInfo.getUserStatus())){
            wxUserInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
            wxUserInfo.setUserStatus(StatusEnum.NORMAL.getCode());
            wxUserInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            wxUserInfoMapper.updateByPrimaryKeySelective(wxUserInfo);
        }
        return wxUserInfo;
    }

    public void edit(WxUserInfo wxUserInfo){
        wxUserInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
        wxUserInfoMapper.updateByPrimaryKeySelective(wxUserInfo);
    }


    private WxUserInfo add(String fromUser,String userName){
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setUserCode(fromUser);
        wxUserInfo.setUserName(userName);
        wxUserInfo.setUserType(UserTypeEnum.USER.getCode());
        wxUserInfo.setIsFollow(FlagEnum.TRUE.getCodeStr());
        wxUserInfo.setUserStatus(StatusEnum.NORMAL.getCode());
        wxUserInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        wxUserInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
        wxUserInfoMapper.insert(wxUserInfo);
        WxUserRole wxUserRole = new WxUserRole();
        wxUserRole.setUserId(wxUserInfo.getId());
        //普通角色
        wxUserRole.setRoleId(1);
        wxUserRole.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        wxUserRole.setCreatedBy(ServiceConstants.SYSTEM_NAME);
        wxUserRoleMapper.insert(wxUserRole);
        return wxUserInfo;
    }

}