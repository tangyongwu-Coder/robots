package com.sirius.robots.service.model.req;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2019/11/21
 */
@Getter
@Setter
@ToString
public class SendCodeReqDTO implements Serializable {

    private static final long serialVersionUID = 7485056043967337112L;
    /**
     * 验证码类型
     */
    @NotBlank(message = "验证码类型不能为空")
    private String codeType;
    /**
     * 邮箱/手机
     */
    @NotBlank(message = "接收者不能为空")
    private String value;


}