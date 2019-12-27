package com.sirius.robots.comm.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 批次查询结果对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/8/17
 */
@Data
public class BatchQueryResultBO<R,U> implements Serializable {

    private static final long serialVersionUID = 5377730695272528384L;
    /**
     * 分页结果
     */
    private PageDTO<R> page;

    /**
     * 额外结果
     */
    private List<U> results;

    /**
     * 额外结果
     */
    private U result;
}
