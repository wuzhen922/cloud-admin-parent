package com.eyelake.cloud.admin.framework.authority.form;

import java.util.Date;

/**
 * Created by wunder on 2016/10/18 19:04.
 */
public class MenuInfoForm {

    private Long id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单等级
     */
    private String menuLevel;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最新更新时间
     */
    private Date lastUpdateTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 菜单图标
     */
    private String menuIcon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
