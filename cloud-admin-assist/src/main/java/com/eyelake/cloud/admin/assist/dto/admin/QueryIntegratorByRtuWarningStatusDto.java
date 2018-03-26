package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;
/**
 * 网卡预览DTO,根据集成商下的网关状态排名
 * 
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */
public class QueryIntegratorByRtuWarningStatusDto extends Entity {


    private static final long serialVersionUID = 3488379376825910727L;

    private Long integratorId;

    private String integratorName;

    private String integratorKey;

    private Long normalSum;

    private Long warningSum;

    private Long exhaustSum;

    private Long rtuSum;

    private Double normalPercentage;

    private Double warningPercentage;

    private Double exhaustPercentage;


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

    public String getIntegratorKey() {
        return integratorKey;
    }

    public void setIntegratorKey(String integratorKey) {
        this.integratorKey = integratorKey;
    }

    public Double getNormalPercentage() {
        return normalPercentage;
    }

    public void setNormalPercentage(Double normalPercentage) {
        this.normalPercentage = normalPercentage;
    }

    public Double getWarningPercentage() {
        return warningPercentage;
    }

    public void setWarningPercentage(Double warningPercentage) {
        this.warningPercentage = warningPercentage;
    }

    public Double getExhaustPercentage() {
        return exhaustPercentage;
    }

    public void setExhaustPercentage(Double exhaustPercentage) {
        this.exhaustPercentage = exhaustPercentage;
    }

    public Long getNormalSum() {
        return normalSum;
    }

    public void setNormalSum(Long normalSum) {
        this.normalSum = normalSum;
    }

    public Long getWarningSum() {
        return warningSum;
    }

    public void setWarningSum(Long warningSum) {
        this.warningSum = warningSum;
    }

    public Long getExhaustSum() {
        return exhaustSum;
    }

    public void setExhaustSum(Long exhaustSum) {
        this.exhaustSum = exhaustSum;
    }

    public Long getRtuSum() {
        return rtuSum;
    }

    public void setRtuSum(Long rtuSum) {
        this.rtuSum = rtuSum;
    }
}
