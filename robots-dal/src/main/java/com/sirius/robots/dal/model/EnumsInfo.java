package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;

/**
 * 枚举信息表实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-01-07
  */
@Data
public class EnumsInfo extends BaseDO implements Serializable {

	private static final long serialVersionUID = 3003751251029893833L;
	/**
    * 枚举类型
    */
	private String enumType;
   /**
    * 枚举KEY
    */
	private String enumKey;
   /**
    * 枚举值
    */
	private String enumValue;
   /**
    * 枚举描述
    */
	private String enumDesc;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String enumStatus;
   /**
    * 删除标识 0正常 1删除
    */
	private Integer deleteFlag;
}
