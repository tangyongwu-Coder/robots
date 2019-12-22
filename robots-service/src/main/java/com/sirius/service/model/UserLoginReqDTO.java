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
public class UserLoginReqDTO implements Serializable {

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
     * 是否记住密码
     */
    private String remember;
}
