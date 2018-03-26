package com.eyelake.cloud.admin.system.service.impl;


import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminLoginDto;
import com.eyelake.cloud.admin.assist.result.AdminLoginResult;
import com.eyelake.cloud.admin.system.service.intf.AdminMemberService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.eyelake.cloud.common.utils.PropertiesUtil;
import com.eyelake.cloud.common.utils.SqlAssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yjh.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.Result;
import com.yjh.framework.utils.DateUtil;

/**
 * admin端会员服务实现类
 *
 * @author wunder
 * @date 16/9/2 21:26
 */
@Service("adminMemberService")
@ServiceTrace
public class AdminMemberServiceImpl implements AdminMemberService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public AdminInfoDmo selectOne(AdminInfoDmo con) {
        return (AdminInfoDmo) commonDao.selectOne(con);
    }

    @Override
    public Result update(AdminInfoDmo con) {

        int i = commonDao.update(con);
        return SqlAssertUtils.updateAssert(i);
    }

    @Override
    public AdminLoginResult login(AdminLoginDto loginDto) {

        AdminLoginResult result = new AdminLoginResult();

        String userName = loginDto.getUserName();

        String password = loginDto.getPassword();

        AdminInfoDmo con = new AdminInfoDmo();

        con.setUserName(userName);
        AdminInfoDmo adminInfoDmo = (AdminInfoDmo) commonDao.selectOne("AdminUserMapper.queryExistingUser",con);

        if (null == adminInfoDmo || !(Constants.AdminMemberStatus.NORMAL.equals(adminInfoDmo.getStatus()) || (Constants.AdminMemberStatus.LOCK.equals(adminInfoDmo.getStatus())))) {
            result.fail("AML-0001", "用户名或者密码错误");
            return result;
        }


        String failedTimesString = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,
                PropertiesConstants.LOGIN_FAILED_LOCK_TIMES);

        //账号连续登录失败锁定次数
        Long maxFailedTimes = Long.parseLong(failedTimesString);

        String lockHoursString = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,
                PropertiesConstants.LOGIN_FAILED_LOCK_HOURS);

        //账号锁定时间
        int lockHours = Integer.parseInt(lockHoursString);

        // 检查登录失败次数是否超过5次并且最后一次登录时间距离现在未到达24小时
        if (adminInfoDmo.getLoginFailCount() >= maxFailedTimes && com.eyelake.framework.utils.DateUtil.addHours(adminInfoDmo.getLastUpdateTime(), lockHours).after(com.eyelake.framework.utils.DateUtil.getDate())) {
            adminInfoDmo.setStatus(Constants.AdminMemberStatus.LOCK);
            adminInfoDmo.setLastUpdateTime(DateUtil.getDate());
            update(adminInfoDmo);
            result.fail("AML-0003", "用户连续登录失败，已被锁定。");
            return result;
        }

        // 通过userName查出记录为已锁定
        if (Constants.AdminMemberStatus.LOCK.equals(adminInfoDmo.getStatus())) {
//            如果时间过了最大锁定时间，解锁用户并重新登陆
            if (!com.eyelake.framework.utils.DateUtil.addHours(adminInfoDmo.getLastUpdateTime(), lockHours).after(com.eyelake.framework.utils.DateUtil.getDate())) {
                processLoginSuccess(adminInfoDmo);
            } else {
                result.fail("AML-0002", "用户已被锁定。");
                return result;
            }
        }

        // 检查密码是否正确
        if (password.equalsIgnoreCase(adminInfoDmo.getPassWord())) {
            result.setAdminInfoDmo(adminInfoDmo);
            processLoginSuccess(adminInfoDmo);

        } else {
            processLoginFailed(adminInfoDmo);
            result.fail("AML-0004", "用户名或者密码不正确");
            return result;
        }

        return result;

    }

    /**
     * 处理登录成功
     *
     * @param adminInfoDmo
     */
    private void processLoginSuccess(AdminInfoDmo adminInfoDmo) {

        if (null == adminInfoDmo || null == adminInfoDmo.getId()) {
            return;
        }
        adminInfoDmo.setStatus(Constants.AdminMemberStatus.NORMAL);
        adminInfoDmo.setLoginFailCount(0L);
        adminInfoDmo.setLastUpdateTime(DateUtil.getDate());

        update(adminInfoDmo);
//todo:加入登陆成功日志
    }

    /**
     * 处理登录失败
     *
     * @param adminInfoDmo
     */
    private void processLoginFailed(AdminInfoDmo adminInfoDmo) {

        if (null == adminInfoDmo || null == adminInfoDmo.getId()) {
            return;
        }

        adminInfoDmo.setLastUpdateTime(DateUtil.getDate());
        adminInfoDmo.setLoginFailCount(adminInfoDmo.getLoginFailCount() + 1);

        update(adminInfoDmo);
    }
}
