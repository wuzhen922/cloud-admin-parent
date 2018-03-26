package com.eyelake.cloud.admin.assist.dmo.admin;



import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 网关月流量表
 * 
 * @author  j_cong
 * @date    2018/01/19
 * @version V1.0
 */
@Table(name= "t_rtu_month")
public class RtuMonthDmo extends Entity {


    private static final long serialVersionUID = -1792004932884066702L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    /**
     * RTU SN码
     */
    @Column(name = "sn_number")
    private String snNumber;

    /**
     * 集成商企业ID
     */
    @Column(name = "integrator_id")
    private Long integratorId;

    /**
     * 流量日期，具体到年月
     */
    @Column(name = "month_date")
    private Date monthDate;

    /**
     * 月使用流量（M）
     */
    @Column(name = "month_traffic")
    private Double monthTraffic;


    /**
     * 月总通用流量（M）
     */
    @Column(name = "month_pack_traffic")
    private Long monthPackTraffic;

    /**
     * 固定套餐名称
     */
    @Column(name = "fixed_pack_name")
    private String fixedPackName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    @Column(name = "last_update_time")
    private Date  lastUpdateTime;

    /**
     * 状态
     *   10: 正常
     *   99：已删除
     */
    @Column(name = "status")
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

    public Date getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(Date monthDate) {
        this.monthDate = monthDate;
    }

    public Double getMonthTraffic() {
        return monthTraffic;
    }

    public void setMonthTraffic(Double monthTraffic) {
        this.monthTraffic = monthTraffic;
    }

    public Long getMonthPackTraffic() {
        return monthPackTraffic;
    }

    public void setMonthPackTraffic(Long monthPackTraffic) {
        this.monthPackTraffic = monthPackTraffic;
    }

    public String getFixedPackName() {
        return fixedPackName;
    }

    public void setFixedPackName(String fixedPackName) {
        this.fixedPackName = fixedPackName;
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
}
