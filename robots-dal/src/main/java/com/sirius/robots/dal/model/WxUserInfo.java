package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * WxUserInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-19
  */
@Data
public class WxUserInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 微信编号
    */
	private String userCode;
   /**
    * 用户名
    */
	private String userName;
   /**
    * 用户类型
    */
	private String userType;
   /**
    * 手机号码
    */
	private String mobile;
   /**
    * 电子邮件
    */
	private String email;
   /**
    * 是否关注
    */
	private String isFollow;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String userStatus;
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
