package com.nart.common;

import com.nart.util.GsonFormatter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LogAspect
 *  Log request and response detail
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/10 13:30
 */
@Component
@Aspect
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger(LogAspect.class);


    @Pointcut("@annotation(com.nart.common.LogA)")
    public void pt() {

    }

    private static final String[] types = { "java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float" };

    @Around("pt()")
    public Object log(ProceedingJoinPoint point) throws Throwable {

        long beginTime = System.currentTimeMillis();
        // execute method
        Object result = point.proceed();
        // how long does it take(milli seconds)
        long time = System.currentTimeMillis() - beginTime;
        // save log
        recordLog(point, time, result);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time, Object result) {
        log.info("=====================log start================================");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        LogA logA = method.getAnnotation(LogA.class);
//        log.info("module:{}", logA.module());
//        log.info("operation:{}", logA.operator());

        // request method
        Object target = joinPoint.getTarget();
        String className = target.getClass().getName();
        String methodName = signature.getName();
        //log.info("ip:{}", IpUtils.getIpAddr());
        log.info("request method:{}", className + "." + methodName + "()");

        // request parameters
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            String params = GsonFormatter.toJsonString(args[0]);
            log.info("params:{}", params);
        }

        // get request IP address
        log.info("{}", result);

        // print request header和body
        //printRequest(HttpContextUtils.getHttpServletRequest(), joinPoint);

        log.info("execute time : {} ms", time);
        log.info("=====================log end================================");
    }

//    private void printRequest(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        log.info("  header: {");
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            // exclude Cookie variable
//            if (key.equalsIgnoreCase("Cookie")) {
//                continue;
//            }
//            String value = request.getHeader(key);
//            log.info("      key: {} value: {}", key, value);
//        }
//        log.info("} body: {");
//        readBody(joinPoint);
//        log.info("}");
//    }
//
//    private void readBody(JoinPoint joinPoint) {
//        try {
//            // catch request, record request content
//            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//                    .getRequestAttributes();
//            String logStr = "";
//            HttpServletRequest request = attributes.getRequest();
//            HttpServletResponse response = attributes.getResponse();
//            // record request content
//            logStr += "URL:" + request.getRequestURI().toString() + " | ";
//            logStr += "CLASS_METHOD:" + joinPoint.getSignature().getDeclaringType().getSimpleName() + "."
//                    + joinPoint.getSignature().getName() + " | ";
//            logStr += "ARGS:";
//            // joinPoint get parameter name
//            String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
//            // joinPoint get parameter value
//            Object[] args = joinPoint.getArgs();
//            Signature signature = joinPoint.getSignature();
//            MethodSignature methodSignature = (MethodSignature) signature;
//            Method method = methodSignature.getMethod();
//            if (true) {
//                // print parameter value
//                int i = 0;
//                for (Object arg : args) {
//                    if (arg == request || arg == response) {
//                        i += 1;
//                        continue;
//                    }
//                    String typeName = "";
//                    try {
//                        typeName = arg.getClass().getTypeName();
//                    } catch (Exception e) {
//                    }
//                    if (!Arrays.asList(types).contains(typeName)) {
//                        // reformat parameter to JSON
//                        logStr += "&" + params[i] + "=" + GsonFormatter.toJsonString(arg);
//                    } else {
//                        logStr += "&" + params[i] + "=" + arg;
//                    }
//                    i += 1;
//                }
//            }
//            logStr += " | ";
//            log.info("print request: ");
//            log.info(logStr);
//        } catch (Throwable e) {
//            log.error("HaLogParamAspect error.", e);
//        }
//    }

}
