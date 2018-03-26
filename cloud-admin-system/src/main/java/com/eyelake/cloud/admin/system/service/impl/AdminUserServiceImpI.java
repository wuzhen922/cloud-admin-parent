package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.system.service.intf.AdminRoleInfoService;
import com.eyelake.cloud.admin.system.service.intf.AdminUserRoleService;
import com.eyelake.cloud.admin.system.service.intf.AdminUserService;
import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RoleInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminInfoDto;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.utils.SqlAssertUtils;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.AppException;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * admin端用户管理服务实现类
 * Created by wunder on 16/9/23 14:09.
 */
@Service("adminUserService")
@ServiceTrace
public class AdminUserServiceImpI implements AdminUserService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private AdminRoleInfoService adminRoleInfoService;

    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @Override
    public Result insert(AdminInfoDmo adminInfoDmo) {

        int i = commonDao.insert(adminInfoDmo);

        return SqlAssertUtils.insertAssert(i);

    }

    @Override
    public Result update(AdminInfoDmo con) {

        int i = commonDao.update(con);

        return SqlAssertUtils.updateAssert(i);
    }

    @Override
    public List<AdminInfoDto> selectListByPage(AdminInfoDmo adminInfoDmo, Page page) {

//        adminInfoDmo.setStatus(Constants.AdminMemberStatus.NORMAL);

        return commonDao.selectListByPage("AdminUserMapper.queryUserCount", "AdminUserMapper.queryUserList", adminInfoDmo, page);
   
    }
    

    @Override
    public AdminInfoDmo selectOne(AdminInfoDmo adminInfoDmo) {

        return (AdminInfoDmo) commonDao.selectOne(adminInfoDmo);
    }

    @Override
    @Transactional
    public Result addUser(AdminInfoDmo adminInfoDmo,AdminRoleDmo adminRoleDmo) {

        Result result = new Result();

    	if(null == adminInfoDmo||null == adminRoleDmo)
    	{
    		result.fail("AAU-0001","参数异常");
    		return result;
    	}

//    	AdminInfoDmo dmo = new AdminInfoDmo();
        AdminInfoDmo con1 = new AdminInfoDmo();
        con1.setUserName(adminInfoDmo.getUserName());


//        dmo.setUserName(adminInfoDmo.getUserName());
//    	dmo.setStatus(Constants.AdminMemberStatus.NORMAL);
//    	AdminInfoDmo existAdminInfo = selectOne(dmo);
        AdminInfoDmo existAdminInfo = (AdminInfoDmo) commonDao.selectOne("AdminUserMapper.queryExistingUser",con1);

        if(existAdminInfo != null)
    	{
    		result.fail("","登录名已存在");
    		return result;
    	}

    	RoleInfoDmo con = new RoleInfoDmo();

        con.setId(adminRoleDmo.getRoleId());
        con.setStatus(Constants.RoleStatus.NORMAL);

        RoleInfoDmo existRoleInfo = adminRoleInfoService.selectOne(con);

        if (null == existRoleInfo){
            result.fail("","角色信息不存在");
            return result;
        }

        result = insert(adminInfoDmo);

        if (!result.isSuccess()){
            result.fail("","新增用户失败");
            throw new AppException(result);
        }

        existAdminInfo = selectOne(adminInfoDmo);

        adminRoleDmo.setUserId(existAdminInfo.getId());
        adminRoleDmo.setCreateTime(DateUtil.getDate());

        result = adminUserRoleService.insert(adminRoleDmo);

        if (!result.isSuccess()){
            result.fail("","新增用户失败");
            throw new AppException(result);
        }

        return result;
    }

    @Override
    @Transactional
    public Result updateUser(AdminInfoDmo adminInfoDmo,AdminRoleDmo adminRoleDmo) {

        Result result = new Result();

        if(null == adminInfoDmo||null == adminRoleDmo)
        {
            result.fail("UAU-0001","参数异常");
            return result;
        }

        AdminInfoDmo dmo = new AdminInfoDmo();

        dmo.setUserName(adminInfoDmo.getUserName());

        AdminInfoDmo existAdminInfo = (AdminInfoDmo) commonDao.selectOne("AdminUserMapper.queryExistingUser",dmo);

        if(existAdminInfo != null&&!existAdminInfo.getId().equals( adminInfoDmo.getId()))
        {
            result.fail("UAU-0002","登录名已存在");
            return result;
        }

        RoleInfoDmo con = new RoleInfoDmo();

        con.setId(adminRoleDmo.getRoleId());
        con.setStatus(Constants.RoleStatus.NORMAL);

        RoleInfoDmo existRoleInfo = adminRoleInfoService.selectOne(con);

        if (null == existRoleInfo){
            result.fail("UAU-0003","角色信息不存在");
            return result;
        }

        result = update(adminInfoDmo);

        if (!result.isSuccess()){
            result.fail("UAU-0004","更新用户失败");
            throw new AppException(result);
        }

        AdminRoleDmo adminRoleCon = new AdminRoleDmo();

        adminRoleCon.setUserId(adminInfoDmo.getId());

        AdminRoleDmo existUserRole = adminUserRoleService.selectOne(adminRoleCon);

        if (null == existUserRole){
            adminRoleDmo.setCreateTime(DateUtil.getDate());
            result = adminUserRoleService.insert(adminRoleDmo);
        }else {
            existUserRole.setRoleId(adminRoleDmo.getRoleId());
            result = adminUserRoleService.update(existUserRole);
        }

        if (!result.isSuccess()){
            result.fail("AAU-0005","更新用户失败");
            throw new AppException(result);
        }

        return result;
    }

    @Override
    @Transactional
    public Result deleteUser(AdminInfoDmo adminInfoDmo) {

        Result result;

        adminInfoDmo.setStatus(Constants.AdminMemberStatus.DELETE);

        result = update(adminInfoDmo);

        if (!result.isSuccess()) {
            result.fail("DLU-0001","删除用户失败");
            throw new AppException(result);
        }

        AdminRoleDmo adminUserRoleDmo = new AdminRoleDmo();

        adminUserRoleDmo.setUserId(adminInfoDmo.getId());

        result = adminUserRoleService.delete(adminUserRoleDmo);

        if (!result.isSuccess()) {
            result.fail("DLU-0002","删除用户角色关系失败");
        }

        return result;
    }

    @Override
    public Result unlockUser(AdminInfoDmo adminInfoDmo) {

        Result result = new Result();

        adminInfoDmo.setStatus(Constants.AdminMemberStatus.LOCK);

        AdminInfoDmo existAdminInfoDmo = selectOne(adminInfoDmo);

        if (null == existAdminInfoDmo){

            result.fail("ULU-0001","用户信息不存在");
            return result;

        }

        existAdminInfoDmo.setLoginFailCount(0L);
        existAdminInfoDmo.setStatus(Constants.AdminMemberStatus.NORMAL);
        existAdminInfoDmo.setLastUpdateTime(DateUtil.getDate());


        int i = commonDao.update(existAdminInfoDmo);

        return SqlAssertUtils.updateAssert(i);
    }


}
