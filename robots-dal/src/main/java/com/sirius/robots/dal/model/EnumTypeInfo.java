package com.sirius.robots.dal.model;

import lombok.Data;

import java.io.Serializable;

import java.lang.Integer;

/**
 * 枚举类型信息表实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2020-01-07
  */
@Data
public class EnumTypeInfo extends BaseDO implements Serializable {

	private static final long serialVersionUID=1578379150243L;
   /**
    * 枚举类型CODE
    */
	private String typeCode;
   /**
    * 枚举类型描述
    */
	private String typeName;
   /**
    * NORMAL 正常 DISABLE 禁用
    */
	private String enumStatus;
   /**
    * 删除标识 0正常 1删除
    */
	private Integer deleteFlag;
}
