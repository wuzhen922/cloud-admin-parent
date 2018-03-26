/**
 * 
 */
package com.eyelake.cloud.admin.framework.common.controller;


import com.eyelake.cloud.admin.system.dto.FirstMenuDto;
import com.eyelake.cloud.admin.assist.dmo.admin.RoleInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryMenuListDto;
import com.eyelake.cloud.admin.system.service.intf.AdminMenuService;
import com.eyelake.cloud.admin.system.service.intf.AdminRoleInfoService;
import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.cloud.common.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页控制类
 * 
 * @author CC
 * 
 */
@Controller
public class MainPageController extends BaseController {

	@Autowired
	private AdminMenuService adminMenuService;

	@Autowired
	private AdminRoleInfoService adminRoleInfoService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest request, Model model) {

		UserSession userSession = getUserSession(request);

		String userId = userSession.getUserId();

		// 通过userId查询菜单
		QueryMenuListDto con = new QueryMenuListDto();

		con.setUserId(Long.parseLong(userId));

		List<FirstMenuDto> menuDtoList = adminMenuService.loadMenuListByUser(con);

		RoleInfoDmo roleInfoDmo = adminRoleInfoService.findRoleInfoByUserId(userId);

		if (null != roleInfoDmo) {
			model.addAttribute("roleName", roleInfoDmo.getRoleName());
		}

		model.addAttribute("menuList", menuDtoList);

		model.addAttribute("userName", userSession.getName());

		return commonView("main");
	}

	@RequestMapping(value = "/managerMain", method = RequestMethod.GET)
	public String managerMain(HttpServletRequest request, Model model) {

		UserSession userSession = getUserSession(request);

		// String userId = userSession.getUserId();

		model.addAttribute("userName", userSession.getName());

		return commonView("managerMain");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model model) {
		UserSession userSession = SessionUtil.getUserSession(request);
		String userSessionStatus =( (userSession== null)? "lostEffective":userSession.toString());
		model.addAttribute("userSessionStatus", userSessionStatus);
		if (null == userSession) {
			return commonView("login");

		}
		String integratorStatus = userSession.getCompanyStatus();
		String integratorId = userSession.getIntegratorId();
		model.addAttribute("integratorStatus", integratorStatus);
		model.addAttribute("integratorId", integratorId);
		return mainView("main_info");

	}

}
