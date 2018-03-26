/**
 *
 */
package com.eyelake.cloud.common.utils;

import com.eyelake.cloud.common.annotations.validator.StringValidator;
import com.eyelake.cloud.common.constants.GlobalErrors;
import com.yjh.framework.lang.AppException;
import com.yjh.framework.lang.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 请输入功能描述
 *
 * @author
 */
public class CommonValidator {

    private static Logger logger = LoggerFactory.getLogger(CommonValidator.class);

    /**
     * 验证一般参数
     *
     * @param method
     * @return
     */
    public static Result validateParameter(Method method, Object args[]) {
        //
        return null;
    }

    /**
     * 验证传参dto
     *
     * @param object
     * @return
     */
    public static <T> Result validate(T object) {

        Result validated = new Result();
        validated.setSuccess(true);

        Class<? extends Object> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {

            /* String Validator */

            if (field.isAnnotationPresent(StringValidator.class)) {
                Object valueObj = invokeGetter(field, object);

                StringValidator annotationObj = field.getAnnotation(StringValidator.class);
                boolean nullable = annotationObj.nullable();
                if (!nullable && (valueObj == null || StringUtils.isBlank((String) valueObj))) {
                    validated.fail();
                    break;
                } else if (valueObj == null || StringUtils.isBlank((String) valueObj)) {
                    validated.setSuccess(true);
                } else {
                    String value = valueObj.toString();
                    String pattern = annotationObj.pattern();
                    if ("".equals(pattern)) {
                        continue;
                        // pattern = makePattern(annotationObj.minLength(),
                        // annotationObj.maxLength(),
                        // annotationObj.chinese(), annotationObj.letter(),
                        // annotationObj.number());
                    }
                    boolean match = false;
                    try {
                        match = Pattern.matches(pattern, value);
                        if (!match) {
                            logger.error("CommonValidator:Pattern doesn't match.");
                            logger.error("CommonValidator:Pattern is (" + pattern + ").");
                            logger.error("CommonValidator:Value is (" + value + ").");
                        }
                    } catch (Exception ex) {
                        throwWrongFormatException();
                    }
                    if (match) {
                        validated.setSuccess(true);
                    } else {
                        validated.fail();
                        validated.setErrorCode(GlobalErrors.WRONG_PARAMETER);
                        break;
                    }
                }
            }
        }
        return validated;
    }

    /**
     * 执行getter
     *
     * @param field
     * @return
     */
    private static <T> Object invokeGetter(Field field, T object) {
        Object value = null;

        String fieldName = field.getName();
        String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            Method method = object.getClass().getMethod(methodName);
            Object valueObj = method.invoke(object);
            value = valueObj;
        } catch (Exception ex) {
            throwWrongFormatException();
        }
        return value;
    }

    /**
     * 格式异常
     */
    private static void throwWrongFormatException() {
        Result result = new Result();
        result.setErrorCode(GlobalErrors.WRONG_PATTERN_ANNOTATION);
        result.setMessage("Error in validating.Suggest to be give a wrong formatted object getter-method.");
        throw new AppException(result);
    }

    /**
     * 创建正则表达式
     *
     * @param minLength
     * @param maxLength
     * @param chinese
     * @param letter
     * @param number
     * @return
     */
    @SuppressWarnings("unused")
    private static String makePattern(long minLength, long maxLength, boolean chinese, boolean letter, boolean number) {
        String pattern = "";
        if (chinese) {
            pattern += (pattern.length() != 0) ? "|" : "";
            pattern += "\\u4e00-\\u9fa5";
        }
        if (letter) {
            pattern += (pattern.length() != 0) ? "|" : "";
            pattern += "a-z|A-Z";
        }
        if (number) {
            pattern += (pattern.length() != 0) ? "|" : "";
            pattern += "0-9";
        }
        pattern = "[" + pattern + "]";
        pattern += "{" + minLength + "," + maxLength + "}";

        return pattern;
    }

}
