package com.eyelake.cloud.common.constants;

import com.yjh.framework.lang.Description;


/**
 * System领域程返回码
 * 
 */
public class SystemResultCode {

    /************************* 全局错误码 start *************************/
    /** 以下提示信息可能会在所有页面显示，尽量减少字数，以免造成样式错乱 */
    public static final String GLOBAL_ERROR                        = "_GE_";
    
    /** 需要跳转到登录页面的错误码 */
    public static final String GLOBAL_ERROR_LOGIN                  = GLOBAL_ERROR + "LOGIN_";

    /** 确定后，需要url页面跳转的错误码 */
    public static final String GLOBAL_ERROR_URL                    = GLOBAL_ERROR + "URL_";

    /** 简单提示信息的错误码 */
    public static final String GLOBAL_ERROR_TIP                    = GLOBAL_ERROR + "TIP_";

    @Description("用户已失效！")
    public static final String GLOBAL_ERROR_USER_SESSION_OUT_TIME  = GLOBAL_ERROR_LOGIN + "OUT_TIME";
    
    @Description("您的账号已在其它地方登录！")
    public static final String GLOBAL_ERROR_DUPLICATE_ENTRIES      = GLOBAL_ERROR_LOGIN + "DUPLICATE_ENTRIES";

    @Description("您没有该操作的权限！")
    public static final String GLOBAL_ERROR_NO_PERMISSION          = GLOBAL_ERROR_TIP + "NO_PERMISSION";

    @Description("非法访问！")
    public static final String GLOBAL_ERROR_ILLEGAL_CHARACTERS     = GLOBAL_ERROR_TIP + "ILLEGAL";
    
    @Description("抱歉！请求处理失败，请稍后重试")
    public static final String GLOBAL_ERROR_SYSTEM_ERROR             = GLOBAL_ERROR_TIP + "SYSTEM_ERROR";
    
    /**用户不存在，返回"用户名或密码错误"*/
    @Description("用户名或密码错误")
    public static final String USER_NOT_EXIST = "USER_NOT_EXIST";
    
    @Description("用户名或密码错误")
    public static final String PASSWORD_ERROR = "PASSWORD_ERROR";
    
    /**用户状态不正确，返回"用户名或密码错误"*/
    @Description("用户名或密码错误")
    public static final String USER_STATE_UNKNOWN = "USER_STATE_UNKNOWN";
    
    @Description("抱歉，用户已停用")
    public static final String USER_DISABLE = "USER_DISABLE";
    
    
}
