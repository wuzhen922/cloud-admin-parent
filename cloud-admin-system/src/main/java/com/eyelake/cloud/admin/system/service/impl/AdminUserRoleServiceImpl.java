package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.eyelake.cloud.admin.system.service.intf.AdminUserRoleService;
import com.eyelake.cloud.common.utils.SqlAssertUtils;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.Result;
import com.yjh.framework.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * admin端用户角色关系服务实现类
 * Created by wunder on 16/9/23 14:09.
 */
@Service("adminUserRoleService")
@ServiceTrace
public class AdminUserRoleServiceImpl implements AdminUserRoleService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public Result insert(AdminRoleDmo adminUserRoleDmo) {

        int i = commonDao.insert(adminUserRoleDmo);

        return SqlAssertUtils.insertAssert(i);
    }

    @Override
    public Result update(AdminRoleDmo con) {

        int i = commonDao.update(con);

        return SqlAssertUtils.updateAssert(i);

    }

    @Override
    public Result delete(AdminRoleDmo con) {

        int i = commonDao.delete(con);

        return SqlAssertUtils.deleteAssert(i);

    }

    @Override
    public AdminRoleDmo selectOne(AdminRoleDmo con) {

        return (AdminRoleDmo)commonDao.selectOne(con);

    }

    @Override
    public Result bindRole(AdminRoleDmo adminUserRoleDmo) {

        Result result = new Result();

        AdminRoleDmo con = new AdminRoleDmo();

        con.setUserId(adminUserRoleDmo.getUserId());

        AdminRoleDmo existUserRoleDmo = selectOne(con);

        if (null != existUserRoleDmo){

            existUserRoleDmo.setCreateTime(DateUtil.getDate());
            existUserRoleDmo.setRoleId(adminUserRoleDmo.getRoleId());

            return update(existUserRoleDmo);

        }

        adminUserRoleDmo.setCreateTime(DateUtil.getDate());

        return insert(adminUserRoleDmo);
    }

}
