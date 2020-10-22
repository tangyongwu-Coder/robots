package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;
import java.util.Date;
import java.util.List;

/**
 * SpendInfo实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-10-21
  */
@Data
public class SpendInfo implements Serializable {

	private static final long serialVersionUID=1L;
   /**
    * 
    */
	private Integer id;
   /**
    * 开支类型 收入/支出
    */
	private String spendType;
   /**
    * 金额
    */
	private Long amt;
   /**
    * 开支渠道
    */
	private String channel;
   /**
    * 备注
    */
	private String remark;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String status;
   /**
    * 用户编号
    */
	private Integer userId;
   /**
    * 家庭编号
    */
	private Integer familyId;
   /**
    * 统计日期
    */
	private String countDate;
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

	private Boolean isMultiple;

	private List<SpendInfo> list;
}
