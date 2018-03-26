package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 集成商企业信息表
 * @author j_cong
 *
 */
@Table(name= "t_integrator_info")
public class IntegratorDmo extends Entity {

    private static final long serialVersionUID = -9223167447986301227L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	
	/**
	 * 集成商企业地址
	 */
	@Column(name = "address")
	private String address;
	
	/**
	 * 集成商企业名
	 */
	@Column(name = "company")
	private String company;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 鉴权key1
	 */
	@Column(name = "integrator_key")
	private String integratorKey;
	
	/**
	 * 鉴权secret
	 */
	@Column(name = "secret")
	private String secret;
	
	/**
	 * 入网许可号
	 */
	@Column(name = "admittance")
	private String admittance;

	
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
	

	/**
	 * 状态
	 */
	@Column(name = "status")
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
