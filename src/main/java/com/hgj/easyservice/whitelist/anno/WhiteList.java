package com.hgj.easyservice.whitelist.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 description: WhiteList
 date: 2021/4/20 16:07
 author: huguojia
 version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WhiteList {

    String value() default "";

    String message() default "";

    String code() default "";

}
