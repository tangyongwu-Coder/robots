package com.sirius.robots.service.model.req;

import com.sirius.robots.comm.req.PageQueryReqDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 枚举查询请求对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Getter
@Setter
@ToString(callSuper = true)
public class EnumsQueryReqDTO extends PageQueryReqDTO implements Serializable {


    private static final long serialVersionUID = -2258517691839396359L;
    /**
     * 枚举类型
     */
    private String enumType;

    /**
     * 枚举状态
     */
    private String enumStatus;
}