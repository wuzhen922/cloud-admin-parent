package com.eyelake.cloud.admin.system.service.intf;

import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminInfoDto;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;

import java.util.List;

/**
 * admin端用户管理服务接口类
 *
 * @author wunder
 * @date 16/9/23 14:09
 */
public interface AdminUserService {


    /**
     * 新增用户
     * @param adminInfoDmo
     * @return
     */
    public Result insert(AdminInfoDmo adminInfoDmo);

    /**
     *
     * @param con
     * @return
     */
    public Result update(AdminInfoDmo con);

    /**
     * 分页查询用户列表
     *
     * @param adminInfoDmo
     * @param page
     * @return
     */
    public List<AdminInfoDto> selectListByPage(AdminInfoDmo adminInfoDmo, Page page);

    /**
     * 查询用户详情
     *
     * @param adminInfoDmo
     * @return
     */

    public AdminInfoDmo selectOne(AdminInfoDmo adminInfoDmo);

    /**
     * 新增用户
     *
     * @param adminInfoDmo
     * @return
     */
    public Result addUser(AdminInfoDmo adminInfoDmo, AdminRoleDmo adminRoleDmo);

    /**
     * 修改用户
     *
     * @param adminInfoDmo
     * @param adminRoleDmo
     * @return
     */
    public Result updateUser(AdminInfoDmo adminInfoDmo, AdminRoleDmo adminRoleDmo);

    /**
     * 删除用户
     *
     * @param adminInfoDmo
     * @return
     */
    public Result deleteUser(AdminInfoDmo adminInfoDmo);

    /**
     * 解锁用户
     *
     * @param adminInfoDmo
     * @return
     */
    public Result unlockUser(AdminInfoDmo adminInfoDmo);

}
