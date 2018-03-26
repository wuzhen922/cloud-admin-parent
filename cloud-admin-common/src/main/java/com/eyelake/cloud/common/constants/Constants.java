package com.eyelake.cloud.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务常量类 Created by wunder on 2016/10/13 23:38.
 */
public class Constants {

	/**
	 * admin会员状态
	 */
	public class AdminMemberStatus {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 锁定
		 */
		public static final String LOCK = "20";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";

	}

	/**
	 * 菜单状态
	 */
	public class MenuStatus {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";
	}

	/**
	 * 用户类型
	 */
	public class ManagerType {

		/**
		 * 超级用户
		 */
		public static final String SUPER_USER = "00";

		/**
		 * 普通用户
		 */
		public static final String NORMAL_USER = "01";
	}

	/**
	 * 菜单等级
	 */
	public class MenuLevel {

		/**
		 * 根级菜单
		 */
		public static final String ROOT = "0";

		/**
		 * 一级菜单
		 */
		public static final String FIRST = "1";

		/**
		 * 二级菜单
		 */
		public static final String SECOND = "2";

		/**
		 * 三级菜单
		 */
		public static final String THIRD = "3";
	}

	/**
	 * 系统模块
	 */
	public class SystemModel {

		public static final String ADMIN = "1";
	}

	/**
	 * 公告状态
	 */
	public class NoticeStatus {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";

	}

	/**
	 * 用户状态
	 */
	public class ManagerStatus {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";

		/**
		 * 锁定
		 */
		public static final String LOCK = "20";

	}

	/**
	 * 角色状态
	 */
	public class RoleStatus {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";
	}

	/**
	 * responseResultCode
	 */
	public class ResponseResultCode {

		/**
		 * 成功
		 */
		public static final String SUCCESS = "0000";

		/**
		 * 失败
		 */
		public static final String FAIL = "0001";

		/**
		 * 系统运行报错
		 */
		public static final String ERROR = "E9999";

		public static final String LOGIN_SESSION_INVALID = "Login-Reset-0000";

	}

	/**
	 * 状态
	 */
	public class CommonStatus {
		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";

	}

	/**
	 * 省市等级
	 */
	public class AreaLevel {

		/**
		 * 省
		 */
		public static final String PROVINCE = "1";

		/**
		 * 地级市
		 */
		public static final String CITY = "2";

		/**
		 * 区/县
		 */
		public static final String COUNTRY = "3";

	}

	/**
	 * 红区状态
	 */
	public class RedAreaStatus {
		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";

	}

	/**
	 * 红区类型
	 */
	public class RedAreaType {
		/**
		 * 大红区
		 */
		public static final String LARGE = "00";

		/**
		 * 小红区
		 */
		public static final String SMALL = "01";

	}

	/**
	 * 信标状态
	 */
	public class BeaconStatus {
		/**
		 * 正常使用
		 */
		public static final String NORMAL = "10";

		/**
		 * 故障
		 */
		public static final String FAILURE = "20";

		/**
		 * 停止使用
		 */
		public static final String STOP = "30";

	}

	/**
	 * 组成红区的点至少3个
	 */
	public static class NumberOfRedAreaPoint {
		/**
		 * 3个点
		 */
		public static final Integer THREE = 3;

	}

	/**
	 * 红绿区区别
	 */
	public class RedGreenType {

		/**
		 * 红区10
		 */
		public static final String RED_TYPE = "10";

		/**
		 * 绿区20
		 */
		public static final String GREEN_TYPE = "20";
	}

    /**
     * 集成商状态
     */
    public class IntegratorStatus {

        /**
         * 正常
         */
        public static final String NORMAL = "10";

        /**
         * 删除
         */
        public static final String DELETE = "99";

        /**
         * 锁定
         */
        public static final String DISABLED = "20";

    }
    /**
     * RTU状态
     */
    public class RtuStatus {

        /**
         * 正常
         */
        public static final String NORMAL = "10";

        /**
         * 删除
         */
        public static final String DELETE = "99";

        /**
         * 禁用
         */
        public static final String DISABLED = "20";

		/**
		 * 未激活
		 */
		public static final String INACTIVE = "00";

	}

    /**
     * 网关预警状态
     */
    public class RtuWarningStatus {

        /**
         * 正常
         */
        public static final String NORMAL = "00";

        /**
         * 流量预警
         */
        public static final String WARNING = "01";

        /**
         * 超出套餐
         */
        public static final String EXHAUST = "02";
    }
	/**
	 * sensor状态
     */
    public class SensorStatus {

        /**
         * 正常
         */
        public static final String NORMAL = "10";

        /**
         * 删除
         */
        public static final String DELETE = "99";

        /**
         * 禁用
         */
        public static final String DISABLED = "20";

    }
    /**
     * 流量表状态
     */
    public class TrafficStatus {

        /**
         * 正常
         */
        public static final String NORMAL = "10";

        /**
         * 删除
         */
        public static final String DELETE = "99";


    }
    //    rtu导入常量
    public class RtuImport {

        public static final String RTU_NAME = "网关名称";

        public static final String RTU_MODEL = "网关型号";

        public static final String RTU_TRANS_TYPE = "网关传输类型";

        public static final String RTU_ACCESS_TYPE = "网关接入类型";

        public static final String SN_NUMBER = "网关序列码";

        public static final String RTU_MACHINE_CODE = "网关机器码";

        public static final String RTU_OPERATOR = "运营商编号";

        public static final String INTEGRATOR_ACCESS_CODE = "集成商入网许可号";

        public static final String FAIL_REASON = "失败原因";

    }

    //	rtu传输类型
    public static final Map<String, String> RTU_TRANS_TYPE_MAP = new HashMap<String, String>() {
        {

            put("4G","00");
            put("NB-IoT","10");
            put("北斗","20");
            put("天通","30");

        }
    };

    //	rtu接入类型
    public static final Map<String, String> RTU_ACCESS_TYPE_MAP = new HashMap<String, String>() {
        {

            put("多通道","10");
            put("单通道","99");

        }
    };

    public class OperatorEnum {

        public static final String CHINA_MOBILE = "00";

        public static final String CHINA_UNICOM = "01";

        public static final String CHINA_TELECOM = "03";
    }



	//流量预警表状态
	public class RtuWarningTableStatus{
		/**
		 * 正常
		 */
		public static final String NORMAL = "10";


		public static final String DELETE = "99";
	}

	//流量预警状态
	public class FlowWarningStatus{
		/**
		 * 正常
		 */
		public static final String NORMAL = "00";
		public static final String WARNING = "01";

		public static final String BEYOND = "02";
	}

}
