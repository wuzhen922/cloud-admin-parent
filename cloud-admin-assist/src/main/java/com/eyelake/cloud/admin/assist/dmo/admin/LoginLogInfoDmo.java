package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 登录日志表
 * @author 
 *
 */
@Table(name = "t_login_log_info")
public class LoginLogInfoDmo extends Entity{

	private static final long serialVersionUID = 6633561401492944060L;
	
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/**
	 * 登录用户名
	 */
	@Column(name = "user_name")
	private String userName;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;
	
	/**
	 * 登录用户ID
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 平台
	 * 0：微信
	 * 1：管理后台
	 */
	@Column(name = "mode")
	private String mode;
	
	/**
	 * 登录时间
	 */
	@Column(name = "login_time")
	private Date loginTime;
	
	/**
	 * 登录描述
	 */
	@Column(name = "login_desc")
	private String loginDesc;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the loginTime
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * @param loginTime the loginTime to set
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * @return the loginDesc
	 */
	public String getLoginDesc() {
		return loginDesc;
	}

	/**
	 * @param loginDesc the loginDesc to set
	 */
	public void setLoginDesc(String loginDesc) {
		this.loginDesc = loginDesc;
	}

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
