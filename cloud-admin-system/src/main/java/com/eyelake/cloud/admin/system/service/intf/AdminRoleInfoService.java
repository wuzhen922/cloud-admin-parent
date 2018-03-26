package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.RoleInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.MenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryMenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.RoleMenuInfoDto;
import com.yjh.framework.lang.Result;

import com.yjh.framework.page.Page;

import java.util.List;


/**
 * @author wunder
 */
public interface AdminRoleInfoService {

    /**
     * 插入角色信息
     * @param roleInfoDmo
     * @return
     */
    public Result insert(RoleInfoDmo roleInfoDmo);

    /**
     * 更新角色信息
     * @param con
     * @return
     */
    public Result update(RoleInfoDmo con);

    /**
     * 删除角色信息
     * @param con
     * @return
     */
    public Result delete(RoleInfoDmo con);

    /**
     * 查找角色信息
     * @param con
     * @return
     */
    public RoleInfoDmo selectOne(RoleInfoDmo con);

    /**
     * 查询角色信息列表
     * @param con
     * @return
     */
    public List<RoleInfoDmo> selectList(RoleInfoDmo con);

	/**
	 * 查询角色列表
	 * @param con
	 * @param page
	 * @return
	 */
	public List<RoleInfoDmo> selectListByPage(RoleInfoDmo con, Page page);
	
	/**
	 * 新增角色
	 * @param roleInfoDmo
	 * @return
	 */
	public Result addRoleInfo(RoleInfoDmo roleInfoDmo);

    /**
	 * 修改角色
	 * @param roleInfoDmo
	 * @return
	 */
	public Result updateRoleInfo(RoleInfoDmo roleInfoDmo);

    /**
	 * 删除角色
	 * @param con
	 * @return
	 */
	public Result deleteRoleInfo(RoleInfoDmo con);

    /**
     * 根据角色信息查询菜单树
     * @param con
     * @return
     */
    public List<MenuTreeNodeDto> queryMenuTreeByRole(QueryMenuTreeNodeDto con);

    /**
     * 更新角色菜单信息
     * @param roleMenuInfoDto
     * @return
     */
    public Result updateRoleMenuInfo(RoleMenuInfoDto roleMenuInfoDto);

    /**
     * 根据用户Id查询用户角色信息
     * @param userId
     * @return
     */
    public RoleInfoDmo findRoleInfoByUserId(String userId);
}
