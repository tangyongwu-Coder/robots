package com.sirius.robots.service.msg;

import com.sirius.robots.comm.bo.WxUserRoleBO;
import com.sirius.robots.comm.enums.FlagEnum;
import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.RoleTypeEnum;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.dal.model.MsgAuthInfo;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.manager.MsgAuthInfoManager;
import com.sirius.robots.manager.WxRoleManager;
import com.sirius.robots.manager.WxUserInfoManager;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Service
public class MsgService{
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private MsgAuthInfoManager msgAuthInfoManager;
    @Autowired
    protected WxUserInfoManager wxUserInfoManager;

    @Autowired
    protected WxRoleManager wxRoleManager;

    public String processMsg(WxMpXmlMessage wxMessage){
        String fromUser = wxMessage.getFromUser();
        //获取用户信息
        WxUserBO userBO = getUserBO(fromUser);
        List<MsgAuthInfo> groups = msgAuthInfoManager.getGroup(userBO);
        MsgTypeEnum msgTypeEnum = MsgUtil.isGroup(wxMessage.getContent());
        if(CollectionUtils.isEmpty(groups)){
            //进分组
            return inGroup(wxMessage,userBO,msgTypeEnum);
        }
        List<MsgAuthInfo> notOutGroups = groups.stream()
                .filter(t -> FlagEnum.FALSE.getCodeStr().equals(t.getIsOut())).collect(Collectors.toList());

        //无分组
        if(CollectionUtils.isEmpty(notOutGroups)){
            //进分组
            return inGroup(wxMessage,userBO,msgTypeEnum);
        }
        MsgAuthInfo currGroup;
        if(notOutGroups.size()>1){
            //取最大ID=最近的一个分组
            currGroup = notOutGroups.stream()
                    .max((o1, o2) -> o1.getId().compareTo(o2.getId())).get();
            //更新其他分组为退出
            msgAuthInfoManager.groupListOut(notOutGroups,currGroup.getId());
        }else{
            currGroup = notOutGroups.get(0);
        }
        String currMsgType = currGroup.getMsgType();
        String content = wxMessage.getContent();
        //消息也是分组
        if(Objects.nonNull(msgTypeEnum)){
            //退出请求
            if(MsgTypeEnum.OUT.equals(msgTypeEnum)){
                msgAuthInfoManager.groupOut(currGroup);
                return HelpUtil.success(msgTypeEnum,userBO);
            }
            if(msgTypeEnum.getCode().equals(currMsgType)){
                return inGroupStr(msgTypeEnum,userBO);
            }
            return HelpUtil.getGroupRepeat(currGroup,msgTypeEnum);
        }

        //进入这里表示已经成功进入currGroup分组中
        log.debug("消息{}已经进入{}分组",content,currMsgType);
        MsgTypeEnum currMsgTypeEnum = MsgTypeEnum.explain(currMsgType);
        String service = currMsgTypeEnum.getService();
        BaseMsgService bean = beanFactory.getBean(service, BaseMsgService.class);
        return bean.process(wxMessage,userBO);
    }


    private String inGroup(WxMpXmlMessage wxMessage,WxUserBO userBO,MsgTypeEnum msgTypeEnum){
        //消息是分组
        if(Objects.nonNull(msgTypeEnum)){
            //退出请求
            if(MsgTypeEnum.OUT.equals(msgTypeEnum)){
                return HelpUtil.noGroupOut(userBO);
            }
            //进入分组
            return initGroup(wxMessage,userBO,msgTypeEnum);
        }
        //未进入分组,提示进入分组
        return HelpUtil.getNull(userBO);
    }
    /**
     * 初始化分组
     *
     * @param wxMessage     消息对象
     * @param userBO        用户信息
     * @param msgTypeEnum   分组
     * @return              返回
     */
    private String initGroup(WxMpXmlMessage wxMessage,WxUserBO userBO,MsgTypeEnum msgTypeEnum){
        String res = inGroupStr(msgTypeEnum,userBO);
        msgAuthInfoManager.addGroup(wxMessage,res,msgTypeEnum.getCode(),userBO);
        return res;
    }
    /**
     * 进入分组
     *
     * @param userBO        用户信息
     * @param msgTypeEnum   分组
     * @return              返回
     */
    private String inGroupStr(MsgTypeEnum msgTypeEnum,WxUserBO userBO){
        return HelpUtil.getGroup(msgTypeEnum,userBO);
    }


    /**
     * 获取用户信息
     *
     * @param fromUser 用户code
     * @return         用户信息
     */
    private WxUserBO getUserBO(String fromUser){
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
        wxUserBO.setFamilyId(wxUser.getFamilyId());
        wxUserBO.setUserCode(wxUser.getUserCode());
        String userName = wxUser.getUserName();
        wxUserBO.setUserName(userName);
        wxUserBO.setUserType(wxUser.getUserType());
        wxUserBO.setMobile(wxUser.getMobile());
        wxUserBO.setEmail(wxUser.getEmail());
        wxUserBO.setIsFollow(wxUser.getIsFollow());
        wxUserBO.setRoleId(wxUserRoleBO.getRoleId());
        wxUserBO.setRoleName(wxUserRoleBO.getRoleName());
        String roleType = wxUserRoleBO.getRoleType();
        wxUserBO.setRoleType(roleType);
        wxUserBO.setRoleCategory(wxUserRoleBO.getRoleCategory());
        wxUserBO.setRolePowerType(wxUserRoleBO.getRolePowerType());
        wxUserBO.setIsManager(RoleTypeEnum.SUPER.getCode().equals(roleType) || RoleTypeEnum.MANAGER.getCode().equals(roleType));
        wxUserBO.setIsManager(RoleTypeEnum.SUPER.getCode().equals(roleType));
        wxUserBO.setIsAuth(Objects.nonNull(userName));
        wxUserBO.setIsFamily(Objects.nonNull(wxUser.getFamilyId()));
        log.debug("wxUserBO:{}",wxUserBO);
        return wxUserBO;
    }

}