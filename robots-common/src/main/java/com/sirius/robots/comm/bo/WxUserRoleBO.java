package com.sirius.robots.comm.bo;

import lombok.Data;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2020/10/19
 */
@Data
public class WxUserRoleBO {
    /**
     *
     */
    private Integer userId;
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


}