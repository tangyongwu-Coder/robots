package com.sirius.robots.manager;

import com.sirius.robots.comm.constants.ServiceConstants;
import com.sirius.robots.comm.enums.DeleteFlagEnum;
import com.sirius.robots.comm.enums.FlagEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.StatusEnum;
import com.sirius.robots.dal.mapper.MsgAuthInfoMapper;
import com.sirius.robots.dal.model.MsgAuthInfo;
import com.sirius.robots.manager.model.WxUserBO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
@Component
public class MsgAuthInfoManager {


    @Autowired
    private MsgAuthInfoMapper msgAuthInfoMapper;
    public void addGroup(WxMpXmlMessage wxMessage, String res, String msgType, WxUserBO userBO){
        addOne(wxMessage,res,msgType,userBO,true);
    }
    public void add(WxMpXmlMessage wxMessage, String res, String msgType, WxUserBO userBO){
        addOne(wxMessage,res,msgType,userBO,false);
    }
    public void addOne(WxMpXmlMessage wxMessage, String res, String msgType, WxUserBO userBO,Boolean isGroup){
        MsgAuthInfo msgAuthInfo = new MsgAuthInfo();
        msgAuthInfo.setMsgId(wxMessage.getMsgId()+ StringUtils.EMPTY);
        msgAuthInfo.setMsgType(msgType);
        msgAuthInfo.setMsgReq(getStr(wxMessage.getContent()));
        msgAuthInfo.setMsgRes(getStr(res));
        msgAuthInfo.setMsgStatus(StatusEnum.NORMAL.getCode());
        msgAuthInfo.setUserId(userBO.getUserId());
        if(isGroup){
            msgAuthInfo.setIsGroup(FlagEnum.TRUE.getCodeStr());
        }else{
            msgAuthInfo.setIsGroup(FlagEnum.FALSE.getCodeStr());
        }
        msgAuthInfo.setIsOut(FlagEnum.FALSE.getCodeStr());
        msgAuthInfo.setIsRead(MsgTypeEnum.NULL.getCode().equals(msgType)? FlagEnum.FALSE.getCodeStr():FlagEnum.TRUE.getCodeStr());
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

    public List<MsgAuthInfo> getGroup(WxUserBO userBO){
        MsgAuthInfo query = new MsgAuthInfo();
        query.setUserId(userBO.getUserId());
        query.setIsGroup(FlagEnum.TRUE.getCodeStr());
        query.setIsRead(FlagEnum.TRUE.getCodeStr());
        query.setDeleteFlag(DeleteFlagEnum.NORMAL.getCode());
        return msgAuthInfoMapper.selectBySelective(query);
    }


    public void groupListOut(List<MsgAuthInfo> list,Integer notId){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (MsgAuthInfo msgAuthInfo : list) {
            if(msgAuthInfo.getId().equals(notId)){
                continue;
            }
            groupOut(msgAuthInfo);
        }
    }

    public void groupOut(MsgAuthInfo msgAuthInfo){
        msgAuthInfo.setIsOut(FlagEnum.TRUE.getCodeStr());
        msgAuthInfo.setUpdatedBy(ServiceConstants.SYSTEM_NAME);
        msgAuthInfoMapper.updateByPrimaryKeySelective(msgAuthInfo);
    }

}