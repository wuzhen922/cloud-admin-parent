package com.eyelake.cloud.admin.framework.authority.form;

/**
 * Created by wunder on 2016/10/19 00:34.
 */
public class BindingRoleForm {

    private Long userId;

    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
