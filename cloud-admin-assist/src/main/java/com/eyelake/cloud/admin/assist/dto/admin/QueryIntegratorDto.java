package com.eyelake.cloud.admin.assist.dto.admin;

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
public class QueryIntegratorDto extends Entity {

    private static final long serialVersionUID = 6039621565672177412L;

    private Long id;
    /**
     * 集成商企业用户名
     */
    private String userName;

    /**
     * 集成商企业用户真实姓名
     */
    private String realName;

    /**
     * 集成商企业名称
     */
    private String company;

    /**
     * 集成商企业手机号码
     */
    private String phone;

    /**
     * 集成商企业地址
     */
    private String address;

    /**
     * 鉴权key1
     */
    private String integratorKey;

    /**
     * 鉴权secret
     */
    private String secret;

    /**
     * 入网许可号
     */
    private String admittance;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后一次更新时间
     */
    private Date  lastUpdateTime;

    /**
     * 集成商管理员状态
     */
    private String status;

    /**
     * 集成商禁用原因
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntegratorKey() {
        return integratorKey;
    }

    public void setIntegratorKey(String integratorKey) {
        this.integratorKey = integratorKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAdmittance() {
        return admittance;
    }

    public void setAdmittance(String admittance) {
        this.admittance = admittance;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
