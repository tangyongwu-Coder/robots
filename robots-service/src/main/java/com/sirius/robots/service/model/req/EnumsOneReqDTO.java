package com.sirius.robots.service.model.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 枚举请求对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Getter
@Setter
@ToString
public class EnumsOneReqDTO implements Serializable {

    private static final long serialVersionUID = 378140911486879071L;
    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 枚举类型
     */
    private String enumType;

    /**
     * 枚举KEY
     */
    private String enumKey;
    /**
     * 枚举值
     */
    private String enumValue;
    /**
     * 枚举描述
     */
    private String enumDesc;

    /**
     * NORMAL 正常 DISABLE 禁用
     */
    private String enumStatus;

}