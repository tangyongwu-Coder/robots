package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据基础模型
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/12
 */
@Data
public class BaseDO implements Serializable {

    private static final long serialVersionUID = 8391442196301842606L;

    /**
     * 数据库主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 最后更新人
     */
    private String updatedBy;

}
