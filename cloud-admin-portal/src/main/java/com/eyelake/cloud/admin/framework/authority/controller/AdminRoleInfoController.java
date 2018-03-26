package com.eyelake.cloud.admin.framework.authority.controller;

import com.alibaba.fastjson.JSON;
import com.eyelake.cloud.admin.assist.dmo.admin.RoleInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.MenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryMenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.RoleMenuInfoDto;
import com.eyelake.cloud.admin.framework.authority.form.QueryRoleInfoForm;
import com.eyelake.cloud.admin.framework.authority.form.RoleInfoForm;
import com.eyelake.cloud.admin.framework.authority.form.UpdateRoleMenuForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.system.service.intf.AdminRoleInfoService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.framework.web.result.JsonResult;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/role")
public class AdminRoleInfoController extends BaseController {

    @Autowired
    private AdminRoleInfoService adminRoleInfoService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return authorityView("role_info");
    }

    /**
     * 查询角色列表
     *
     * @param request
     * @param response
     * @param model
     * @param queryRoleInfoForm
     * @return
     */
    @RequestMapping(value = "/queryRoleList", method = RequestMethod.POST)
    public String queryRoleList(HttpServletRequest request, HttpServletResponse response, Model model, QueryRoleInfoForm queryRoleInfoForm) {

        String roleName = queryRoleInfoForm.getRoleName();

        String pageNum = queryRoleInfoForm.getPageNum();

        String pageSize = queryRoleInfoForm.getPageSize();

        RoleInfoDmo con = new RoleInfoDmo();

        if (StringUtils.isNotBlank(roleName)) {
            con.setRoleName(roleName);
        }

        con.setStatus(Constants.RoleStatus.NORMAL);

        Page page = new Page();

        if (StringUtils.isNotBlank(pageSize)) {
            page.setPageSize(Integer.valueOf(pageSize));
        }

        if (StringUtils.isNotBlank(pageNum)) {
            page.setCurrentPage(Integer.valueOf(pageNum));
        }

        List<RoleInfoDmo> roleInfoList = adminRoleInfoService.selectListByPage(con, page);

        model.addAttribute("roleInfoList", roleInfoList);

        model.addAttribute("page", PageVo.createPageVo(request, page));

        return authorityView("role_table");

    }

    /**
     * 新增角色
     *
     * @param addRoleInfoForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult addRoleInfo(RoleInfoForm addRoleInfoForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        String roleName = addRoleInfoForm.getRoleName();

        if (StringUtils.isBlank(roleName)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        RoleInfoDmo roleInfo = new RoleInfoDmo();

        roleInfo.setRoleName(roleName);
        roleInfo.setDescription(addRoleInfoForm.getDescription());
        roleInfo.setCreateTime(DateUtil.getDate());
        roleInfo.setLastUpdateTime(DateUtil.getDate());
        roleInfo.setStatus(Constants.RoleStatus.NORMAL);

        Result result = adminRoleInfoService.addRoleInfo(roleInfo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }

        return jsonResult;

    }

    /**
     * 修改角色
     *
     * @param updateRoleInfoForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult updateRoleInfo(RoleInfoForm updateRoleInfoForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        if (null == updateRoleInfoForm.getId()) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        RoleInfoDmo roleInfoDmo = new RoleInfoDmo();

        roleInfoDmo.setId(updateRoleInfoForm.getId());
        roleInfoDmo.setRoleName(updateRoleInfoForm.getRoleName());
        roleInfoDmo.setDescription(updateRoleInfoForm.getDescription());
        roleInfoDmo.setLastUpdateTime(DateUtil.getDate());

        Result result = adminRoleInfoService.updateRoleInfo(roleInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }

        return jsonResult;

    }

    /**
     * 删除角色
     *
     * @param deleteRoleInfoForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult deleteRoleInfo(RoleInfoForm deleteRoleInfoForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        RoleInfoDmo roleInfoDmo = new RoleInfoDmo();

        Long id = deleteRoleInfoForm.getId();

        if (null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        roleInfoDmo.setId(id);

        Result result = adminRoleInfoService.deleteRoleInfo(roleInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }

        return jsonResult;

    }

    @RequestMapping(value = "/queryMenuList/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    JsonResult queryMenuListOfRole(HttpServletRequest request, Model model, @PathVariable(value = "id") String id) {

        JsonResult jsonResult = new JsonResult();

        QueryMenuTreeNodeDto con = new QueryMenuTreeNodeDto();

        con.setRoleId(id);
        con.setSubMenuLevel(Constants.MenuLevel.SECOND);

        //选取出所有的菜单
        List<MenuTreeNodeDto> menuList = adminRoleInfoService.queryMenuTreeByRole(con);

        jsonResult.setData(menuList);

        jsonResult.setSuccess(true);

        return jsonResult;
    }

    @RequestMapping(value = "/updateRoleMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult updateRoleMenu(HttpServletRequest request, Model model, UpdateRoleMenuForm roleMenuForm) {

        JsonResult jsonResult = new JsonResult();

        RoleMenuInfoDto roleMenuInfoDto = new RoleMenuInfoDto();

        roleMenuInfoDto.setRoleId(roleMenuForm.getRoleId());

        List<Long> menuIdList = JSON.parseArray(roleMenuForm.getMenuIdList(), Long.class);

        roleMenuInfoDto.setMenuIdList(menuIdList);

        Result result = adminRoleInfoService.updateRoleMenuInfo(roleMenuInfoDto);

        if (!result.isSuccess()) {

            jsonResult.fail(result.getMessage());
            return jsonResult;

        }

        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult queryRole(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        RoleInfoDmo con = new RoleInfoDmo();

        con.setStatus(Constants.RoleStatus.NORMAL);

        con.setId((long) 2);

        List<RoleInfoDmo> roleInfoDmoList = adminRoleInfoService.selectList(con);

        jsonResult.setData(roleInfoDmoList);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

}
