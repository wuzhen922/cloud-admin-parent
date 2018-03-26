package com.eyelake.cloud.admin.framework.account.form;

/**
 * 云平台统计form
 * Created by wff on 2018/1/24.
 */
public class SystemStatisticsForm {

    private String accountDate;
    //统计年月的标识
    private String mark;

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
