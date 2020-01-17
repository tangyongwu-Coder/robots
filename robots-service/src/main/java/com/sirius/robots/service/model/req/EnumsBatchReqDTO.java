package com.sirius.robots.service.model.req;

import com.sirius.robots.service.model.bo.EnumsBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 枚举批量操作请求对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Getter
@Setter
@ToString
public class EnumsBatchReqDTO implements Serializable {

    private static final long serialVersionUID = 5461534864158117893L;

    /**
     * 枚举类型
     */
    private String enumType;
    /**
     * 枚举列表
     */
    private List<EnumsBO> enums;

    private List<Long> deleteIds;

}