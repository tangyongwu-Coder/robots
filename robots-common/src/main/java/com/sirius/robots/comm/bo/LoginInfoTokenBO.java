package com.sirius.robots.comm.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *@author liang_shi
 *@date 2018/11/15 16:38
 *@description 登录信息
 */
@Data
@ToString
public class LoginInfoTokenBO implements Serializable {

	private static final long serialVersionUID = -3101368672439836475L;
	/**
	 * 操作员Id
	 */
	private Long userId;

	/**
	 * 登录帐户
	 */
	private String username;

	/**
	 * 访问令牌
	 */
	private String token;

	/**
	 * 系统类型
	 */
	private String systemType;

	/**
	 * TOKEN类型
	 */
	private String tokenType;

	/**
	 * 时间挫
	 */
	private Long timestamp = System.currentTimeMillis();

}