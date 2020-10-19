package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * WxUserRole实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-19
  */
@Data
public class WxUserRole implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 
    */
	private Integer userId;
   /**
    * 
    */
	private Integer roleId;
   /**
    * 
    */
	private Integer deleteFlag;
   /**
    * 创建时间
    */
	private Date createdAt;
   /**
    * 创建人
    */
	private String createdBy;
   /**
    * 
    */
	private Date updatedAt;
   /**
    * 
    */
	private String updatedBy;
}
