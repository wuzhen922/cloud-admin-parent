package com.eyelake.cloud.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 应用工具类
 */
public class ApplicationUtil {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtil.class);

//    public static Map<String,String> getLoginUserMap(HttpServletRequest request){
//        ServletContext application = request.getSession().getServletContext();
//        return getLoginUserMap(application);
//    }
//
//    public static Map<String,String> getLoginUserMap(ServletContext application){
//        return (Map<String, String>) application.getAttribute(ApplicationKey.LOGIN_USER_MAP);
//    }
//
//    public static String getLoginSessionId(HttpServletRequest request,String userId){
//        Map<String, String> loginUserMap = getLoginUserMap(request);
//        if(null == loginUserMap){
//            return null;
//        }else{
//            return loginUserMap.get(userId);
//        }
//    }
//
//    public static Map<String,String> addLoginUser(HttpServletRequest request,String userId){
//        HttpSession httpSession = request.getSession();
//        ServletContext application = httpSession.getServletContext();
//        Map<String,String> loginUserMap = getLoginUserMap(application);
//        if(null == loginUserMap){
//            loginUserMap = new HashMap<String,String>();
//            setAttribute(application,ApplicationKey.LOGIN_USER_MAP,loginUserMap);
//        }
//        loginUserMap.put(userId, httpSession.getId());
//        return loginUserMap;
//    }
//
//    public static Map<String,String> removeUserId(ServletContext application,String userId){
//        Map<String, String> loginUserMap = getLoginUserMap(application);
//        if(null != loginUserMap){
//            loginUserMap.remove(userId);
//        }
//        return loginUserMap;
//    }

    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        setAttribute(request.getSession().getServletContext(), key, value);
    }

    public static synchronized void setAttribute(ServletContext application, String key, Object value) {
        application.setAttribute(key, value);
    }

    public static synchronized void removeAttribute(ServletContext application, String key) {
        application.removeAttribute(key);
    }

}
