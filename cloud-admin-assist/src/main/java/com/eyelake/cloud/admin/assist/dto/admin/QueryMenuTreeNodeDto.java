package com.eyelake.cloud.admin.assist.dto.admin;

import java.io.Serializable;

/**
 * 查询菜单树节点
 * Created by wunder on 2016/10/11 23:37.
 */
public class QueryMenuTreeNodeDto implements Serializable {

    private static final long serialVersionUID = -3347403491507616623L;

    private String subMenuLevel;

    private String roleId;

    public String getSubMenuLevel() {
        return subMenuLevel;
    }

    public void setSubMenuLevel(String subMenuLevel) {
        this.subMenuLevel = subMenuLevel;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
