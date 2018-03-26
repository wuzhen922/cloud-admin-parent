package com.eyelake.cloud.admin.framework.authority.form;

/**
 * Created by wunder on 2016/10/18 18:55.
 */
public class QueryMenuForm {

    private int pageNum;

    private int pageSize;

    private Long parentId;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}
