package com.eyelake.cloud.common.constants;

import java.util.regex.Pattern;

/**
 * 正则表达式
 * 
 */
public class PatternConst {

    /** 非法字符 */
    public static final Pattern ILLEGAL_CHARACTERS_PATTERN = Pattern
                                                                   .compile(
                                                                           "%0d%0a|SCRIPT|EVAL|ALERT|EXEC|DROP|SELECT|ALTER|INSERT|UPDATE|DELETE|EXISTS|\\bOR\\b|\\bXOR\\b|EXECUTE|XP_CMDSHELL|DECLARE|SP_OACREATE|WHERE|WSCRIPT.SHELL|XP_REGWRITE|\\'|\\<|\\>|\\$|\\&|\\\\|\\||\\\"|\\~|\\`|\\*",
                                                                           Pattern.CASE_INSENSITIVE);
}
