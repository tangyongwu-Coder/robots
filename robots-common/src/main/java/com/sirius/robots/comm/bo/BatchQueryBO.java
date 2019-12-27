package com.sirius.robots.comm.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 批次查询对象
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/8/17
 */
@Data
public class BatchQueryBO<T extends BaseReqDTO,U> implements Serializable {

    private static final long serialVersionUID = -5595944632465592621L;

    /**
     * 额外查询条件
     */
    private U query;
    /**
     * 传入查询条件
     */
    private T reqDTO;

}
