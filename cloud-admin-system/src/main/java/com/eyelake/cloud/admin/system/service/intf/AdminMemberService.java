package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminLoginDto;
import com.eyelake.cloud.admin.assist.result.AdminLoginResult;
import com.yjh.framework.lang.Result;

/**
 * admin端会员服务接口类
 *
 * @author wunder
 * @date 16/9/2 21:25
 */
public interface AdminMemberService {

    public AdminInfoDmo selectOne(AdminInfoDmo con);

    public Result update(AdminInfoDmo con);

    /**
     * 管理员登录
     *
     * @param loginDto
     * @return
     */
    public AdminLoginResult login(AdminLoginDto loginDto);
}








