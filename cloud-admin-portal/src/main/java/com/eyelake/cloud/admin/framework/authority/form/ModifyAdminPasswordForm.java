package com.eyelake.cloud.admin.framework.authority.form;

/**
 * Created by wunder on 2016/10/19 00:36.
 */
public class ModifyAdminPasswordForm {

    private Long userId;

    private String oldPassword;

    private String newPassword;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
