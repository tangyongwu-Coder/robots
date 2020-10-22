package com.sirius.robots.handler.builder;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/22
 */
@Slf4j
public class NewsBuilder  extends AbstractBuilder {

    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage,
                                   WxMpService service) {
        WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
        item.setDescription(content);
        item.setPicUrl("http://mmbiz.qpic.cn/mmbiz_jpg/rS8yxabSsDUpsyztVtHbK08icmTLsCHGoaJOGMw0PjBDic8CfRursmVZ3IklGib4shdfFNHvl7TV7Ell9DY4Crf5w/0");
        item.setTitle(content);
        item.setUrl("https://loan.xinyan-ai.com");
        WxMpXmlOutNewsMessage m = WxMpXmlOutMessage.NEWS()
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .addArticle(item)
                .build();
        return m;
    }
}