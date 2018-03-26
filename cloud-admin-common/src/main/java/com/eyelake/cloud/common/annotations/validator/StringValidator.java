/**
 *
 */
package com.eyelake.cloud.common.annotations.validator;

import java.lang.annotation.*;

/**
 * 字符串校验
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringValidator {

    boolean nullable() default true;

    String pattern() default "";

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    boolean chinese() default true;

    boolean letter() default true;

    boolean number() default true;

}
