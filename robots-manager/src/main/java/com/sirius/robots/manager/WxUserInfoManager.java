package com.sirius.robots.manager;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.*;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.dal.mapper.FamilyInfoMapper;
import com.sirius.robots.dal.mapper.WxUserInfoMapper;
import com.sirius.robots.dal.mapper.WxUserRoleMapper;
import com.sirius.robots.dal.model.FamilyInfo;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.dal.model.WxUserRole;
import com.sirius.robots.manager.util.WoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

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

    @Autowired
    private FamilyInfoMapper familyInfoMapper;


    public void auth(String fromUser,String msg,BiConsumer<WxUserInfo,String> consumer){
        WxUserInfo query = new WxUserInfo();
        query.setUserCode(fromUser);
        List<WxUserInfo> wxUserInfos = wxUserInfoMapper.selectBySelective(query);
        if(CollectionUtils.isEmpty(wxUserInfos)){
            add(fromUser,msg, consumer);
        }else{
            WxUserInfo wxUserInfo = wxUserInfos.get(0);
            if(DeleteFlagEnum.DELETE.getCode().equals(wxUserInfo.getDeleteFlag())
                    ||StatusEnum.DISABLE.getCode().equals(wxUserInfo.getUserStatus())){
                wxUserInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
                wxUserInfo.setUserStatus(StatusEnum.NORMAL.getCode());
            }
            wxUserInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
            consumer.accept(wxUserInfo,msg);
            wxUserInfoMapper.updateByPrimaryKeySelective(wxUserInfo);
        }
    }

    public FamilyInfo getFamily(String name){
        FamilyInfo familyInfo = familyInfoMapper.selectByName(name);
        if(Objects.nonNull(familyInfo)){
            if(DeleteFlagEnum.DELETE.getCode().equals(familyInfo.getDeleteFlag())
                    ||StatusEnum.DISABLE.getCode().equals(familyInfo.getStatus())){
                familyInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
                familyInfo.setStatus(StatusEnum.NORMAL.getCode());
                familyInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
                familyInfoMapper.updateByPrimaryKeySelective(familyInfo);
            }
        }else{
            familyInfo = new FamilyInfo();
            familyInfo.setName(name);
            familyInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
            familyInfo.setStatus(StatusEnum.NORMAL.getCode());
            familyInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
            familyInfoMapper.insert(familyInfo);
        }
        return familyInfo;
    }


    public WxUserInfo getWxUser(String fromUser){
        WxUserInfo query = new WxUserInfo();
        query.setUserCode(fromUser);
        List<WxUserInfo> wxUserInfos = wxUserInfoMapper.selectBySelective(query);
        if(CollectionUtils.isEmpty(wxUserInfos)){
            return add(fromUser,null,null);
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


    private WxUserInfo add(String fromUser,
                           String msg,
                           BiConsumer<WxUserInfo,String> consumer){
        WxUserInfo wxUserInfo = new WxUserInfo();
        wxUserInfo.setUserCode(fromUser);
        wxUserInfo.setUserType(UserTypeEnum.USER.getCode());
        wxUserInfo.setIsFollow(FlagEnum.TRUE.getCodeStr());
        wxUserInfo.setUserStatus(StatusEnum.NORMAL.getCode());
        wxUserInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        wxUserInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
        if(Objects.nonNull(consumer)){
            consumer.accept(wxUserInfo,msg);
        }
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