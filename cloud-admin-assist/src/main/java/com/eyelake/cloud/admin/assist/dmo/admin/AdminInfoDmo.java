package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 管理员信息表
 * @author 
 *
 */
@Table(name= "t_cloud_admin_info")
public class AdminInfoDmo extends Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2820639579321599354L;


	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	
	/**
	 * 管理员用户名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 管理员登录密码
	 */
	@Column(name = "password")
	private String passWord;
	
	/**
	 * 管理员手机号码
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 管理员邮箱
	 */
	@Column(name = "e_mail")
	private String email;
	

	/**
	 * 管理员描述
	 */
	@Column(name = "user_desc")
	private String userDesc;
	
	/**
	 * 真实姓名
	 */
	@Column(name = "real_name")
	private String realName;
	
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
	 * 登录失败次数
	 */
	@Column(name = "login_fail_count")
	private Long loginFailCount;
	
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getUserDesc() {
		return userDesc;
	}

	public void setUserDesc(String userDesc) {
		this.userDesc = userDesc;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
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

	public Long getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(Long loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
