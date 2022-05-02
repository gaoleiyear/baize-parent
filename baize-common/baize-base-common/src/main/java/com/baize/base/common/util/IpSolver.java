package com.baize.base.common.util;

import javax.servlet.http.HttpServletRequest;

public class IpSolver {
    public IpSolver() {
    }

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            String[] ips = ip.split(",");
            ip = ips[0];
        }

        if (ip != null && ip.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
            ip = "127.0.0.1";
        }

        return ip;
    }
}
