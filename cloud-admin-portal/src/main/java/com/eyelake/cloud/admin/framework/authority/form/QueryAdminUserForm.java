package com.eyelake.cloud.admin.framework.authority.form;

/**
 * 管理员信息表单
 *
 * @author eyelake
 */
public class QueryAdminUserForm {

    private String userName;

    private String pageNum;

    private String pageSize;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
