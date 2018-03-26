package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * RTUdto
 * @author xudajan
 *
 */
public class RtuInfoDto extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
    /**
	 * 主键
	 */
	private Long id;

	/**
	 * RTU的SN码（网卡编号）
	 */
	private String snNumber;
	
	/**
	 * RTU名称
	 */
	private String rtuName;
	
	/**
	 * RTU 型号
	 */
	private String rtuModel;
	
	/**
	 * RTU传输类型
     *
     * 00: 4G
     * 10：NB-IoT
     * 20：北斗
     * 30：天通
	 */
	private String rtuTransType;
	
	/**
	 * RTU接入类型（有无通道）
     *
     * 00：有通道
     * 99：无通道
	 */
	private String rtuAccessType;

    /**
     * 集成商企业ID
     */
    private Long integratorId;

    /**
     * 集成商企业名称
     */
    private String integratorName;

    /**
     * 业主企业ID
     */
    private Long ownerId;

    /**
     * 业主企业名称
     */
    private String orgName;

    /**
     * 机器码
     */
    private String machineCode;

    /**
     * 运营商枚举
     * 中国移动：00
     * 中国联通：01
     * 中国电信：03
     */
    private String carrierOperatorEnum;

    /**
     * 备注
     */
    private String remark;

	/**
	 * 创建时间
	 */
	private Date  createTime;
	
	/**
	 * 最后一次更新时间
	 */
	private Date  lastUpdateTime;

	/**
	 * 状态
     * 00: 未激活
     * 10：正常
     * 20：禁用
     * 99：已删除
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

    public String getRtuName() {
        return rtuName;
    }

    public void setRtuName(String rtuName) {
        this.rtuName = rtuName;
    }

    public String getRtuModel() {
        return rtuModel;
    }

    public void setRtuModel(String rtuModel) {
        this.rtuModel = rtuModel;
    }

    public String getRtuTransType() {
        return rtuTransType;
    }

    public void setRtuTransType(String rtuTransType) {
        this.rtuTransType = rtuTransType;
    }

    public String getRtuAccessType() {
        return rtuAccessType;
    }

    public void setRtuAccessType(String rtuAccessType) {
        this.rtuAccessType = rtuAccessType;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getIntegratorName() {
        return integratorName;
    }

    public void setIntegratorName(String integratorName) {
        this.integratorName = integratorName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getCarrierOperatorEnum() {
        return carrierOperatorEnum;
    }

    public void setCarrierOperatorEnum(String carrierOperatorEnum) {
        this.carrierOperatorEnum = carrierOperatorEnum;
    }
}
