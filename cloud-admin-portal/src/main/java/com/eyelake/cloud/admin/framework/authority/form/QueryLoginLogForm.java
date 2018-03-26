package com.eyelake.cloud.admin.framework.authority.form;

/**
 * Created by wunder on 2016/10/21 17:13.
 */
public class QueryLoginLogForm {

    private String startDate;

    private String endDate;

    private Long pageSize;

    private Long pageNum;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }
}
