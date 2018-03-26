package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 菜单信息表
 * @author zhouqian
 *
 */
@Table(name = "t_cloud_menu_info")
public class MenuInfoDmo extends Entity{

	private static final long serialVersionUID = 6258516232499656618L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/**
	 * 菜单名称
	 */
	@Column(name = "menu_name")
	private String menuName;
	
	/**
	 * 菜单url
	 */
	@Column(name = "menu_url")
	private String menuUrl;
	
	/**
	 * 菜单等级
	 */
	@Column(name = "menu_level")
	private String menuLevel;
	
	/**
	 * 父菜单id
	 */
	@Column(name = "parent_id")
	private Long parentId;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;
	
	/**
	 * 最新更新时间
	 */
	@Column(name = "last_update_time")
	private Date lastUpdateTime;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	private String status;
	
	/**
	 * 排序
	 */
	@Column(name = "sort")
	private Long sort;

    /**
     * 菜单图标
     */
    @Column(name = "menu_icon")
    private String menuIcon;

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
	 * @return the menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * @return the menuUrl
	 */
	public String getMenuUrl() {
		return menuUrl;
	}

	/**
	 * @param menuUrl the menuUrl to set
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * @return the menuLevel
	 */
	public String getMenuLevel() {
		return menuLevel;
	}

	/**
	 * @param menuLevel the menuLevel to set
	 */
	public void setMenuLevel(String menuLevel) {
		this.menuLevel = menuLevel;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the sort
	 */
	public Long getSort() {
		return sort;
	}

	/**
	 * @param sort the sort to set
	 */
	public void setSort(Long sort) {
		this.sort = sort;
	}

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }
}
