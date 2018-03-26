package com.eyelake.cloud.admin.assist.dmo.admin;


import com.yjh.framework.core.entity.Entity;
import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;

import java.util.Date;

/**
 * 集成商企业日流量表
 * 
 * @author  j_cong
 * @date    2018/01/19
 * @version V1.0
 */
@Table(name= "t_integrator_day")
public class IntegratorDayDmo extends Entity {


    private static final long serialVersionUID = -7165854291705736212L;
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;


    /**
     * 集成商企业ID
     */
    @Column(name = "integrator_id")
    private Long integratorId;

    /**
     * 流量日期，具体到年月日
     */
    @Column(name = "day_date")
    private Date dayDate;

    /**
     * 日使用流量（KB）
     */
    @Column(name = "day_traffic")
    private Double dayTraffic;


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
}
