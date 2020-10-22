package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.MsgErrorEnum;
import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.comm.enums.msg.TicketMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.WoolMsgTypeEnum;
import com.sirius.robots.comm.exception.RobotsServiceException;
import com.sirius.robots.dal.model.WoolInfo;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import com.sirius.robots.manager.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Slf4j
@Service
public class TicketMsgService extends BaseMsgService<Object> {
    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected MsgTypeEnum getMsgType() {
        return MsgTypeEnum.TICKET;
    }

    /**
     * 消息类型
     *
     * @param userBO
     * @param wxMessage
     * @return 消息类型
     */
    @Override
    protected BaseMsgBO<Object> processData(WxUserBO userBO, WxMpXmlMessage wxMessage) {
        String content = wxMessage.getContent();
        MsgTypeEnum msgType = getMsgType();
        BaseMsgTypeEnum base = MsgUtil.getBase(content,msgType);
        if(Objects.isNull(base)|| WoolMsgTypeEnum.WOOL_ADD.equals(base)){
            //尝试解析
           // WoolInfo woolInfo = WoolUtil.getWool(content);
           /* if(Objects.isNull(woolInfo.getNextTime())){
                String unknown = HelpUtil.getUnknown(msgType, userBO);
                throw new RobotsServiceException(MsgErrorEnum.UN_KNOWN.getCode(),unknown);
            }*/

            base = TicketMsgTypeEnum.TICKET_ADD;
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
    protected Boolean validate(WxUserBO userBO, WxMpXmlMessage wxMessage, BaseMsgBO<Object> baseMsgBO) {
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
    protected String process(WxUserBO userBO, WxMpXmlMessage wxMessage, BaseMsgBO<Object> baseMsgBO) {
        return null;
    }
}