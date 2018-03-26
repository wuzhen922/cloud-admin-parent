package com.eyelake.cloud.admin.framework.rtu.form;

/**
 * @Author CuiXw
 * @Date 2018/1/19
 */
public class AddRtuInfoForm {

    private String id;

    private String rtuName;

    private String rtuModel;

    private String rtuTransType;

    private String rtuAccessType;

    private String machineCode;

    private String snNumber;

    private String operator;

    private String integratorId;

    private String integratorName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIntegratorId(String integratorId) {
        this.integratorId = integratorId;
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

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getIntegratorId() {
        return integratorId;
    }

    public String getIntegratorName() {
        return integratorName;
    }

    public void setIntegratorName(String integratorName) {
        this.integratorName = integratorName;
    }
}
