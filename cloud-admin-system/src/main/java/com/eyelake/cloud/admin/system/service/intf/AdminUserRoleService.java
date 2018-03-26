package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.yjh.framework.lang.Result;

/**
 * admin端用户角色关系服务接口类
 *
 * @author wunder
 * @date 16/9/23 14:05
 */
public interface AdminUserRoleService {

    /**
     * 新增用户角色关系信息
     * @param adminUserRoleDmo
     * @return
     */
    public Result insert(AdminRoleDmo adminUserRoleDmo);

    /**
     * 更新用户角色关系信息
     * @param con
     * @return
     */
    public Result update(AdminRoleDmo con);

    /**
     * 删除用户角色关系信息
     * @param con
     * @return
     */
    public Result delete(AdminRoleDmo con);

    /**
     * 查找用户角色关系信息
     * @param con
     * @return
     */
    public AdminRoleDmo selectOne(AdminRoleDmo con);

    /**
     * 用户绑定角色
     * @param adminUserRoleDmo
     * @return
     */
    public Result bindRole(AdminRoleDmo adminUserRoleDmo);

}
