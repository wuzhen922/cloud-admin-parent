package com.eyelake.cloud.admin.assist.result;

import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.yjh.framework.lang.Result;


/**
 * 登录结果
 */
public class AdminLoginResult extends Result{

	private static final long serialVersionUID = 8872240089430203272L;

	private AdminInfoDmo adminInfoDmo;

    public AdminInfoDmo getAdminInfoDmo() {
        return adminInfoDmo;
    }

    public void setAdminInfoDmo(AdminInfoDmo adminInfoDmo) {
        this.adminInfoDmo = adminInfoDmo;
    }
}
