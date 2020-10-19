package com.sirius.robots.manager;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.FlagEnum;
import com.sirius.robots.comm.enums.MsgTypeEnum;
import com.sirius.robots.comm.enums.StatusEnum;
import com.sirius.robots.dal.mapper.MsgAuthInfoMapper;
import com.sirius.robots.dal.model.MsgAuthInfo;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
@Component
public class MsgAuthInfoManager {


    @Autowired
    private MsgAuthInfoMapper msgAuthInfoMapper;


    public void add(WxMpXmlMessage wxMessage, String res, MsgTypeEnum msgType, WxUserBO userBO){
        MsgAuthInfo msgAuthInfo = new MsgAuthInfo();
        msgAuthInfo.setMsgId(wxMessage.getMsgId()+ StringUtils.EMPTY);
        msgAuthInfo.setMsgType(msgType.getCode());
        msgAuthInfo.setMsgReq(getStr(wxMessage.getContent()));
        msgAuthInfo.setMsgRes(getStr(res));
        msgAuthInfo.setMsgStatus(StatusEnum.NORMAL.getCode());
        msgAuthInfo.setUserId(userBO.getUserId());
        msgAuthInfo.setIsRead(MsgTypeEnum.NULL.equals(msgType)? FlagEnum.FALSE.getCodeStr():FlagEnum.TRUE.getCodeStr());
        msgAuthInfo.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        msgAuthInfo.setCreatedBy(ServiceConstants.SYSTEM_NAME);
        msgAuthInfoMapper.insert(msgAuthInfo);
    }

    private String getStr(String str){
        if(StringUtils.isEmpty(str)){
            return str;
        }
        if(str.length() <128){
            return str;
        }
        return str.substring(0,128);
    }

    public MsgAuthInfo getBefore(){
        return null;
    }
}