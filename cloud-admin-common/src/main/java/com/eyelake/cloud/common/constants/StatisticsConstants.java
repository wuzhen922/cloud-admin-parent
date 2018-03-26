/**
 * 
 */
package com.eyelake.cloud.common.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流量统计使用的常量类
 * 
 *
 */
public class StatisticsConstants {

	//数字5
	public static final int FIVE = 5;

	//数字
	public static final Integer ZERO = 0;

	//字符0
	public static final String ZEROSTR = "0";

	/**
	 * 各表记录状态
	 */
	public class Status {

		/**
		 * 正常
		 */
		public static final String NORMAL = "10";

		/**
		 * 删除
		 */
		public static final String DELETE = "99";
	}

	//每月28天的list
	public static final List<String> TWENTY_EIGHT_LIST = new ArrayList<String>()

	{
		{

			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
			add("12");
			add("13");
			add("14");
			add("15");
			add("16");
			add("17");
			add("18");
			add("19");
			add("20");
			add("21");
			add("22");
			add("23");
			add("24");
			add("25");
			add("26");
			add("27");
			add("28");
		}
	};

	//每月29天的list
	public static final List<String> TWENTY_NINE_LIST = new ArrayList<String>()

	{
		{

			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
			add("12");
			add("13");
			add("14");
			add("15");
			add("16");
			add("17");
			add("18");
			add("19");
			add("20");
			add("21");
			add("22");
			add("23");
			add("24");
			add("25");
			add("26");
			add("27");
			add("28");
			add("29");
		}
	};

	//每月30天的list
	public static final List<String> THIRTY_LIST = new ArrayList<String>()

	{
		{

			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
			add("12");
			add("13");
			add("14");
			add("15");
			add("16");
			add("17");
			add("18");
			add("19");
			add("20");
			add("21");
			add("22");
			add("23");
			add("24");
			add("25");
			add("26");
			add("27");
			add("28");
			add("29");
			add("30");
		}
	};

	//每月31天的list
	public static final List<String> THIRTY_ONE_LIST = new ArrayList<String>()

	{
		{

			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
			add("12");
			add("13");
			add("14");
			add("15");
			add("16");
			add("17");
			add("18");
			add("19");
			add("20");
			add("21");
			add("22");
			add("23");
			add("24");
			add("25");
			add("26");
			add("27");
			add("28");
			add("29");
			add("30");
			add("31");

		}
	};

	//	日历map(每天的)
	public static final Map<Integer, List<String>> DAY_MAP = new HashMap<Integer, List<String>>()
	{
		{

			put(28,TWENTY_EIGHT_LIST);
			put(29,TWENTY_NINE_LIST);
			put(30,THIRTY_LIST);
			put(31,THIRTY_ONE_LIST);

		}
	};



	//每年的12个月份
	public static final List<String> MONTH_OF_YEAR_LIST = new ArrayList<String>()

	{
		{

			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
			add("12");
		}
	};

	//	一年的12个月
	public static final Map<Integer, List<String>> TWELVE_MONTH_MAP = new HashMap<Integer, List<String>>()
	{
		{

			put(0,MONTH_OF_YEAR_LIST);


		}
	};

	//当年中，2月份之前的月份
	public static final List<String> ONE_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
		}
	};

	//当年中，3月份之前的月份
	public static final List<String> TWO_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
		}
	};

	//当年中，4月份之前的月份
	public static final List<String> THREE_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
		}
	};

	//当年中，5月份之前的月份
	public static final List<String> FOUR_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
		}
	};

	//当年中，6月份之前的月份
	public static final List<String> FIVE_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
		}
	};

	//当年中，7月份之前的月份
	public static final List<String> SIX_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
		}
	};

	//当年中，8月份之前的月份
	public static final List<String> SEVEN_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
		}
	};

	//当年中，9月份之前的月份
	public static final List<String> EIGHT_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
		}
	};

	//当年中，10月份之前的月份
	public static final List<String> NINE_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
		}
	};

	//当年中，11月份之前的月份
	public static final List<String> TEN_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
		}
	};

	//当年中，12月份之前的月份
	public static final List<String> ELEVEN_MONTH_LIST = new ArrayList<String>()

	{
		{
			add("01");
			add("02");
			add("03");
			add("04");
			add("05");
			add("06");
			add("07");
			add("08");
			add("09");
			add("10");
			add("11");
		}
	};

	//	年日历map(每月的)
	public static final Map<Integer, List<String>> MONTH_MAP = new HashMap<Integer, List<String>>()
	{
		{
			put(2,ONE_MONTH_LIST);
			put(3,TWO_MONTH_LIST);
			put(4,THREE_MONTH_LIST);
			put(5,FOUR_MONTH_LIST);
			put(6,FIVE_MONTH_LIST);
			put(7,SIX_MONTH_LIST);
			put(8,SEVEN_MONTH_LIST);
			put(9,EIGHT_MONTH_LIST);
			put(10,NINE_MONTH_LIST);
			put(11,TEN_MONTH_LIST);
			put(12,ELEVEN_MONTH_LIST);
		}
	};
}

