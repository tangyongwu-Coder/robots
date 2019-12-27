package com.sirius.robots.comm.exception;

import com.sirius.robots.comm.enums.BaseErrorEnum;
import lombok.Getter;


/**
 * 自定义业务处理异常
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
public class RobotsServiceException extends RuntimeException {

    /** 异常代码 */
    @Getter
    private String code;
    /** 异常描述 */
    @Getter
    private String resMessage;

    public RobotsServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.resMessage = message;
    }

    public RobotsServiceException(BaseErrorEnum errorCodeEnum) {
        super(errorCodeEnum.getMsg());
        this.code = errorCodeEnum.getCode();
        this.resMessage = errorCodeEnum.getMsg();
    }
}
