package com.eyelake.cloud.admin.assist.dto.account;

import com.eyelake.framework.lang.Request;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;


/**
 *  月统计Dto 流量统计页面用
 * Created by wff on 2018/1/23.
 */
public class MonthStatisticsDto extends Entity {

    private static final long serialVersionUID = 6217332314023531748L;


    private Long id;

    /**
     * RTU SN码
     */
    private String snNumber;

    /**
     * 集成商企业ID
     */
    private Long integratorId;

    /**
     * 业主企业ID
     */
    private Long ownerId;

    /**
     * 流量日期，具体到年月日
     */
    private Date dayDate;

    /**
     * 要统计的年份月份
     */
    private String accountYearMonth;

    /**
     * 要统计的月份对应的每天
     */
    private String queryDay;

    /**
     * 日使用流量（KB）
     */
    private Double dayTraffic;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    private Date  lastUpdateTime;

    /**
     * 状态
     *   10: 正常
     *   99：已删除
     */
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }

    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public Double getDayTraffic() {
        return dayTraffic;
    }

    public void setDayTraffic(Double dayTraffic) {
        this.dayTraffic = dayTraffic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccountYearMonth() {
        return accountYearMonth;
    }

    public void setAccountYearMonth(String accountYearMonth) {
        this.accountYearMonth = accountYearMonth;
    }

    public String getQueryDay() {
        return queryDay;
    }

    public void setQueryDay(String queryDay) {
        this.queryDay = queryDay;
    }
}
