package com.eyelake.cloud.common.utils;

import com.yjh.framework.utils.DateUtil;

import java.util.Random;

/**
 * 基准站 和 监测站 number生成工具类
 * Created by cc on 2017/1/13.
 */
public class TestNumberUtil {

    /**
     * 前缀
     */
    private static final String TEST="TEST";


    /**
     * 生成实验编号
     * 格式：TEST20170113104859111XXX
     * @return
     */
    public static String generateTestNumber(){

        String timeStr = DateUtil.format(DateUtil.getDate(),"yyyyMMddHHmmssSSS");
        String randomStr = getRandomString(3);
        return TEST+timeStr+randomStr;
    }

    /**
     * length表示生成字符串的长度
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
