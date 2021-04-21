package com.hgj.easyservice.whitelist.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: WhiteList <br>
 * @date: 2021/4/20 16:07 <br>
 * @author: huguojia <br>
 * @version: 1.0 <br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WhiteList {

    String value() default "";

    String message() default "";

    String code() default "";

}
