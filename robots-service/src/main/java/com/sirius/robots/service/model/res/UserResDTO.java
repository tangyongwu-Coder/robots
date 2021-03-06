package com.sirius.robots.service.model.res;

import com.sirius.robots.dal.model.BaseDO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息表实体类
 * 
 * @author 孟星魂
 * @version 5.0 createTime: 2019-12-30
  */
@Data
public class UserResDTO extends BaseDO implements Serializable {

	private static final long serialVersionUID=1577696081709L;
   /**
    * 登陆用户名
    */
	private String loginName;
   /**
    * 登录密码
    */
	private String passWord;
   /**
    * 是否记住 1是 0否
    */
	private String rememberMe;
   /**
    * 手机号码
    */
	private String mobile;
   /**
    * 电子邮件
    */
	private String email;
   /**
    * 是否启用 0启用 1不启用
    */
	private String useAble;
   /**
    * 删除标识 0正常 1删除
    */
	private Integer deleteFlag;
   /**
    * 1 是管理员 0 不是管理员
    */
	private String isManager;

	/**
	 * 登录令牌
	 */
	@ApiModelProperty("登录令牌")
	private String token;

	/**
	 * 刷新令牌
	 */
	@ApiModelProperty("刷新令牌")
	private String refreshToken;

	/**
	 * 系统类型
	 */
	@ApiModelProperty("系统类型")
	private String systemType;
}
