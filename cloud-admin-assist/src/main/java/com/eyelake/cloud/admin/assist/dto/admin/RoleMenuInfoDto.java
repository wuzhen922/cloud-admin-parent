package com.eyelake.cloud.admin.assist.dto.admin;


import com.yjh.framework.core.entity.Entity;

import java.util.List;

/**
 * Created by wunder on 2016/10/12 00:26.
 */
public class RoleMenuInfoDto extends Entity {

    private static final long serialVersionUID = -3844544691140548048L;

    private Long roleId;

    private List<Long> menuIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
