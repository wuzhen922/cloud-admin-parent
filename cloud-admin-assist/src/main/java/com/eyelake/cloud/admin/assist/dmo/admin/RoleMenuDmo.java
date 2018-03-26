package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 
 * 角色与菜单关联实体
 * @author 
 *
 */

@Table(name = "t_cloud_role_menu")
public class RoleMenuDmo extends Entity {

	private static final long serialVersionUID = 871972142078083194L;

    /**
     * 主键
     */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/**
     * 角色ID
     */
	@Column(name = "role_id")
    private Long  roleId;
	
	/**
     * 菜单ID
     */
	@Column(name = "menu_id")
    private Long  menuId;
	
	/**
     * 创建时间
     */
	@Column(name = "create_time")
    private Date createTime;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
