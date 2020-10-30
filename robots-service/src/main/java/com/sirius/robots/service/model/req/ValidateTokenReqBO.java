package com.sirius.robots.service.model.req;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liang_shi
 * @date on 2019/6/19 17:48
 * @description 校验token
 */
@Data
public class ValidateTokenReqBO implements Serializable {

    private static final long serialVersionUID = -3474019240755879566L;
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 系统类型
     */
    private String systemType;
    /**
     * token
     */
    private String token;


}
