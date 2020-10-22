package com.sirius.robots.service.msg;

import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import com.sirius.robots.manager.MsgAuthInfoManager;
import com.sirius.robots.manager.WxRoleManager;
import com.sirius.robots.manager.WxUserInfoManager;
import com.sirius.robots.manager.model.BaseMsgBO;
import com.sirius.robots.manager.model.WxUserBO;
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
public abstract class BaseMsgService<T> {
    @Autowired
    private MsgAuthInfoManager msgAuthInfoManager;

    @Autowired
    protected WxUserInfoManager wxUserInfoManager;

    @Autowired
    protected WxRoleManager wxRoleManager;

    /**
     * 消息类型
     *
     * @return  消息类型
     */
    protected abstract MsgTypeEnum getMsgType();

    /**
     * 消息类型
     *
     * @return  基础消息对象
     */
    protected abstract BaseMsgBO<T> processData(WxUserBO userBO, WxMpXmlMessage wxMessage);

    /**
     * 验证
     *
     * @param userBO        用户信息
     * @param wxMessage     请求信息
     * @param baseMsgBO     基础消息对象
     */
    protected abstract Boolean validate(WxUserBO userBO,
                                        WxMpXmlMessage wxMessage,
                                        BaseMsgBO<T> baseMsgBO);
    /**
     * 处理
     *
     * @param userBO        用户信息
     * @param wxMessage     请求信息
     * @param baseMsgBO     基础消息对象
     */
    protected abstract String process(WxUserBO userBO,
                                      WxMpXmlMessage wxMessage,
                                      BaseMsgBO<T> baseMsgBO);

    /**
     * 通用验证
     *
     * @param wxMessage     请求信息
     * @param userBO        用户信息
     * @param baseMsgBO     基础消息对象
     */
    private void baseValidate(WxMpXmlMessage wxMessage,WxUserBO userBO, BaseMsgBO<T> baseMsgBO){
        validate(userBO,wxMessage,baseMsgBO);
    }
    /**
     * 通用处理
     *
     * @param wxMessage     请求信息
     * @param userBO        用户信息
     */
    public String process(WxMpXmlMessage wxMessage,WxUserBO userBO){
        BaseMsgBO<T> baseMsgBO = processData(userBO,wxMessage);
        baseValidate(wxMessage,userBO,baseMsgBO);
        String res = process(userBO,wxMessage,baseMsgBO);
        BaseMsgTypeEnum baseMsgType = baseMsgBO.getBaseMsgTypeEnum();
        msgAuthInfoManager.add(wxMessage,res,baseMsgType.getCode(),userBO);
        return "["+getMsgType().getMsg()+"]"+res;
    }


}