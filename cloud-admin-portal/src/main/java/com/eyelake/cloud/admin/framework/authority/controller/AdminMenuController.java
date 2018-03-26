package com.eyelake.cloud.admin.framework.authority.controller;


import com.eyelake.cloud.admin.assist.dmo.admin.MenuInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminMenuDto;
import com.eyelake.cloud.admin.framework.authority.form.MenuInfoForm;
import com.eyelake.cloud.admin.framework.authority.form.QueryMenuForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.system.service.intf.AdminMenuService;
import com.eyelake.cloud.common.constants.Constants;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import com.eyelake.framework.web.result.JsonResult;
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
@RequestMapping(value = "/menu")
public class AdminMenuController extends BaseController {

    @Autowired
    private AdminMenuService adminMenuService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return authorityView("menu_info");
    }

    /**
     * 分页查询一级菜单列表
     *
     * @param request
     * @param response
     * @param model
     * @param queryMenuForm
     * @return
     */
    @RequestMapping(value = "/queryFirstMenuListByPage", method = RequestMethod.POST)
    public String queryFirstMenuListByPage(HttpServletRequest request, HttpServletResponse response, Model model, QueryMenuForm queryMenuForm) {

        MenuInfoDmo con = new MenuInfoDmo();

        con.setStatus(Constants.MenuStatus.NORMAL);
        con.setMenuLevel(Constants.MenuLevel.FIRST);

        Page page = new Page();

        page.setCurrentPage(queryMenuForm.getPageNum());
        page.setPageSize(queryMenuForm.getPageSize());

        List<AdminMenuDto> adminMenuDtoList = adminMenuService.selectListByPage(con, page);

        model.addAttribute("menuList", adminMenuDtoList);
        model.addAttribute("page", PageVo.createPageVo(request, page));
        model.addAttribute("pageHandler","firstLevelMenuPageHandler");

        return authorityView("menu_table");

    }

    /**
     * 分页查询二级菜单列表
     *
     * @param request
     * @param response
     * @param model
     * @param queryMenuForm
     * @return
     */
    @RequestMapping(value = "/querySecondMenuListByPage", method = RequestMethod.POST)
    public String querySecondMenuListByPage(HttpServletRequest request, HttpServletResponse response, Model model, QueryMenuForm queryMenuForm) {

        MenuInfoDmo con = new MenuInfoDmo();

        con.setStatus(Constants.MenuStatus.NORMAL);
        con.setMenuLevel(Constants.MenuLevel.SECOND);
        con.setParentId(queryMenuForm.getParentId());

        Page page = new Page();

        page.setCurrentPage(queryMenuForm.getPageNum());
        page.setPageSize(queryMenuForm.getPageSize());

        List<AdminMenuDto> adminMenuDtoList = adminMenuService.selectListByPage(con, page);

        model.addAttribute("menuList", adminMenuDtoList);
        model.addAttribute("page", PageVo.createPageVo(request, page));
        model.addAttribute("pageHandler","secondLevelMenuPageHandler");

        return authorityView("menu_table");

    }

    /**
     * 查询菜单列表
     *
     * @param menuLevel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryMenuList", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult queryMenuList(String menuLevel, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        MenuInfoDmo menuInfoDmo = new MenuInfoDmo();

        if (Constants.MenuLevel.SECOND.equals(menuLevel)) {
            menuInfoDmo.setMenuLevel(Constants.MenuLevel.FIRST);
        }
        if (Constants.MenuLevel.THIRD.equals(menuLevel)) {
            menuInfoDmo.setMenuLevel(Constants.MenuLevel.SECOND);
        }

        menuInfoDmo.setStatus(Constants.MenuStatus.NORMAL);

        List<MenuInfoDmo> menuInfoDmoList = adminMenuService.selectList(menuInfoDmo);

        jsonResult.setSuccess(true);
        jsonResult.setData(menuInfoDmoList);

        return jsonResult;

    }

    /**
     * 新增主菜单
     *
     * @param addMenuForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult addMenu(MenuInfoForm addMenuForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        String menuLevel = addMenuForm.getMenuLevel();
        String menuName = addMenuForm.getMenuName();
        String menuUrl = addMenuForm.getMenuUrl();
        Long parentMenu = addMenuForm.getParentId();
        Long sort = addMenuForm.getSort();
        String menuIcon = addMenuForm.getMenuIcon();

        MenuInfoDmo menuInfoDmo = new MenuInfoDmo();

        menuInfoDmo.setMenuLevel(menuLevel);
        menuInfoDmo.setMenuName(menuName);
        menuInfoDmo.setMenuUrl(menuUrl);
        menuInfoDmo.setMenuIcon(menuIcon);
        menuInfoDmo.setStatus(Constants.MenuStatus.NORMAL);
        if (Constants.MenuLevel.FIRST.equals(menuLevel)) {
            menuInfoDmo.setParentId(1L);
        } else {
            menuInfoDmo.setParentId(parentMenu);
        }
        menuInfoDmo.setSort(sort);
        menuInfoDmo.setCreateTime(DateUtil.getDate());
        menuInfoDmo.setLastUpdateTime(DateUtil.getDate());

        Result result = adminMenuService.addMenu(menuInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("新增主菜单失败");
        }

        return jsonResult;

    }

    /**
     * 修改菜单
     *
     * @param updateMenuForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult updateMenu(MenuInfoForm updateMenuForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long id = updateMenuForm.getId();
        String menuName = updateMenuForm.getMenuName();
        String menuUrl = updateMenuForm.getMenuUrl();
        String menuLevel = updateMenuForm.getMenuLevel();
        Long parentMenu = updateMenuForm.getParentId();
        Long sort = updateMenuForm.getSort();
        String menuIcon = updateMenuForm.getMenuIcon();

        if (null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        MenuInfoDmo menuInfoDmo = new MenuInfoDmo();

        menuInfoDmo.setMenuName(menuName);
        menuInfoDmo.setMenuUrl(menuUrl);
        if (StringUtils.isNotBlank(menuLevel)){
            menuInfoDmo.setMenuLevel(menuLevel);
        }
        menuInfoDmo.setParentId(parentMenu);
        menuInfoDmo.setId(id);
        menuInfoDmo.setSort(sort);
        menuInfoDmo.setLastUpdateTime(DateUtil.getDate());
        menuInfoDmo.setMenuIcon(menuIcon);

        Result result = adminMenuService.updateMenu(menuInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail("修改菜单失败");
        }

        return jsonResult;

    }

    /**
     * 删除菜单
     *
     * @param deleteMenuForm
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult deleteMenu(MenuInfoForm deleteMenuForm, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        Long id = deleteMenuForm.getId();

        if (null == id) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        MenuInfoDmo menuInfoDmo = new MenuInfoDmo();

        menuInfoDmo.setId(id);

        Result result = adminMenuService.deleteMenu(menuInfoDmo);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getErrorCode());
        }

        return jsonResult;

    }


}
