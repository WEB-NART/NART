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
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2023-01-04 4:06 p.m.
 */
@Component
public class WebSocketListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        // 将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
    }

    public WebSocketListener() {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
    }
}
