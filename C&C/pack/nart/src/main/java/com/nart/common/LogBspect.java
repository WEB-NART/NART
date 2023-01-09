package com.nart.common;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: LogBspect
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/9/1 17:46
 */

import com.nart.util.GsonFormatter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LogAspect
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/12/28 13:30
 */
@Component
@Aspect
public class LogBspect {

    private final Logger log = LoggerFactory.getLogger(LogBspect.class);


    @Pointcut("@annotation(com.nart.common.LogB)")
    public void pt() {}

    @Around("pt()")
    public Object log(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        recordLog(point, time, result);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time, Object result) {
        log.info("=====================log start================================");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // running method name
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}", className + "." + methodName + "()");

        // parameter name
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            String params = GsonFormatter.toJsonString(args[0]);
            log.info("params:{}", params);
        }
        // output
        String res;
        if(result.getClass() != String.class) {
            res = GsonFormatter.toJsonString(result);
        } else {
            res = (String) result;
        }
        log.info("result answer: {}", res);

        log.info("execute time : {} ms", time);
        log.info("=====================log end================================");
    }

}
