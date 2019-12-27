package com.sirius.robots.comm.req;


import com.sirius.robots.comm.util.ValidatePattenUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@Setter
@ToString
public class UserLoginReqDTO implements Serializable {

    private static final long serialVersionUID = 3312135580878117472L;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = ValidatePattenUtil.USER_NAME_REGEX,message = "用户名格式有误,请确认后再输入")
    private String loginName;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = ValidatePattenUtil.PASS_WORD_REGEX,message = "密码格式有误,请确认后再输入")
    private String passWord;

    /**
     * 记住我
     */
    private Boolean rememberMe;

}