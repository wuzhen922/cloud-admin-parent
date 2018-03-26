package com.eyelake.cloud.admin.assist.dmo.admin;


import com.yjh.framework.core.entity.Entity;
import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;

import java.util.Date;

/**
 * 集成商企业年流量表
 * 
 * @author  j_cong
 * @date    2018/01/19
 * @version V1.0
 */
@Table(name= "t_integrator_year")
public class IntegratorYearDmo extends Entity {

    private static final long serialVersionUID = 7277351477283746012L;
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
     * 流量日期，具体到年
     */
    @Column(name = "year_date")
    private Date yearDate;

    /**
     * 年使用流量（M）
     */
    @Column(name = "year_traffic")
    private Double yearTraffic;


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

    public Date getYearDate() {
        return yearDate;
    }

    public void setYearDate(Date yearDate) {
        this.yearDate = yearDate;
    }

    public Double getYearTraffic() {
        return yearTraffic;
    }

    public void setYearTraffic(Double yearTraffic) {
        this.yearTraffic = yearTraffic;
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
