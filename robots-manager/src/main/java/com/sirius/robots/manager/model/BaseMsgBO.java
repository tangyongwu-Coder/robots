package com.sirius.robots.manager.model;

import com.sirius.robots.comm.enums.msg.BaseMsgTypeEnum;
import lombok.Data;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/21
 */
@Data
public class BaseMsgBO<T> {

    private BaseMsgTypeEnum baseMsgTypeEnum;

    private T t;
}