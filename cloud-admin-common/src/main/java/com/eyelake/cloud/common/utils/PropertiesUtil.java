package com.eyelake.cloud.common.utils;

import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.yjh.framework.lang.AppException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @version 1.0
 * @module 系统模块
 * @func 系统基础类
 */

public class PropertiesUtil {
    // 日志记录
    private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

    private static ResourceBundle APP_ENV_RES = ResourceBundle.getBundle(PropertiesConstants.APPLICATION_ENV_CONFIG);
    
    public PropertiesUtil() {
    }

    public static ResourceBundle getResourceBundle(String resourceBundle) {
        if (PropertiesConstants.APPLICATION_ENV_CONFIG.equals(resourceBundle)) {
            return APP_ENV_RES;
        } else {
            throw new AppException("load Properties file failed:" + resourceBundle);
        }
    }

    public static String getString(String resourceBundle, String key) {
        return getString(resourceBundle, key, "");
    }

    public static String getString(String resourceBundle, String key, String defaultVal) {
        try {
            return getResourceBundle(resourceBundle).getString(key);
        } catch (MissingResourceException e) {
            LOGGER.error(e);
        }
        return defaultVal;
    }

    public static int getInt(String resourceBundle, String key) {
        return getInt(resourceBundle, key, 0);
    }

    public static int getInt(String resourceBundle, String key, int defaultVal) {
        String configValue = getString(resourceBundle, key);
        if (StringUtils.isEmpty(configValue)) {
            return defaultVal;
        } else {
            return Integer.parseInt(configValue);
        }
    }

    public static boolean getBoolean(String resourceBundle, String key) {
        String configValue = getString(resourceBundle, key);
        if (StringUtils.isEmpty(configValue)) {
            return false;
        } else {
            return Boolean.parseBoolean(configValue);
        }
    }

}