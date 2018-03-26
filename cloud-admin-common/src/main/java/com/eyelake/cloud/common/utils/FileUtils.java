/**
 *
 */
package com.eyelake.cloud.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 文件工具类
 *
 * @author tiny zhu
 */
public class FileUtils {

    /**
     * 文件头文件类型校验
     *
     * @param inputStream
     * @param validType
     * @return
     */
    public static boolean validateFileType(InputStream inputStream, String validType) {

        if (null == inputStream) {
            return false;
        }

        if (!StringUtils.isNotEmpty(validType)) {
            return false;
        }

        byte[] data = new byte[10];

        try {
            inputStream.read(data);

            return validateFileTypeByte(data, validType);
        } catch (IOException e) {
            return false;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 文件字节中文件头类型校验
     *
     * @param data
     * @param validType
     * @return
     */
    public static boolean validateFileTypeByte(byte[] data, String validType) {
        if (!StringUtils.isNotEmpty(validType)) {
            return false;
        }

        if (null == data || 0 == data.length) {
            return false;
        }

        byte[] newData = new byte[10];

        if (10 < data.length) {
            newData = Arrays.copyOf(data, 10);
        } else {
            newData = Arrays.copyOf(data, data.length);
        }

        String datastr = binaryToHexString(newData);

        String[] validTypeArray = validType.split(";");

        for (String type : validTypeArray) {
            String hexType = getHexType(type);
            if (datastr.startsWith(hexType)) {
                return true;
            }
        }

        return false;

    }

    private static String getHexType(String type) {
        if ("jpg".equals(type) || "jpeg".equals(type)) {
            return "FFD8FF";
        } else if ("png".equals(type)) {
            return "89504E47";
        } else if ("gif".equals(type)) {
            return "47494638";
        } else if ("bmp".equals(type)) {
            return "424D";
        } else if ("pdf".equals(type)) {
            return "255044462D312E";
        } else if ("doc".equals(type)) {
            return "D0CF11E0";
        } else if ("docx".equals(type)) {
            return "504B0304";
        } else {
            return null;
        }

    }

    /**
     * 将二进制转换为十六进制字符
     *
     * @param bytes
     * @return 将二进制转换为十六进制字符输出
     */
    public static String binaryToHexString(byte[] bytes) {
        String hexStr = "0123456789ABCDEF";
        String result = "";
        String hex = "";
        for (int i = 0; i < bytes.length; i++) {
            // 字节高4位
            hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
            // 字节低4位
            hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
            result += hex;
        }
        return result;
    }
}
