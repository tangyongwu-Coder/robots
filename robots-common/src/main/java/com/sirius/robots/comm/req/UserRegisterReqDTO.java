package com.sirius.robots.comm.req;


import com.sirius.robots.comm.util.ValidatePattenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户注册请求参数
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/26
 */
@Getter
@Setter
@ToString
public class UserRegisterReqDTO implements Serializable {

    private static final long serialVersionUID = -5562609626132402007L;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = ValidatePattenUtil.USER_NAME_REGEX,message = "用户名必须为6-16位字母数字下划线组成且开头组成")
    private String loginName;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = ValidatePattenUtil.EMAIL_REGEX,message = "邮箱格式不正确")
    private String email;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = ValidatePattenUtil.PASS_WORD_REGEX,message = "密码必须由字母+数字组合，长度大于8位")
    private String passWord;
    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码密码不能为空")
    @Pattern(regexp = ValidatePattenUtil.PASS_WORD_REGEX,message = "密码必须由字母+数字组合，长度大于8位")
    private String passWord2;

}