package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 集成商管理员信息表
 * @author j_cong
 *
 */
@Table(name= "t_integrator_manager_info")
public class IntegratorManagerDmo extends Entity {

    private static final long serialVersionUID = -1111368900839024003L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/**
	 * 用户名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name")
	private String realName;
	
	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * 集成商企业ID
	 */
	@Column(name = "integrator_id")
	private String integratorId;

	/**
	 * 集成商企业名称
	 */
	@Column(name = "company")
	private String company;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

    /**
     * 手机号码
     */
    @Column(name = "phone")
    private String phone;

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

    /**
     * 登录失败次数
     */
    @Column(name = "login_fail_count")
    private Long loginFailCount;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(String integratorId) {
        this.integratorId = integratorId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Long getLoginFailCount() {
        return loginFailCount;
    }

    public void setLoginFailCount(Long loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
}
