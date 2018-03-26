package com.eyelake.cloud.admin.framework.authority.form;

/**
 * Created by wunder on 2016/10/12 00:22.
 */
public class UpdateRoleMenuForm {

    private Long roleId;

    private String menuIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(String menuIdList) {
        this.menuIdList = menuIdList;
    }
}
