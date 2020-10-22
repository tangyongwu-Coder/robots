package com.sirius.robots.comm.enums;

import com.sirius.robots.comm.constants.ServiceConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Getter
@AllArgsConstructor
public enum MsgErrorEnum implements BaseErrorEnum{
    /**
     * 用户信息不存在
     */
    USER_NO_EXIST("USER_NO_EXIST", "用户信息不存在,请联系管理员。"),

    /**
     * 用户角色不存在
     */
    USER_ROLE_NO_EXIST("USER_ROLE_NO_EXIST", "用户角色不存在,请联系管理员。"),


    /**
     * 用户未授权
     */
    USER_NOT_AUTH("USER_NOT_AUTH", "该操作需要授权,请授权后再操作。"),

    /**
     * 用户未设置家庭
     */
    USER_NOT_FAMILY("USER_NOT_FAMILY", "该功能需要设置家庭,请设置后再操作。"),

    /**
     * 用户无权限
     */
    USER_NOT_PERMISSION("USER_NOT_PERMISSION", "该信息不存在或者您无权操作该信息,如有疑问请联系管理员。"),


    /**
     * 查询为空
     */
    RESULT_NULL("RESULT_NULL", "很抱歉,未能查询到信息。"),


    /**
     * 删除为空
     */
    DELETE_NULL("DELETE_NULL", "很抱歉,没有什么可删除的了。"),

    /**
     * 内容未知
     */
    UN_KNOWN("UN_KNOWN", ServiceConstants.MSG_UNKNOWN),

    /**
     * 数据格式有误
     */
    DATA_FORMAT_ERROR("DATA_FORMAT_ERROR", "数据格式有误"),


    ;




    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String msg;

}