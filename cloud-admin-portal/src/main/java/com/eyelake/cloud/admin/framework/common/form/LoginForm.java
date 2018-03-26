package com.eyelake.cloud.admin.framework.common.form;

/**
 * 登录表单
 */
public class LoginForm {

	private String userName;

	private String password;

	private String loginType;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
}
