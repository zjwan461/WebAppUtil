package com.itsu.annotation;

import java.lang.annotation.*;

/**
 * @author 苏犇
 * @date 2019/7/27 16:37
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TreeChildren {
}
