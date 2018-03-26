package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 网卡阈值表
 * @author :xudajan
 *
 */
@Table(name= "t_rtu_warning")
public class RtuWarningDmo extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

    /**
     * 绑在rtu上卡的ccid
     */
    @Column(name = "sn_number")
    private String snNumber;

    /**
     * 阈值(KB)
     */
    @Column(name = "warning_num")
    private Double warningNum;

    /**
     * 套餐已用量(KB)
     */
    @Column(name = "cost_num")
    private Double costNum;



    /**
     * 套餐名称
     */
    @Column(name = "pack_name")
    private String packName;

    /**
     * 套餐流量值（M）
     */
    @Column(name = "pack_traffic")
    private Long packTraffic;


    /**
     * 流量使用状态00:正常01：流量预警02：超出套餐
     */
    @Column(name = "warning_status")
    private String warningStatus;


    /**
     * 状态 10：正常 20：失效 99：删除
     */
    @Column(name = "status")
    private String status;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date  createTime;

	/**
	 * 最后一次更新时间
	 */
	@Column(name = "last_update_time")
	private Date  lastUpdateTime;


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

    public Double getWarningNum() {
        return warningNum;
    }

    public void setWarningNum(Double warningNum) {
        this.warningNum = warningNum;
    }

    public Double getCostNum() {
        return costNum;
    }

    public void setCostNum(Double costNum) {
        this.costNum = costNum;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Long getPackTraffic() {
        return packTraffic;
    }

    public void setPackTraffic(Long packTraffic) {
        this.packTraffic = packTraffic;
    }

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}

