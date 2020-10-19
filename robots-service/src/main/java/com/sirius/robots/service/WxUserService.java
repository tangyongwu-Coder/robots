package com.sirius.robots.service;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Slf4j
public class WxUserService extends BaseService{


    public void subscribe(WxMpXmlMessage wxMessage){
        String fromUser = wxMessage.getFromUser();
        subscribe(fromUser);
    }

    public void unSubscribe(WxMpXmlMessage wxMessage){
        String fromUser = wxMessage.getFromUser();
        unSubscribe(fromUser);
    }
}