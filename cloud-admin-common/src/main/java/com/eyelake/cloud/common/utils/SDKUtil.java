package com.eyelake.cloud.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.eyelake.cloud.common.constants.PropertiesConstants;

public class SDKUtil {

    //初始化ascClient需要的几个参数
    private static final String API_PRODUCT = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_PRODUCT);//物联卡API产品名称（短信产品名固定，无需修改）
    private static final String API_DOMAIN = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_DOMAIN);//物联卡API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    private static final String ACCESS_KEY_ID = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_ID);;//你的accessKeyId,参考本文档步骤2
    private static final String ACCESS_KEY_SECRET = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_SECRET);;//你的accessKeySecret，参考本文档步骤2

    public static IAcsClient init() {

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID,
                ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", API_PRODUCT, API_DOMAIN);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new DefaultAcsClient(profile);
    }
}
