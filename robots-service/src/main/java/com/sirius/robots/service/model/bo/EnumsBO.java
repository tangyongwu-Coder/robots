package com.sirius.robots.service.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 枚举修改对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/9
 */
@Getter
@Setter
@ToString
public class EnumsBO implements Serializable {

    private static final long serialVersionUID = 6463578213630284755L;
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

}