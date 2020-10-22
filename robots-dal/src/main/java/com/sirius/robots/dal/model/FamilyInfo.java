package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * FamilyInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-21
  */
@Data
public class FamilyInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 名称
    */
	private String name;
   /**
    * 备注
    */
	private String remark;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String status;
   /**
    * 删除标识 0正常 1删除
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
    * 修改时间
    */
	private Date updatedAt;
   /**
    * 修改人
    */
	private String updatedBy;
}
