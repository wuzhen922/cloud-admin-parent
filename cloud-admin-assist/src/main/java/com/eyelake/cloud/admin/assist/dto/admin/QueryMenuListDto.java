package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

/**
 * 查询菜单列表
 * Created by wunder on 2016/10/14 07:07.
 */
public class QueryMenuListDto extends Entity {

    private static final long serialVersionUID = 8440512223715502913L;

    private Long userId;

    private String menuLevel;

    private Long parentId;

    private String status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMenuLevel() {
        return menuLevel;
    }

    public void setMenuLevel(String menuLevel) {
        this.menuLevel = menuLevel;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
