package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.msg.HelpMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
import com.sirius.robots.manager.util.HelpUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.stereotype.Service;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Slf4j
@Service
public class HelpMsgService extends BaseMsgService<Object> {
    /**
     * 消息类型
     *
     * @return 消息类型
     */
    @Override
    protected MsgTypeEnum getMsgType() {
        return MsgTypeEnum.HELP;
    }

    /**
     * 基础消息对象
     *
     * @param userBO
     * @param wxMessage
     * @return 基础消息对象
     */
    @Override
    protected BaseMsgBO<Object> processData(WxUserBO userBO, WxMpXmlMessage wxMessage) {
        BaseMsgBO<Object> baseMsgBO = new BaseMsgBO<>();
        baseMsgBO.setBaseMsgTypeEnum(HelpMsgTypeEnum.HELP);
        baseMsgBO.setT(new Object());
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
        return HelpUtil.getHelp(userBO);
    }
}