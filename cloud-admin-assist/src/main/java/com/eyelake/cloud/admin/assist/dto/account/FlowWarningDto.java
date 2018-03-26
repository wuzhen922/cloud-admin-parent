package com.eyelake.cloud.admin.assist.dto.account;

import com.eyelake.framework.lang.Request;

import java.util.Date;

public class FlowWarningDto extends Request {
    private static final long serialVersionUID = 6217332314823531745L;


    private Long id;
    /**
     * RTU的SN码
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
     * RTU接入类型（有无通道）
     *
     * 10：有通道
     * 99：无通道
     */
    private String rtuAccessType;

    /**
     * 集成商企业ID
     */
    private Long integratorId;
    private String integratorName;
    private String carrierOperatorEnum;
    /**
     * 业主企业ID
     */
    private Long ownerId;

    /**
     * 业主企业名称
     */
    private String orgName;

    /**
     * 状态
     *
     * 10：正常
     * 20：禁用
     * 99：已删除
     */
    private String status;
    /**
     * 阈值(KB)
     */

    private Double warningNum;

    /**
     * 套餐已用量(KB)
     */

    private Double costNum;



    /**
     * 套餐名称
     */

    private String packName;

    /**
     * 套餐流量值（M）
     */

    private Long packTraffic;


    /**
     * 流量使用状态00:正常01：流量预警02：超出套餐
     */

    private String warningStatus;
    /**
     * 最后一次更新时间
     */

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

    public String getIntegratorName() {
        return integratorName;
    }

    public void setIntegratorName(String integratorName) {
        this.integratorName = integratorName;
    }

    public String getCarrierOperatorEnum() {
        return carrierOperatorEnum;
    }

    public void setCarrierOperatorEnum(String carrierOperatorEnum) {
        this.carrierOperatorEnum = carrierOperatorEnum;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
