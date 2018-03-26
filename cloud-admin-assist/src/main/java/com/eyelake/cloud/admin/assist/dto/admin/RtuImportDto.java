package com.eyelake.cloud.admin.assist.dto.admin;

import com.eyelake.framework.core.entity.Entity;

import java.util.Date;

/**
 * @Author CuiXw
 * @Date 2018/1/16
 */
public class RtuImportDto extends Entity {


    private static final long serialVersionUID = -4082579305078820112L;
    private String rtuName;

    private String rtuModel;

    private String rtuTransType;

    private String rtuAccessType;

    private String snNumber;

    private String machineCode;

    private String operatorEnum;

    private String integratorAccessCode;

    private Date createTime;

    private Date lastUpdateTime;

    private String status;

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

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getOperatorEnum() {
        return operatorEnum;
    }

    public void setOperatorEnum(String operatorEnum) {
        this.operatorEnum = operatorEnum;
    }

    public String getIntegratorAccessCode() {
        return integratorAccessCode;
    }

    public void setIntegratorAccessCode(String integratorAccessCode) {
        this.integratorAccessCode = integratorAccessCode;
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
