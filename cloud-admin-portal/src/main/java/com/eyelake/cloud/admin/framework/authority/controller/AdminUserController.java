package com.eyelake.cloud.admin.framework.authority.controller;


import com.eyelake.cloud.admin.assist.dmo.admin.AdminInfoDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminInfoDto;
import com.eyelake.cloud.admin.framework.authority.form.AdminUserForm;
import com.eyelake.cloud.admin.framework.authority.form.BindingRoleForm;
import com.eyelake.cloud.admin.framework.authority.form.ModifyAdminPasswordForm;
import com.eyelake.cloud.admin.framework.authority.form.QueryAdminUserForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.system.service.intf.AdminRoleInfoService;
import com.eyelake.cloud.admin.system.service.intf.AdminUserRoleService;
import com.eyelake.cloud.admin.system.service.intf.AdminUserService;
import com.eyelake.cloud.common.constants.Constants;

import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.framework.web.result.JsonResult;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class AdminUserController extends BaseController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private AdminRoleInfoService adminRoleInfoService;

    @Autowired
    private AdminUserRoleService adminUserRoleService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String userManger(HttpServletRequest request, Model model) {

        return authorityView("admin_info");
    }

    /**
     * 分页查询用户列表
     *
     * @param request
     * @param response
     * @param model
     * @param queryAdminUserForm
     * @return
     */
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST)
    public String queryUserManagement(HttpServletRequest request, HttpServletResponse response, Model model, QueryAdminUserForm queryAdminUserForm) {

        String userName = queryAdminUserForm.getUserName();

        String pageNum = queryAdminUserForm.getPageNum();

        String pageSize = queryAdminUserForm.getPageSize();

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        if (!StringUtils.isBlank(userName)) {
            adminInfoDmo.setUserName(userName);
        }

        Page page = new Page();

        if (!StringUtils.isBlank(pageNum)) {
            page.setCurrentPage(Integer.valueOf(pageNum));
        }

        if (!StringUtils.isBlank(pageSize)) {
            page.setPageSize(Integer.valueOf(pageSize));
        }

        List<AdminInfoDto> adminInfoList = adminUserService.selectListByPage(adminInfoDmo, page);

        model.addAttribute("adminList", adminInfoList);
        model.addAttribute("page", PageVo.createPageVo(request, page));

        return authorityView("admin_table");
    }

    /**
     * 新增用户信息
     *
     * @param adminUserForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult addUserManagement(AdminUserForm adminUserForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        String userName = adminUserForm.getUserName();
        String realName = adminUserForm.getRealName();
        String phone = adminUserForm.getPhone();
        String userDesc = adminUserForm.getUserDesc();
        String email = adminUserForm.getEmail();
        String password = adminUserForm.getPassword();
        Long roleId = adminUserForm.getRoleId();

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        adminInfoDmo.setUserName(userName);
        adminInfoDmo.setPassWord(password);
        adminInfoDmo.setPhone(phone);
        adminInfoDmo.setRealName(realName);
        adminInfoDmo.setEmail(email);
        adminInfoDmo.setUserDesc(userDesc);
        adminInfoDmo.setCreateTime(DateUtil.getDate());
        adminInfoDmo.setLastUpdateTime(DateUtil.getDate());
        adminInfoDmo.setLoginFailCount(0L);
        adminInfoDmo.setStatus(Constants.AdminMemberStatus.NORMAL);

        AdminRoleDmo adminRoleDmo = new AdminRoleDmo();

        adminRoleDmo.setRoleId(roleId);
        adminRoleDmo.setCreateTime(DateUtil.getDate());

        Result result = adminUserService.addUser(adminInfoDmo,adminRoleDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }
        
        return jsonResult;

    }

    /**
     * 修改用户信息
     *
     * @param adminUserForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult updateUserManagement(AdminUserForm adminUserForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        String userName = adminUserForm.getUserName();
        String realName = adminUserForm.getRealName();
        String phone = adminUserForm.getPhone();
        Long id = adminUserForm.getId();
        String userDesc = adminUserForm.getUserDesc();
        String email = adminUserForm.getEmail();
        Long roleId = adminUserForm.getRoleId();

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        if (null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        adminInfoDmo.setId(id);
        adminInfoDmo.setUserName(userName);
        adminInfoDmo.setPhone(phone);
        adminInfoDmo.setRealName(realName);
        adminInfoDmo.setLastUpdateTime(DateUtil.getDate());
        adminInfoDmo.setUserDesc(userDesc);
        adminInfoDmo.setEmail(email);

        AdminRoleDmo adminRoleDmo = new AdminRoleDmo();

        adminRoleDmo.setRoleId(roleId);
        adminRoleDmo.setUserId(id);

        Result result = adminUserService.updateUser(adminInfoDmo,adminRoleDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }
        
        return jsonResult;

    }

    /**
     * 删除用户信息
     *
     * @param adminUserForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult DeleteUserManagement(AdminUserForm adminUserForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long id = adminUserForm.getId();

        if (null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        adminInfoDmo.setId(id);

        Result result = adminUserService.deleteUser(adminInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }
        
        return jsonResult;

    }

    /**
     * 解锁用户
     *
     * @param adminUserForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/unlockUser", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult unlockUser(AdminUserForm adminUserForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long userId = adminUserForm.getId();

        if (null == userId) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        adminInfoDmo.setId(userId);

        Result result = adminUserService.unlockUser(adminInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("解锁用户失败");
        }
        
        return jsonResult;

    }

    /**
     * 绑定角色
     *
     * @param bindingRoleForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/bindingRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult bindingRole(BindingRoleForm bindingRoleForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long userId = bindingRoleForm.getUserId();
        Long roleId = bindingRoleForm.getRoleId();

        if (null == roleId || null == userId) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        AdminRoleDmo adminUserRoleDmo = new AdminRoleDmo();

        adminUserRoleDmo.setRoleId(roleId);
        adminUserRoleDmo.setUserId(userId);

        Result result = adminUserRoleService.bindRole(adminUserRoleDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("绑定角色失败");
        }

        return jsonResult;

    }

    /**
     * 重置管理员密码
     *
     * @param adminUserForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult resetPassword(AdminUserForm adminUserForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long id = adminUserForm.getId();
        String password = adminUserForm.getPassword();

        AdminInfoDmo adminInfoDmo = new AdminInfoDmo();

        if (StringUtils.isBlank(password) || null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        adminInfoDmo.setId(id);
        adminInfoDmo.setPassWord(password);
        adminInfoDmo.setLastUpdateTime(DateUtil.getDate());

        Result result = adminUserService.update(adminInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("重置管理员密码失败");
        }
        
        return jsonResult;

    }

    /**
     * 修改管理员密码
     *
     * @param modifyAdminPasswordForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult modifyPassword(ModifyAdminPasswordForm modifyAdminPasswordForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        UserSession userSession = getUserSession(request);

        Long id = Long.parseLong(userSession.getUserId());

        String oldPassword = modifyAdminPasswordForm.getOldPassword();
        String newPassword = modifyAdminPasswordForm.getNewPassword();

        AdminInfoDmo con = new AdminInfoDmo();

        con.setId(id);
        con.setStatus(Constants.AdminMemberStatus.NORMAL);

        AdminInfoDmo adminInfoDmo = adminUserService.selectOne(con);

        if (null  == adminInfoDmo){

            jsonResult.fail("管理员不存在，修改管理员密码失败");
            return jsonResult;
        }

        if (StringUtils.isBlank(adminInfoDmo.getPassWord())||!adminInfoDmo.getPassWord().equalsIgnoreCase(oldPassword)){

            jsonResult.fail("旧密码错误，修改管理员密码失败");
            return jsonResult;

        }

        adminInfoDmo.setPassWord(newPassword);

        Result result = adminUserService.update(adminInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("修改管理员密码失败");
        }

        return jsonResult;

    }
}
