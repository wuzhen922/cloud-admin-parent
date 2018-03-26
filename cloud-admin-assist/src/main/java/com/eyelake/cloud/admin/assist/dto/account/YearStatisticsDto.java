package com.eyelake.cloud.admin.assist.dto.account;

import com.yjh.framework.core.entity.Entity;

import java.util.Date;


/**
 * 年统计Dto
 * Created by wff on 2018/1/23.
 */
public class YearStatisticsDto extends Entity {

    private static final long serialVersionUID = 6217332314023531748L;


    private Long id;

    /**
     * RTU SN码
     */
    private String snNumber;

    /**
     * 要统计的年份
     */
    private String accountYear;

    /**
     * 要统计的年对应的月份
     */
    private String queryMonth;

    /**
     * 集成商企业ID
     */
    private Long integratorId;

    /**
     * 业主企业ID
     */
    private Long ownerId;


    /**
     * 月使用流量（M）
     */
    private Double monthTraffic;


    /**
     * 月总通用流量（M）
     */
    private int monthPackTraffic;

    /**
     * 月套餐外通用流量（M）
     */
    private Double monthOutPackTraffic;

    /**
     * 月套餐内使用流量（M）
     */
    private Double monthInPackTraffic;

    /**
     * 流量日期，具体到年月
     */
    private Date monthDate;

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

    public Double getMonthInPackTraffic() {
        return monthInPackTraffic;
    }

    public void setMonthInPackTraffic(Double monthInPackTraffic) {
        this.monthInPackTraffic = monthInPackTraffic;
    }

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


    public Double getMonthTraffic() {
        return monthTraffic;
    }

    public void setMonthTraffic(Double monthTraffic) {
        this.monthTraffic = monthTraffic;
    }

    public int getMonthPackTraffic() {
        return monthPackTraffic;
    }

    public void setMonthPackTraffic(int monthPackTraffic) {
        this.monthPackTraffic = monthPackTraffic;
    }

    public Double getMonthOutPackTraffic() {
        return monthOutPackTraffic;
    }

    public void setMonthOutPackTraffic(Double monthOutPackTraffic) {
        this.monthOutPackTraffic = monthOutPackTraffic;
    }

    public String getAccountYear() {
        return accountYear;
    }

    public void setAccountYear(String accountYear) {
        this.accountYear = accountYear;
    }

    public String getQueryMonth() {
        return queryMonth;
    }

    public void setQueryMonth(String queryMonth) {
        this.queryMonth = queryMonth;
    }

    public Date getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(Date monthDate) {
        this.monthDate = monthDate;
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
}
