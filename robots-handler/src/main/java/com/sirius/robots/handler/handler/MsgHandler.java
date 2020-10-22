package com.sirius.robots.handler.handler;

import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.handler.builder.NewsBuilder;
import com.sirius.robots.handler.builder.TextBuilder;
import com.sirius.robots.manager.util.MsgUtil;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.service.msg.MsgService;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    private MsgService msgService;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {
        String msg = null;
        try{
            msg = msgService.processMsg(wxMessage);
        }catch (RobotsServiceException e){
            logger.error("处理消息异常,e",e);
            msg = e.getMessage();
        }catch (Exception e){
            msg = "服务异常,请稍后再试！";
            logger.error("处理消息出错,e",e);
        }
        msg = "<a data-miniprogram-appid=\"wx5ba8812bdfc7741f\" data-miniprogram-path=\"amouse_wxapp_card/pages/card/home/home\" href=\"http://www.qq.com\">5万运营都在用的 运营名片小程序</a>";
        return new TextBuilder().build(msg, wxMessage, weixinService);

    }

}
