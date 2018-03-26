package com.eyelake.cloud.admin.framework.rtu.form;

/**
 * 集成商信息表单
 *
 * @author eyelake
 */
public class QueryRtuForm {

    private String rtuName;

    private String rtuTransType;

    private String company;

    private String integratorId;

    private String pageNum;

    private String pageSize;

    public String getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(String integratorId) {
        this.integratorId = integratorId;
    }

    public String getRtuName() {
        return rtuName;
    }

    public void setRtuName(String rtuName) {
        this.rtuName = rtuName;
    }

    public String getRtuTransType() {
        return rtuTransType;
    }

    public void setRtuTransType(String rtuTransType) {
        this.rtuTransType = rtuTransType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
