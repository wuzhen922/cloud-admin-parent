package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 查询登录日志请求实体
 * Created by wunder on 2016/10/14 08:47.
 */
public class QueryLoginLogDto extends Entity {

    private static final long serialVersionUID = -8159668238589448194L;

    private String userName;

    private Date startDate;

    private Date endDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
