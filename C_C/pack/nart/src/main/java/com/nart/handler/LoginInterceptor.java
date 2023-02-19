package com.nart.handler;

import com.nart.pojo.User;
import com.nart.service.LoginService;
import com.nart.util.ErrorCode;
import com.nart.util.GsonFormatter;
import com.nart.util.Result;
import com.nart.util.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: LoginInterceptor
 *  intercept all request except for login and register
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023/1/07 22:22
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在执行controller方法(Handler)之前进行执行
        /** 1.
         * 1. need to determine if the requested interface path is HandlerMethod (controller method)
         * 2. determine if the token is empty, if empty, not logged in Jump to login
         * 3. verify the token Login verification
         * 4. release on success
         */
        if(!(handler instanceof HandlerMethod)) {
            // allow access to static
            return true;
        }
        String token = request.getHeader("Authorization");
//        System.out.println("token: " + token);

//        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
//        log.info("request uri:{}",requestURI);
//        log.info("request method:{}",request.getMethod());
//        log.info("token:{}", token);
//        log.info("=================request end===========================");

        if(StringUtils.isBlank(token)) {
            noLogin(response);
            return false;
        }
        User user = loginService.checkToken(token);
        if(user == null) {
            noLogin(response);
            return false;
        }
        // save userinfo in ThreadLocal
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // delete before complete request
        UserThreadLocal.remove();
    }

    private void noLogin(HttpServletResponse response) throws IOException {
        Result result = Result.fail(ErrorCode.NO_LOGIN);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(GsonFormatter.toJsonString(result));
    }
}
