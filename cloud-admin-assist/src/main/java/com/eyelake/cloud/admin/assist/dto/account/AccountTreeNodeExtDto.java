package com.eyelake.cloud.admin.assist.dto.account;

import com.yjh.framework.core.entity.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * 台账树结构外部Dto
 * Created by wff on 2018/1/23.
 */
public class AccountTreeNodeExtDto extends Entity {

    private static final long serialVersionUID = -9133831450311104824L;
    private String id;

    private String title;

    private String parentId;

    private String snNumber;

    private DataDto data;

    private List<AccountTreeNodeExtDto> children = new ArrayList<>();



    public String getId() {
        return id;
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

    public List<AccountTreeNodeExtDto> getChildren() {
        return children;
    }

    public void setChildren(List<AccountTreeNodeExtDto> children) {
        this.children = children;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public DataDto getData() {
        return data;
    }

    public void setData(DataDto data) {
        this.data = data;
    }
}
