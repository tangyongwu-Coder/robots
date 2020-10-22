package com.sirius.robots.manager.model;

import lombok.Data;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Data
public class WxUserBO {
    /**
     *
     */
    private Integer userId;
    /**
     * 家庭编号
     */
    private Integer familyId;
    /**
     * 微信编号
     */
    private String userCode;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户类型
     */
    private String userType;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 电子邮件
     */
    private String email;

    /**
     * 是否关注
     */
    private String isFollow;

    /**
     *
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色类型(SUPPER 超级管理员 MANAGER 管理员 NORMAL 普通人员 )
     */
    private String roleType;
    /**
     * 角色分类
     */
    private String roleCategory;
    /**
     * 角色权限类型（数据权限，资源权限)
     */
    private String rolePowerType;

    /**
     * 是否管理员
     */
    private Boolean isManager;

    /**
     * 是否超级管理员
     */
    private Boolean isSuper;
    /**
     * 是否授权
     */
    private Boolean isAuth;

    /**
     * 是否家庭
     */
    private Boolean isFamily;

}