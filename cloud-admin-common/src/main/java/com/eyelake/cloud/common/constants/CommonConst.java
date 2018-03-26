package com.eyelake.cloud.common.constants;

/**
 * 全局常量类
 */
public class CommonConst {

	/**
	 * 数据操作影响行数
	 */
	public static final int ZERO = 0;

	/**
	 * 数字1
	 */
	public static final int ONE = 1;

	/**
	 * 默认编码
	 */
	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 失败的提示信息。request.setAttribute(AssistConst.GLOBAL_FAILED_MSG, failedMsg);
	 */
	public static final String RESULT_MSG = "_result_msg";

	/**
	 * Get请求
	 */
	public static final String REQUEST_METHOD_GET = "GET";

	/**
	 * POST请求
	 */
	public static final String REQUEST_METHOD_POST = "POST";

	/**
	 * ftl路径
	 */
	public static final String BASE_VIEW_PATH = "screen";

	public static final String DOMAIN_SYSTEM = "system";

	public static final String DOMAIN_INTEGRATOR= "integrator";

    public static final String DOMAIN_RTU= "rtu";

	public static final String DOMAIN_APPLY = "apply";

	public static final String REAL_DATA = "realdata";

	public static final String DOMAIN_MEMBER = "member";

	public static final String DOMAIN_BASE = "base";

	public static final String DOMAIN_COMMON = "common";

	public static final String DOMAIN_STAFF = "staff";

	public static final String DOMAIN_RECORD = "record";

	public static final String DOMAIN_FLOW= "flow";
	public static final String DOMAIN_MAIN= "main";

	public static final String DOMAIN_STATISTICS = "statistics";

	public static final String DOMAIN_ACCOUNT= "account";


	public static final String DOMAIN_AUTHORITY = "authority";

	public static final String DOMAIN_DATA = "data";

	public static final String DOMAIN_WARN = "warn";

	public static final String DOMAIN_TAKE = "take";

	public static final String QUARTZ_TIMER_TASK_OPEN = "1";

	public static final String QUARTZ_TIMER_TASK_CLOSE = "0";



	/**
	 * 导入读取文件从第几行读取
	 */
	public static final int CLOUD_IMPORT_READ_ROW_NUMBER = 5;

}