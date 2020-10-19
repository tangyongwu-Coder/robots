package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;

/**
 * MsgAuthInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-19
  */
@Data
public class MsgAuthInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 消息编号
    */
	private String msgId;
   /**
    * 消息类型
    */
	private String msgType;
   /**
    * 消息请求
    */
	private String msgReq;
   /**
    * 消息返回
    */
	private String msgRes;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String msgStatus;
   /**
    * 用户编号
    */
	private Integer userId;
   /**
    * 是否识别
    */
	private String isRead;
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
