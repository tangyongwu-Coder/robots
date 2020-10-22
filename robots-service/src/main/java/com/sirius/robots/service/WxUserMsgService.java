package com.sirius.robots.service;

import com.sirius.robots.comm.enums.FlagEnum;
import com.sirius.robots.dal.model.WxUserInfo;
import com.sirius.robots.manager.WxUserInfoManager;
import com.sirius.robots.service.msg.BaseMsgService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
@Service
public class WxUserMsgService {
    @Autowired
    protected WxUserInfoManager wxUserInfoManager;

    public void subscribe(WxMpXmlMessage wxMessage){
        String fromUser = wxMessage.getFromUser();
        WxUserInfo wxUser = wxUserInfoManager.getWxUser(fromUser);
        wxUser.setIsFollow(FlagEnum.TRUE.getCodeStr());
        wxUserInfoManager.getWxUser(fromUser);
    }

    public void unSubscribe(WxMpXmlMessage wxMessage){
        String fromUser = wxMessage.getFromUser();
        WxUserInfo wxUser = wxUserInfoManager.getWxUser(fromUser);
        wxUser.setIsFollow(FlagEnum.FALSE.getCodeStr());
        wxUserInfoManager.edit(wxUser);
    }
}