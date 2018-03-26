package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 业主企业信息表
 * @author j_cong
 *
 */
@Table(name= "t_owner_info")
public class OwnerDmo extends Entity {

    private static final long serialVersionUID = 9120675498121328L;

    /**
	 * 业主企业id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	
	/**
	 * 业主企业名称
	 */
	@Column(name = "org_name")
	private String orgName;
	
	/**
	 * 业主企业电话
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 业主所属行业
	 */
	@Column(name = "industry")
	private String industry;
	
	/**
	 * 省名称
	 */
	@Column(name = "province_name")
	private String provinceName;
	
	/**
	 * 市名称
	 */
	@Column(name = "city_name")
	private String cityName;
	
	/**
	 * 业主详细地址
	 */
	@Column(name = "address_desc")
	private String addressDesc;

    /**
     * 组织机构号
     */
    @Column(name = "org_number")
    private String orgNumber;

    /**
     * 集成商企业id
     */
    @Column(name = "integrator_id")
    private Long integratorId;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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
     *
     * 10：正常
     * 20：禁用
     * 99：已删除
     */
	@Column(name = "status")
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(String orgNumber) {
        this.orgNumber = orgNumber;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
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
