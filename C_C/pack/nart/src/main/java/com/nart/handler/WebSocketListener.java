package com.nart.handler;

import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: pack
 *
 * @className: RequestListener
 *  listen for websocket actions
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023-01-04 4:06 p.m.
 */
@Component
//@Slf4j
public class WebSocketListener implements ServletRequestListener {

    public WebSocketListener() {}

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // set all request with httpSession
        //log.info("WebSocketListener request Initialized");
        ((HttpServletRequest) sre.getServletRequest()).getSession();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        //log.info("WebSocketListener request Destroyed");
    }
}
