package com.haoback.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class IpUtils {

    private static final String LOCALHOST = "127.0.0.1";
    private IpUtils() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }

        StringBuffer ipBuff = new StringBuffer();

        String ip = request.getHeader("x-forwarded-for");
        if(StringUtils.isNotBlank(ip)) ipBuff.append(ip).append(",");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || LOCALHOST.equals(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if(StringUtils.isNotBlank(ip)) ipBuff.append(ip).append(",");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || LOCALHOST.equals(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            if(StringUtils.isNotBlank(ip)) ipBuff.append(ip).append(",");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || LOCALHOST.equals(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            if(StringUtils.isNotBlank(ip)) ipBuff.append(ip).append(",");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || LOCALHOST.equals(ip)) {
            ip = request.getHeader("X-Real-IP");
            if(StringUtils.isNotBlank(ip)) ipBuff.append(ip).append(",");
        }

        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || LOCALHOST.equals(ip)) {
            ip = request.getRemoteAddr();
            if(StringUtils.isNotBlank(ip)) ipBuff.append(ip);
        }

        return ipBuff.lastIndexOf(",") > -1 ? ipBuff.substring(0, ipBuff.length() - 1) : ipBuff.toString();
    }
}
