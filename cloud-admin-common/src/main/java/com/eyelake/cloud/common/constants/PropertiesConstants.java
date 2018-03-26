package com.eyelake.cloud.common.constants;

/**
 * Properties配置文件
 */
public class PropertiesConstants {

	/**
	 * application-env.properties 配置文件
	 */
	public static final String APPLICATION_ENV_CONFIG = "properties/application-env";

	/**
	 * 连续登录失败锁定次数
	 */
	public static final String LOGIN_FAILED_LOCK_TIMES = "login.failed.lock.times";

	/**
	 * 连续登录失败锁定时间
	 */
	public static final String LOGIN_FAILED_LOCK_HOURS = "login.failed.lock.hours";

	/**
	 * 导入Excel表格缓存目录
	 */
	public static final String EXCEL_IMPORT_PATH = "import.excel.path";

	/**
	 * cadUrl获取
	 */
	public static final String CAD_URL = "cadUrl";

	/**
	 * 报表配置
	 */
	public static final String REPORT_PATH = "report.path";



	/**
	 * 云后台导入rtu文件存放路径
	 */
	public static final String CLOUD_IMPORT_RTUS_PATH = "cloud.import.rtus.path";

	/**
	 * 云后台失败导入rtu文件存放路径
	 */
	public static final String CLOUD_FAIL_EXPORT_URL = "cloud.fail.export.url";


	/**
	 * rtu名称的最大长度
	 */
	public static final String IMPORT_RTU_NAME_MAX_LENGTH = "import.rtu.name.max.length";


	/**
	 * 物联卡API产品名称（短信产品名固定，无需修改）
	 */
	public static final String API_PRODUCT = "api.product";

	/**
	 * 物联卡API产品域名（接口地址固定，无需修改）
	 */
	public static final String API_DOMAIN = "api.domain";

	/**
	 * 你的accessKeyId,参考本文档步骤2
	 */
	public static final String API_ACCESS_KEY_ID = "api.access.key.id";

	/**
	 * 你的accessKeySecret，参考本文档步骤2
	 */
	public static final String API_ACCESS_KEY_SECRET = "api.access.key.secret";



}
