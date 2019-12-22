package com.sirius.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/10/31 0031.
 */
@Getter
@Setter
@ToString
public class UserSignReqDTO implements Serializable {

    private static final long serialVersionUID = 7702788860608715578L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 确认密码
     */
    private String rePassWord;
    /**
     * 邮箱
     */
    private String email;
}
