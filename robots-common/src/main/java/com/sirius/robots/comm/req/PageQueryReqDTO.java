package com.sirius.robots.comm.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sirius.robots.comm.bo.PageDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 分页查询请求对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/7
 */
@Getter
@Setter
@ToString
public class PageQueryReqDTO implements Serializable {

    private static final long serialVersionUID = -2123491040250979977L;

    @ApiModelProperty("分页数据")
    private PageDTO pageDTO;
}