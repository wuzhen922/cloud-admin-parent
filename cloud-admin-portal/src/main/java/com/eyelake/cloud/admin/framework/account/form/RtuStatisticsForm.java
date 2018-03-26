package com.eyelake.cloud.admin.framework.account.form;

/**
 * rtu 统计form表单
 *
 */
public class RtuStatisticsForm {

    private String snNumber;
    private String accountDate;
    //统计年月的标识
    private String mark;

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
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