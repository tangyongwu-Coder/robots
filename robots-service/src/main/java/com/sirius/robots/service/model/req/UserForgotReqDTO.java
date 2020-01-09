package com.sirius.robots.service.model.req;


import com.sirius.robots.comm.util.ValidatePattenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 用户忘记密码请求参数
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/26
 */
@Getter
@Setter
@ToString
public class UserForgotReqDTO implements Serializable {

    private static final long serialVersionUID = 6194657533286274185L;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = ValidatePattenUtil.EMAIL_REGEX,message = "邮箱格式不正确,,请重新输入")
    private String email;
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Length(min =6 ,message = "验证码格式不正确")
    private String passWord;

}