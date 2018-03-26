package com.eyelake.cloud.admin.framework.account.form;

/*
*
*  业主企业form表单
*
*/
public class CompanyStatisticsForm {

    private String companyId;
    private String accountDate;
    //统计年月的标识
    private String mark;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(String accountDate) {
        this.accountDate = accountDate;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}