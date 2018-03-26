package com.eyelake.cloud.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 相对位置协议常量 Created by wunder on 2017/4/27 19:46.
 */
public class GravityDataConstants {

	/**
	 * asc码表10进制！号对应字符
	 */
	public static final byte IDENTIFIER1= (byte) 33 ;


	/**
	 * asc码表回车键
	 */
	public static final byte ENDBIT =  (byte) 0xD;
	
	
	/**
	 * PITCH、ROLL 和 HEADING 轴角度命令字
	 */
	public static final byte ALL_ANGLE = (byte) 0x84;

	public class BaudRateValue {

		public static final int BAUD_RATE_2400 = 2400;

		public static final int BAUD_RATE_4800 = 4800;

		public static final int BAUD_RATE_9600 = 9600;

		public static final int BAUD_RATE_19200 = 19200;

		public static final int BAUD_RATE_115200 = 115200;

	}

	public class BaudRateKey {

		public static final byte BAUD_RATE_2400 = 0x00;

		public static final byte BAUD_RATE_4800 = 0x01;

		public static final byte BAUD_RATE_9600 = 0x02;

		public static final byte BAUD_RATE_19200 = 0x03;

		public static final byte BAUD_RATE_115200 = 0x04;

	}

	public static final Map<Byte, Integer> BaudRateKeyValueMap = new HashMap<Byte, Integer>() {

		private static final long serialVersionUID = 6130075606361305935L;
		{
			put(BaudRateKey.BAUD_RATE_2400, BaudRateValue.BAUD_RATE_2400);
			put(BaudRateKey.BAUD_RATE_4800, BaudRateValue.BAUD_RATE_4800);
			put(BaudRateKey.BAUD_RATE_9600, BaudRateValue.BAUD_RATE_9600);
			put(BaudRateKey.BAUD_RATE_19200, BaudRateValue.BAUD_RATE_19200);
			put(BaudRateKey.BAUD_RATE_115200, BaudRateValue.BAUD_RATE_115200);

		}

	};

	public static final Map<Integer, Byte> BaudRateValueKeyMap = new HashMap<Integer, Byte>() {

		private static final long serialVersionUID = 6130075606361305935L;
		{
			put(BaudRateValue.BAUD_RATE_2400, BaudRateKey.BAUD_RATE_2400);
			put(BaudRateValue.BAUD_RATE_4800, BaudRateKey.BAUD_RATE_4800);
			put(BaudRateValue.BAUD_RATE_9600, BaudRateKey.BAUD_RATE_9600);
			put(BaudRateValue.BAUD_RATE_19200, BaudRateKey.BAUD_RATE_19200);
			put(BaudRateValue.BAUD_RATE_115200, BaudRateKey.BAUD_RATE_115200);

		}

	};

}
