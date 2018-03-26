/**
 * 
 */
package com.eyelake.cloud.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台服务错误码
 * 
 * @author CC
 *
 */
public class ServiceErrorCode {
	
	public class Member {
		
		public final static String MEMBER_LOGIN_AUDIT_ERROR = "0001";
		
		public final static String MEMBER_LOGIN_LOCK_ERROR = "0002";
	}
	
	public final static Map<String,String> ERROR_CODE_DESC_MAP = new HashMap<String,String>(){
		private static final long serialVersionUID = -2647343287032722773L;

	    {
		    this.put(Member.MEMBER_LOGIN_AUDIT_ERROR, "用户已注册成功，待后台工作人员审核，请耐心等待1~2个工作日，如有特殊情况，请联系客服人员！");
		    this.put(Member.MEMBER_LOGIN_LOCK_ERROR, "用户连续登录多次失败，已被锁定，请联系客服人员！");
	    }
	};
}
