package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * WoolInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-19
  */
@Data
public class WoolInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 线报类型
    */
	private String woolType;
   /**
    * 渠道类型
    */
	private String channelType;
   /**
    * 日期类型
    */
	private String dateType;
   /**
    * 时间
    */
	private String woolDate;
   /**
    * 下次时间
    */
	private Date nextTime;
   /**
    * 线报标题
    */
	private String woolTitle;
   /**
    * 线报内容
    */
	private String woolMsg;
   /**
    * 登记用户
    */
	private Integer countUserId;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String woolStatus;
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
