package com.eyelake.cloud.common.dto;

import java.io.Serializable;

/**
 * 用户信息相关的session
 * 
 * @author 乔建胜 2014年2月8日
 */
public class UserSession implements Serializable {

    private static final long serialVersionUID = 1356803237527761369L;

    /** 集成商管理员Id */
    private String            userId;

    /** 用户名 */
    private String            name;

    /** 用户角色 */
    private String            roleId;

    /**
     * 集成商企业状态
     * <p>
     * 10：正常
     * 20：禁用
     * 99：已删除
     */
    private String companyStatus;

    /**
     * 鉴权key
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

     /*
    * 集成商企业id
    * */

    private  String integratorId;

    public String getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(String integratorId) {
        this.integratorId = integratorId;
    }

    public String getIntegratorKey() {
        return integratorKey;
    }

    public void setIntegratorKey(String integratorKey) {
        this.integratorKey = integratorKey;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
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

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the roleId
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /** 按钮权限 */
    // private List<String> permissionCode;TODO:等页面需要用到权限编码时再使用该属性

}
