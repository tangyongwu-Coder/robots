package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.msg.UserAuthMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.WoolMsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.dal.model.WoolInfo;
import com.sirius.robots.manager.MsgAuthInfoManager;
import com.sirius.robots.manager.WoolInfoManager;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import com.sirius.robots.manager.util.WoolUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;


/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/15
 */
@Slf4j
@Service
public class WoolMsgService extends BaseMsgService<WoolInfo> {
    @Autowired
    private WoolInfoManager woolInfoManager;

    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected MsgTypeEnum getMsgType() {
        return MsgTypeEnum.WOOL;
    }

    /**
     * 消息类型
     *
     * @param wxMessage
     * @return 消息类型
     */
    @Override
    protected BaseMsgBO<WoolInfo> processData(WxUserBO userBO, WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();
        MsgTypeEnum msgType = getMsgType();
        BaseMsgTypeEnum base = MsgUtil.getBase(content,msgType);
        WoolInfo woolInfo = null;
        if(Objects.isNull(base)||WoolMsgTypeEnum.WOOL_ADD.equals(base)){
            //尝试解析
            woolInfo = WoolUtil.getWool(content);
            if(Objects.isNull(woolInfo.getNextTime())){
                String unknown = HelpUtil.getUnknown(msgType, userBO);
                throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
            }
            base = WoolMsgTypeEnum.WOOL_ADD;
        }
        BaseMsgBO<WoolInfo> baseMsgBO = new BaseMsgBO<>();
        baseMsgBO.setBaseMsgTypeEnum(base);
        baseMsgBO.setT(woolInfo);
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
                               BaseMsgBO<WoolInfo> baseMsgBO) {
        if(userBO.getIsManager()){
            return Boolean.TRUE;
        }
        Boolean isAuth = userBO.getIsAuth();
        Boolean isManager = userBO.getIsManager();
        WoolMsgTypeEnum msgType =(WoolMsgTypeEnum) baseMsgBO.getBaseMsgTypeEnum();
        switch (msgType){
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
            case WOOL_DISABLE:
                if(!isManager){
                    throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
                }
                return Boolean.TRUE;
            case WOOL_ENABLE:
                if(!isManager){
                    throw new RobotsServiceException(MsgErrorEnum.USER_NOT_PERMISSION);
                }
                return Boolean.TRUE;
            case WOOL_ADD:
                return Boolean.TRUE;
            default:
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
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
                             BaseMsgBO<WoolInfo> baseMsgBO) {
        String content = wxMessage.getContent();
        String res = null;
        WoolMsgTypeEnum msgType =(WoolMsgTypeEnum) baseMsgBO.getBaseMsgTypeEnum();
        switch (msgType){
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
                    res = HelpUtil.getUnknown(getMsgType(),userBO);
                }
                break;
            case WOOL_ENABLE:
                if(userBO.getIsManager()){
                    res = woolInfoManager.enableWool(content,msgType,userBO);
                }else{
                    res = HelpUtil.getUnknown(getMsgType(),userBO);
                }
                break;
            case WOOL_ADD:
                res = woolInfoManager.addWool(content,msgType,userBO);
                break;
            default:
                res = HelpUtil.getUnknown(getMsgType(),userBO);
                break;
        }

        return res;
    }
}