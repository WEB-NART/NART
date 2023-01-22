package com.nart.util.upload;

/**
 * Copyright (c) 2008-2024: Zirui Qiao
 * Project: blog-parent
 *
 * @className: IpUtils
 *  TODO
 * @version: v1.8.0
 * @Author ZIRUI QIAO
 * @Date 2022/7/27 13:40
 */
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取Ip
 *
 */
@Slf4j
public class IpUtils {

    /**
     * Get IP address
     * <p>
     * If you are using a reverse proxy such as Nginx, you cannot get an IP address with request.getRemoteAddr().
     * If you are using a multi-level reverse proxy, there is not just one X-Forwarded-For value, but a string of IP addresses.
     * The first valid IP string in the X-Forwarded-For that is not unknown is the real IP address
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null, unknown = "unknown", separator = ",";
        int maxLength = 15;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || ip.length() == 0 || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IpUtils ERROR ", e);
        }

        // Using a proxy, the first IP address is obtained
        if (StringUtils.isNotEmpty(ip) && ip.length() > maxLength) {
            int idx = ip.indexOf(separator);
            if (idx > 0) {
                ip = ip.substring(0, idx);
            }
        }

        return ip;
    }

    /**
     * Get ip address
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        return getIpAddr(request);
    }
}