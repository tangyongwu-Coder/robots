package com.sirius.robots.comm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/12/25
 */
@Getter
@AllArgsConstructor
public enum ErrorCodeEnum implements BaseErrorEnum {

    /** 系统异常 */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常"),

    /** 请求参数异常 */
    PARAMS_ERROR("PARAMS_ERROR", "请求参数异常"),

    /**  外部服务调用异常  */
    FEIGN_ERROR("FEIGN_ERROR", "外部服务调用异常"),

    /** 外部服务调用超时 */
    OUT_SERVICE_TIME_OUT("OUT_SERVICE_TIME_OUT", "外部服务调用超时"),

    /**不支持的请求方式 */
    UNSUPPORTED_REQUEST("UNSUPPORTED_REQUEST", "不支持的请求方式"),

    /** 数据重复 */
    DATA_REPEAT("DATA_REPEAT", "数据重复"),

    /**  数据库更新条数错误 */
    DATA_UPDATE_ROW_ERROR("DATA_UPDATE_ROW_ERROR", "数据库更新条数错误"),

    /**  数据库更新条数错误 */
    OUT_DATA_ERROR("OUT_DATA_ERROR", "外部接口数据异常"),

    /**  外部接口异常 */
    OUT_ERROR("OUT_ERROR", "外部接口异常"),

    /**  数据异常 */
    DATA_ERROR("DATA_ERROR", "数据异常"),

    /**  数据更新异常 */
    DATA_UPDATE_ERROR("DATA_UPDATE_ERROR", "数据更新异常"),

    /**  数据已存在 */
    DATA_EXITS("DATA_EXITS", "数据已存在"),

    /**  数据不存在 */
    DATA_NOT_EXITS("DATA_NOT_EXITS", "数据不存在"),

    /**  文件上传异常 */
    FILE_TO_LARGE("FILE_TO_LARGE", "上传文件过大,请重新选择"),

    /**  发送消息异常 */
    SEND_MSG_ERROR("SEND_MSG_ERROR", "发送消息异常"),

    /**  权限异常 AUTH 开头 */
    AUTH0001("AUTH0001","您没有登录或登录已过期,请重新登录"),

    MSG_OUT_1000("S1000", "用户名重复,请重新输入"),

    MSG_OUT_1001("S1001", "两次输入密码不一致,请确认后重新输入"),

    MSG_OUT_1002("S1002", "用户名或者密码不正确,请重新输入"),

    MSG_OUT_1003("S1003", "不支持的验证码类型"),

    MSG_OUT_1004("S1004", "邮件服务异常,请稍后再试!"),

    MSG_OUT_1005("S1005", "验证码已过期,请重新发送!"),

    MSG_OUT_1006("S1006", "验证码错误,请重新输入!"),
    ;

    /**
     * 错误码
     */
    private String code;
    /**
     * 错误描述
     */
    private String msg;
}
