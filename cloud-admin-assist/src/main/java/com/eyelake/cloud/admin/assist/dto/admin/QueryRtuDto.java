package com.eyelake.cloud.admin.assist.dto.admin;

import com.eyelake.framework.utils.DateUtil;
import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.Column;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 根据条件查询集成商企业
 *
 * @author  j_cong
 * @date    2017-12-12
 * @version V1.0
 */
public class QueryRtuDto extends Entity {

    private static final long serialVersionUID = -2712585454415804096L;

    private Long id;

    /**
     * RTU 名称
     */
    private String rtuName;
    /**
     * RTU的SN码，唯一编号
     */
    private String snNumber;

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
     * RTU所属的集成商企业名称
     */
    private String company;

    /**
     * 业主企业ID
     */
    private Long ownerId;

    /**
     * 业主企业名称
     */
    private String orgName;

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
     *
     * 10：正常
     * 20：禁用
     * 99：已删除
     */

    private String status;

    /**
     * 机器码
     */
    private String machineCode;

    /**
     * 运营商
     * 00中国移动
     * 01中国联通
     * 02中国电信
     */
    private String operator;

    /**
     * 套餐名称字符串，以分号隔开
     */
    private String packName;

    /**
     * 月使用流量
     */
    private Double costTraffic;

    /**
     * 套餐总量
     */
    private long packTraffic;

    private String fixedPackName;

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRtuName() {
        return rtuName;
    }

    public void setRtuName(String rtuName) {
        this.rtuName = rtuName;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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


    public String getFixedPackName() {
        return fixedPackName;
    }

    public void setFixedPackName(String fixedPackName) {
        this.fixedPackName = fixedPackName;
    }

    public Double getCostTraffic() {
        return costTraffic;
    }

    public void setCostTraffic(Double costTraffic) {
        this.costTraffic = costTraffic;
    }

    public long getPackTraffic() {
        return packTraffic;
    }

    public void setPackTraffic(long packTraffic) {
        this.packTraffic = packTraffic;
    }
}
