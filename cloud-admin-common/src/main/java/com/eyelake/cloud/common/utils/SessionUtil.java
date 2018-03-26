package com.eyelake.cloud.common.utils;

import com.eyelake.cloud.common.constants.SessionKey;
import com.eyelake.cloud.common.dto.UserSession;
import com.yjh.framework.web.util.SessionHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SessionUtil
 * 
 */
public class SessionUtil extends SessionHelper<UserSession> {
	
	private static final String USER_SESSION_KEY = SessionKey.USER_SESSION;
	
    /**
     * 获取session中用户相关信息
     * @param request
     * @return
     */
    public static UserSession getUserSession(HttpServletRequest request) {
        return (UserSession) getSessionValue(request, USER_SESSION_KEY);
    }

    /**
     * 获取session中用户相关信息
     * @param httpSession
     * @return
     */
    public static UserSession getUserSession(HttpSession httpSession) {
        if (httpSession == null) {
            return null;
        }
        return (UserSession) httpSession.getAttribute(USER_SESSION_KEY);
    }

    /**
     * 设置session中用户相关信息
     * @param request
     * @return
     */
    public static void setUserSession(HttpServletRequest request, UserSession userSession) {
        setSessionValue(request, USER_SESSION_KEY, userSession);
    }

    /**
     * 设置session中用户相关信息
     * @param request
     * @param reNew:是否创建一个新的session
     * @return
     */
    public static void setUserSession(HttpServletRequest request, UserSession userSession, boolean reNew) {
        if (reNew) {
            invalidSession(request);
        }
        setSessionValue(request, USER_SESSION_KEY, userSession);
    }

    /**
     * session失效
     * @param request
     */
    public static void invalidSession(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            removeSessionValue(request, USER_SESSION_KEY);
            httpSession.invalidate();
        }
    }

    /**
     * 是否是同一个session。只针对已登录用户,不相同说明异地登陆
     * @param request
     * @param userSession
     */
//    public static boolean isSameSession(HttpServletRequest request, String userId) {
//        String currentSessionId = request.getSession().getId();
//        String loginSessionId = ApplicationUtil.getLoginSessionId(request, userId);
//        return currentSessionId.equals(loginSessionId);
//    }
}
