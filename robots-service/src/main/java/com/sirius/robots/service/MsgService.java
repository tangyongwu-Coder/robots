package com.sirius.robots.service;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.manager.MsgAuthInfoManager;
import com.sirius.robots.manager.WoolInfoManager;
import com.sirius.robots.comm.enums.MsgTypeEnum;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Service
public class MsgService extends BaseService{
    @Autowired
    private WoolInfoManager woolInfoManager;

    @Autowired
    private MsgAuthInfoManager msgAuthInfoManager;


    public String processMsg(WxMpXmlMessage wxMessage,MsgTypeEnum msgType){

        String content = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        //获取用户信息
        WxUserBO userBO = getUserBO(fromUser);
        validate(msgType,userBO);
        String res = null;
        switch (msgType){
            case USER_AUTH:
                res = wxUserInfoManager.auth(fromUser,content,msgType);
                break;
            case WOOL_ADD:
                res = woolInfoManager.addWool(content,msgType,userBO);
                break;
            case WOOL_EDIT:
                res = woolInfoManager.editWool(content,msgType,userBO);
                break;
            case WOOL_DELETE:
                res = woolInfoManager.deleteWool(content,msgType,userBO);
                break;
            case WOOL_QUERY:
                res = woolInfoManager.queryWool(content,msgType,userBO);
                break;
            case WOOL_TODAY:
                res = woolInfoManager.queryTodayWool(content,msgType,userBO);
                break;
            case WOOL_DISABLE:
                if(userBO.getIsManager()){
                    res = woolInfoManager.disableWool(content,msgType,userBO);
                }else{
                    res = HelpUtil.getHelp2(userBO);
                }
                break;
            case WOOL_ENABLE:
                if(userBO.getIsManager()){
                    res = woolInfoManager.enableWool(content,msgType,userBO);
                }else{
                    res = HelpUtil.getHelp2(userBO);
                }
                break;
            case HELP:
                res = HelpUtil.getHelp1(userBO);
                break;
            case NULL:
                res = HelpUtil.getHelp2(userBO);
                break;
            default:
                res = HelpUtil.getHelp2(userBO);
                break;
        }
        msgAuthInfoManager.add(wxMessage,res,msgType,userBO);
        return res;
    }

    public Boolean validate(MsgTypeEnum msgType,WxUserBO userBO){
        if(userBO.getIsManager()){
            return Boolean.TRUE;
        }
        Boolean isAuth = userBO.getIsAuth();
        switch (msgType){
            case USER_AUTH:
                return Boolean.TRUE;
            case WOOL_ADD:
                return Boolean.TRUE;
            case WOOL_EDIT:
                return Boolean.TRUE;
            case WOOL_DELETE:
                break;
            case WOOL_QUERY:
                if(!isAuth){
                    throw new RobotsServiceException(MsgErrorEnum.USER_NOT_AUTH);
                }
                return Boolean.TRUE;
            case WOOL_TODAY:
                if(!isAuth){
                    throw new RobotsServiceException(MsgErrorEnum.USER_NOT_AUTH);
                }
                return Boolean.TRUE;
            case HELP:
                return Boolean.TRUE;
            case NULL:
                return Boolean.TRUE;
            default:
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}