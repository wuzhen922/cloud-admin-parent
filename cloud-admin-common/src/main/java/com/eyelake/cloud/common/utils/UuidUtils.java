/**
 * 
 */
package com.eyelake.cloud.common.utils;

import java.util.UUID;

/**
 * 32位随机数工具类
 * 
 * @author CC
 *
 */
public class UuidUtils {
	
	public static String generateUUID() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

}
