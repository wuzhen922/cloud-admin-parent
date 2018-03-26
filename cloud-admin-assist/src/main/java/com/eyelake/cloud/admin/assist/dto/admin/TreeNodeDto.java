package com.eyelake.cloud.admin.assist.dto.admin;


import com.yjh.framework.core.entity.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 传感器树结构外部Dto
 */
public class TreeNodeDto extends Entity {

    private static final long serialVersionUID = -9133831450311104824L;
    private String id;

    private String title;

    private String parentId;

   private String status;

    private List<TreeNodeDto> children = new ArrayList<>();

    private String snNumber;

    /**rtu节点使用的信息*/
    private TreeNodeDataInfoDto data;

    public String getId() {
        return id;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<TreeNodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeDto> children) {
        this.children = children;
    }

    public TreeNodeDataInfoDto getData() {
        return data;
    }

    public void setData(TreeNodeDataInfoDto data) {
        this.data = data;
    }
}
