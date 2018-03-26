package com.eyelake.cloud.admin.framework.account.form;

/*
*
*  集成商form表单
*
*/
public class IntegratorStatisticsForm {

    private String integratorId;
    private String accountDate;
    //统计年月的标识
    private String mark;

    public String getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(String integratorId) {
        this.integratorId = integratorId;
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