package com.eyelake.cloud.admin.assist.dto.admin;

import java.io.Serializable;

/**
 * 菜单树节点
 * Created by wunder on 2016/10/11 23:14.
 */
public class MenuTreeNodeDto implements Serializable {

    private static final long serialVersionUID = 4704693143734207411L;

    /**
     * id
     */
    private Long id;

    /**
     * 父节点id
     */
    private Long parentId;

    /**
     * name
     */
    private String name;

    /**
     * 是否勾选
     */
    private String checked;

    /**
     * 是否展开
     */
    private String open;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
