package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * WxRoleInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-19
  */
@Data
public class WxRoleInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 角色名
    */
	private String roleName;
   /**
    * 角色类型(SUPPER 超级管理员 MANAGER 管理员 NORMAL 普通人员 )
    */
	private String roleType;
   /**
    * 角色分类
    */
	private String roleCategory;
   /**
    * 角色权限类型（数据权限，资源权限)
    */
	private String rolePowerType;
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
