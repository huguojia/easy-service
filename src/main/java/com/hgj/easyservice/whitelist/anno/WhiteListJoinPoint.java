package com.hgj.easyservice.whitelist.anno;

import com.alibaba.fastjson.JSON;
import com.hgj.easyservice.whitelist.config.WhiteListProperties;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.lang.reflect.Method;

/**
 description: WhiteListJoinPoint
 date: 2021/4/20 16:29
 author: huguojia
 version: 1.0
 */
@Aspect
public class WhiteListJoinPoint {

    private Logger logger = LoggerFactory.getLogger(WhiteListJoinPoint.class);


    @Autowired
    private WhiteListProperties prop;

    @Pointcut("@annotation(com.hgj.easyservice.whitelist.anno.WhiteList)")
    public void aopPoint() {
    }


    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取内容
        Method method = getMethod(jp);
        WhiteList whiteList = method.getAnnotation(WhiteList.class);
        // 获取字段值
        String keyValue = getFiledValue(whiteList.value(), jp.getArgs());
        logger.info("middleware whitelist handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();

        String[] split = prop.getUsers().split(",");

        // 白名单过滤
        for (String str : split) {
            if (keyValue.equals(str)) {
                return jp.proceed();
            }
        }

        // 拦截
        return returnObject(whiteList, method);
    }

    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    // 返回对象
    private Object returnObject(WhiteList whiteList, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.message();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    // 获取属性值
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }




}