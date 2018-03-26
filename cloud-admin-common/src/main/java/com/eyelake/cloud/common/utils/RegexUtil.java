package com.eyelake.cloud.common.utils;

import com.eyelake.cloud.common.constants.PatternConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 */
public class RegexUtil {

    private static final PathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * URL是否匹配
     *
     * @param antPattern
     * @param url
     * @return
     */
    public static boolean urlMatch(String antPattern, String url) {

        if (StringUtils.isEmpty(antPattern) || StringUtils.isEmpty(url)) {
            return false;
        }
        //基本上配置的都是全路径,很少用通配符。filter中所有路径循环匹配时，这样稍微提高点效率
        if (!antPattern.contains("*") && !antPattern.contains("?")) {
            return antPattern.equals(url);
        } else {
            return antUrlMatch(antPattern, url);
        }
    }

    /**
     * ant风格URL是否匹配
     *
     * @param antPattern
     * @param url
     * @return
     */
    public static boolean antUrlMatch(String antPattern, String url) {
        return antPathMatcher.match(antPattern, url);
    }

    /**
     * 是否是非法字符
     *
     * @return
     */
    public static boolean isIllegalCharacters(String character) {
        return match(PatternConst.ILLEGAL_CHARACTERS_PATTERN, character);
    }

    /**
     * 是否匹配
     *
     * @param pattern
     * @param character
     * @return
     */
    public static boolean matches(String pattern, String character) {
        return match(Pattern.compile(pattern), character);
    }

    /**
     * 是否匹配
     *
     * @param pattern
     * @param character
     * @return
     */
    public static boolean match(Pattern pattern, String character) {
        Matcher matcher = pattern.matcher(character);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    /**
     * 获取字符串中指定位置匹配串中的起始和截止下标
     * list有两个值，第一个startIndex，第二个endIndex
     *
     * @param pattern
     * @param character
     * @param position  要获得第几个匹配串的坐标。默认为1。
     * @return
     */
    public static List<Integer> getIndexs(Pattern pattern, String character, int position) {
        if (position <= 0) {
            position = 1;
        }
        Matcher matcher = pattern.matcher(character);
        List<Integer> indexs = new ArrayList<Integer>();

        for (int i = 0; i < position; i++) {
            if (matcher.find() && i == position - 1) {
                indexs.add(matcher.start());
                indexs.add(matcher.end());
            }
        }
        return indexs;
    }
}
