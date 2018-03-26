package com.eyelake.cloud.admin.framework.common.filter;

import com.eyelake.cloud.common.constants.CommonConst;
import com.eyelake.cloud.common.constants.SessionKey;
import com.eyelake.cloud.common.constants.SystemResultCode;
import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.cloud.common.utils.RegexUtil;
import com.eyelake.cloud.common.utils.SessionUtil;
import com.yjh.framework.web.filter.BaseFilter;
import com.yjh.framework.web.result.JsonResult;
import com.yjh.framework.web.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * WebFilter
 * 
 * @author
 */
public class WebFilter extends BaseFilter {

	private static final Logger logger = LoggerFactory.getLogger(WebFilter.class);

	private static final Logger securityLogger = LoggerFactory.getLogger("Security");

	/**
	 * 是否需要用户鉴权
	 */
	private boolean authorizeCheck = true;

	public boolean requestFilter(HttpServletRequest request, HttpServletResponse response, String currentURL)
			throws IOException, ServletException {

		// if (false) {
		// if (!illegalCharactersValidate(currentURL, request)) {
		// return filterFailed(request, response,
		// SystemResultCode.GLOBAL_ERROR_ILLEGAL_CHARACTERS);
		// }
		// }

		// 是否是任意用户可以访问的白名单
		if (isWhiteUrl(currentURL)) {
			return true;
		}

		// 检查用户是否登录
		UserSession userSession = SessionUtil.getUserSession(request);
		if (null == userSession) {
			return filterFailed(request, response, SystemResultCode.GLOBAL_ERROR_USER_SESSION_OUT_TIME);
		}

		// 判断该用户是否有该URL的访问权限
		// if (!hasUrlPermission(request, currentURL)) {
		// if (logger.isDebugEnabled()) {
		// logger.debug("filterFailed.[{},{}].", currentURL,
		// SystemResultCode.GLOBAL_ERROR_NO_PERMISSION);
		// }
		// return filterFailed(request, response,
		// SystemResultCode.GLOBAL_ERROR_NO_PERMISSION);
		// }

		return true;
	}

	/**
	 * 过滤校验失败跳转
	 * 
	 * @param request
	 * @param response
	 * @param filterFailedCode
	 * @return boolean [返回类型说明]
	 * @throws IOException
	 * @throws ServletException
	 *             [参数说明]
	 * @throws throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public static boolean filterFailed(HttpServletRequest request, HttpServletResponse response, String filterFailedCode)
			throws IOException, ServletException {

		String failedMsg = "用户已失效，请重新登录！";

		if (WebUtil.isAjaxRequest(request)) {
			JsonResult jsonResult = new JsonResult(false);
			jsonResult.result(filterFailedCode, failedMsg);
			WebUtil.writeJson(response, jsonResult);
		} else {
			String requestMethod = request.getMethod();
			if (CommonConst.REQUEST_METHOD_GET.equals(requestMethod)
					&& filterFailedCode.startsWith(SystemResultCode.GLOBAL_ERROR_USER_SESSION_OUT_TIME)) {
				String dispatcherUrl = request.getContextPath() + "/login";
				response.sendRedirect(dispatcherUrl);
			} else {
				String dispatcherUrl = null;
				if (SystemResultCode.GLOBAL_ERROR_ILLEGAL_CHARACTERS.equals(filterFailedCode)
						|| SystemResultCode.GLOBAL_ERROR_NO_PERMISSION.equals(filterFailedCode)) {
					dispatcherUrl = "/404";// TODO
				} else {
					dispatcherUrl = "/404";
				}
				request.setAttribute(CommonConst.RESULT_MSG, failedMsg);
				((HttpServletRequest) request).getRequestDispatcher(dispatcherUrl).forward(request, response);
			}
		}
		return false;
	}

	/**
	 * 特殊字符过滤
	 * 
	 * @param currentURL
	 * @param request
	 * @return
	 */
	public boolean illegalCharactersValidate(String currentURL, HttpServletRequest request) {

		Map<String, String[]> allParameter = request.getParameterMap();
		if (null == allParameter || allParameter.isEmpty()) {
			return true;
		}

		for (Map.Entry<String, String[]> entry : allParameter.entrySet()) {
			String key = entry.getKey().toUpperCase();
			if (RegexUtil.isIllegalCharacters(key)) {
				securityLogger.info("Illegal character access,string={}", key);
				return false;
			}
			String[] values = entry.getValue();
			if (null == values || values.length < 1) {
				continue;
			}

			for (String val : values) {
				if (StringUtils.isEmpty(val)) {
					continue;
				}
				if (RegexUtil.isIllegalCharacters(val)) {
					securityLogger.info("Illegal character access,string={}", val);
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断当前用户是否有该url访问的权限
	 * 
	 * @param request
	 * @param currentURL
	 * @return
	 */
	private boolean hasUrlPermission(HttpServletRequest request, String currentURL) {
		// 判断该用户是否有该URL的访问权限
		List<String> userUrlAuthorizeInfo = (List<String>) SessionUtil.getSessionValue(request,
				SessionKey.AUTHROIZE_INFO_URL);
		if (null == userUrlAuthorizeInfo || userUrlAuthorizeInfo.isEmpty()) {
			return false;
		}

		for (String authorizeUrl : userUrlAuthorizeInfo) {
			if (RegexUtil.urlMatch(authorizeUrl, currentURL)) {
				return true;
			}
		}
		return false;
	}

	private boolean isWhiteUrl(String currentURL) {
		// 目前只有/login是白名单，其它页面都需要登录才能访问。暂时路径写死，后续改为可配置
		if ("/login".equals(currentURL) || "/logout".equals(currentURL) || "/404".equals(currentURL)
				|| "/500".equals(currentURL) || "/loginSubmit".equals(currentURL) || currentURL.contains("/image/view")) {
			return true;
		}
		return false;
	}
}
