package com.sirius.robots.manager.model;

import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import com.sirius.robots.comm.enums.msg.MsgTypeEnum;
import lombok.Data;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/20
 */
@Data
public class MsgTypeBO {
    /**
     * 是否分组
     */
    private Boolean isGroup;
    /**
     * 组类型
     */
    private MsgTypeEnum msgType;
    /**
     * 基础类型
     */
    private BaseMsgTypeEnum BaseMsgTypeEnum;
}