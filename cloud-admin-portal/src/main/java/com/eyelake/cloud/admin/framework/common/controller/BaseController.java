/**
 *
 */
package com.eyelake.cloud.admin.framework.common.controller;

import com.eyelake.cloud.common.constants.CommonConst;
import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.cloud.common.utils.SessionUtil;
import com.yjh.framework.lang.Result;
import com.yjh.framework.web.result.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Gov服务的父控制类
 */
public class BaseController {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected JsonResult toJsonResult(JsonResult jsonResult, Result result, String... params) {

		jsonResult.setSuccess(result.isSuccess());
		if (null == params || params.length < 1) {
			params = result.getArgs();
		}
		String message = "";
		jsonResult.setMessage(message);
		jsonResult.setCode(result.getErrorCode());
		return jsonResult;
	}

	protected boolean checkUserSession(HttpServletRequest request, HttpServletResponse response) {

		// 用户session校验
		UserSession userSession = SessionUtil.getUserSession(request);

		return null == userSession;

	}

	protected JsonResult toJsonResult(Result result, String... params) {
		return toJsonResult(new JsonResult(), result, params);
	}

	/**
	 * Direct to system pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String systemView(String ftl) {
		return view(CommonConst.DOMAIN_SYSTEM, ftl);
	}

	/**
	 * 用户管理
	 * @param ftl
	 * @return
	 */
	protected String flowView(String ftl) {
		return view(CommonConst.DOMAIN_FLOW, ftl);
	}
	protected String mainView(String ftl) {
		return view(CommonConst.DOMAIN_MAIN, ftl);
	}
	/**
	 * Direct to integrator pages.
	 *
	 * @param ftl
	 * @return
	 */
	protected String integratorView(String ftl) {
		return view(CommonConst.DOMAIN_INTEGRATOR, ftl);
	}

    /**
     * Direct to RTU pages.
     *
     * @param ftl
     * @return
     */
    protected String rtuView(String ftl) {
        return view(CommonConst.DOMAIN_RTU, ftl);
    }

	/**
	 * Direct to system pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String applyView(String ftl) {
		return view(CommonConst.DOMAIN_APPLY, ftl);
	}

	/**
	 * Direct to system pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String realData(String ftl) {
		return view(CommonConst.REAL_DATA, ftl);
	}

	/**
	 * Direct to data pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String dataView(String ftl) {
		return view(CommonConst.DOMAIN_DATA, ftl);
	}

	/**
	 * Direct to warn pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String warnView(String ftl) {
		return view(CommonConst.DOMAIN_WARN, ftl);
	}

	/**
	 * Direct to base pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String baseView(String ftl) {
		return view(CommonConst.DOMAIN_BASE, ftl);
	}

	/**
	 * Direct to staff pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String staffView(String ftl) {
		return view(CommonConst.DOMAIN_STAFF, ftl);
	}

	/**
	 * Direct to record pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String recordView(String ftl) {
		return view(CommonConst.DOMAIN_RECORD, ftl);
	}

	/**
	 * Direct to statistics pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String statisticsView(String ftl) {
		return view(CommonConst.DOMAIN_STATISTICS, ftl);
	}

	/**
	 * 流量统计页面
	 * @param ftl
	 * @return
	 */
	protected String accountView(String ftl) {
		return view(CommonConst.DOMAIN_ACCOUNT, ftl);
	}

	/**
	 * Direct to authority pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String authorityView(String ftl) {
		return view(CommonConst.DOMAIN_AUTHORITY, ftl);
	}

	/**
	 * Direct to common pages.
	 * 
	 * @param ftl
	 * @return
	 */
	protected String commonView(String ftl) {
		return view(CommonConst.DOMAIN_COMMON, ftl);
	}

	/**
	 * 重定向
	 */
	protected String redirect(String redirectUrl) {
		return "redirect:" + redirectUrl;
	}

	/**
	 * 重定向
	 */
	protected String forword(String forwordUrl) {
		return "forward:" + forwordUrl;
	}


	private String view(String domain, String ftl) {
		return CommonConst.BASE_VIEW_PATH.concat("/").concat(domain).concat("/").concat(ftl);
	}

	/**
	 * 获取session中用户相关信息
	 * 
	 * @param request
	 * @return
	 */
	protected UserSession getUserSession(HttpServletRequest request) {
		return SessionUtil.getUserSession(request);
	}

	/**
	 * 获取session中用户相关信息
	 * 
	 * @param request
	 * @return
	 */
	protected String getCurrentUserId(HttpServletRequest request) {
		return SessionUtil.getUserSession(request).getUserId();
	}

	/**
	 * 获取session中用户相关信息
	 * 
	 * @param request
	 * @return
	 */
	protected String getCurrentUserName(HttpServletRequest request) {
		return SessionUtil.getUserSession(request).getName();
	}
}
