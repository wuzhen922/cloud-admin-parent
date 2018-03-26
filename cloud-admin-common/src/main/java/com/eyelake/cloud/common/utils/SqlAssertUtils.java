/**
 * 
 */
package com.eyelake.cloud.common.utils;

import com.yjh.framework.lang.Result;

/**
 * sql操作断言工具类
 */
public class SqlAssertUtils {

	public static Result insertAssert(int i) {

		Result result = new Result();

		if (1 == i) {
			result.setSuccess(true);
		} else {
			result.fail("", "insert dmo failed");
		}

		return result;
	}

	public static Result updateAssert(int i) {

		Result result = new Result();

		if (1 == i) {
			result.setSuccess(true);
		} else {
			result.fail("", "update dmo failed");
		}

		return result;
	}
	
	public static Result deleteAssert(int i) {

		Result result = new Result();

		if (1 == i) {
			result.setSuccess(true);
		} else {
			result.fail("", "delete dmo failed");
		}

		return result;
	}

	public static Result sqlAssert(int i,String message) {

		Result result = new Result();

		if (1 == i) {
			result.setSuccess(true);
		} else {
			result.fail("", message);
		}

		return result;
	}

	public static Result sqlAssert(int i,String errorCode, String message) {

		Result result = new Result();

		if (1 == i) {
			result.setSuccess(true);
		} else {
			result.fail(errorCode, message);
		}

		return result;
	}

}
