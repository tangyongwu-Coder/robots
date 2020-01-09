package com.sirius.robots.comm.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 修改公共请求参数
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2020/1/9
 */
@Getter
@Setter
@ToString
public class EditCommReqDTO implements Serializable {

    private static final long serialVersionUID = -145468675884758834L;
    /**
     * 删除Id列表
     */
    private List<Integer> deleteIds;
}