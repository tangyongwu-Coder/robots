package com.sirius.robots.handler.handler;

import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.handler.builder.TextBuilder;
import com.sirius.robots.manager.util.MsgUtil;
import com.sirius.robots.comm.enums.MsgTypeEnum;
import com.sirius.robots.service.MsgService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
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
            MsgTypeEnum msgType = MsgUtil.getMsgType(wxMessage);
            msg = msgService.processMsg(wxMessage, msgType);
        }catch (RobotsServiceException e){
            logger.error("处理消息异常,e",e);
            msg = e.getMessage();
        }catch (Exception e){
            logger.error("处理消息出错,e",e);
        }
        return new TextBuilder().build(msg, wxMessage, weixinService);

    }

}
