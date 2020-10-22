package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.msg.UserAuthMsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.comm.util.ValidatePattenUtil;
import com.sirius.robots.dal.model.FamilyInfo;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Service
public class UserAuthMsgService extends BaseMsgService<Object> {

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected MsgTypeEnum getMsgType() {
        return MsgTypeEnum.USER_AUTH;
    }

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected BaseMsgBO<Object> processData(WxUserBO userBO, WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();
        MsgTypeEnum msgType = getMsgType();
        BaseMsgTypeEnum base = MsgUtil.getBase(content,msgType);
        if(Objects.isNull(base)){
            String unknown = HelpUtil.getUnknown(msgType, userBO);
            throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
        }
        BaseMsgBO<Object> baseMsgBO = new BaseMsgBO<>();
        baseMsgBO.setBaseMsgTypeEnum(base);
        baseMsgBO.setT(null);
        return baseMsgBO;
    }

    /**
     * 验证
     *
     * @param userBO    用户信息
     * @param wxMessage 请求信息
       * @param baseMsgBO     基础消息对象
     */
    @Override
    protected Boolean validate(WxUserBO userBO,
                               WxMpXmlMessage wxMessage,
                               BaseMsgBO<Object> baseMsgBO) {
        return Boolean.TRUE;
    }

    /**
     * 处理
     *
     * @param userBO    用户信息
     * @param wxMessage 请求信息
       * @param baseMsgBO     基础消息对象
     */
    @Override
    protected String process(WxUserBO userBO,
                             WxMpXmlMessage wxMessage,
                             BaseMsgBO<Object> baseMsgBO) {
        String content = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        UserAuthMsgTypeEnum msgType =(UserAuthMsgTypeEnum) baseMsgBO.getBaseMsgTypeEnum();
        String msg = MsgUtil.getMsg(content, msgType);
        switch (msgType) {
            case NAME:
                if(!ValidatePattenUtil.validate(ValidatePattenUtil.NAME_REGEX, msg)){
                    throw new RobotsServiceException(MsgErrorEnum.DATA_FORMAT_ERROR);
                }
                wxUserInfoManager.auth(fromUser,msg,this::setName);
                break;
            case PHONE:
                if(!ValidatePattenUtil.validate(ValidatePattenUtil.MOBILE_REGEX, msg)){
                    throw new RobotsServiceException(MsgErrorEnum.DATA_FORMAT_ERROR);
                }
                wxUserInfoManager.auth(fromUser,msg,this::setMobile);
                break;
            case EMAIL:
                if(!ValidatePattenUtil.validate(ValidatePattenUtil.EMAIL_REGEX, msg)){
                    throw new RobotsServiceException(MsgErrorEnum.DATA_FORMAT_ERROR);
                }
                wxUserInfoManager.auth(fromUser,msg,this::setEmail);
                break;
            case FAMILY:
                FamilyInfo family = wxUserInfoManager.getFamily(msg);
                wxUserInfoManager.auth(fromUser,family.getId()+ StringUtils.EMPTY,this::setFamilyId);
                break;
        }
        return "欢迎你,"+msg;
    }

    private void setName(WxUserInfo wxUserInfo,String msg){
        wxUserInfo.setUserName(msg);
    }
    private void setMobile(WxUserInfo wxUserInfo,String msg){
        wxUserInfo.setMobile(msg);
    }
    private void setEmail(WxUserInfo wxUserInfo,String msg){
        wxUserInfo.setEmail(msg);
    }
    private void setFamilyId(WxUserInfo wxUserInfo,String msg){
        wxUserInfo.setFamilyId(Integer.parseInt(msg));
    }

}