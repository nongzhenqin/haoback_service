package com.haoback.common.utils;

import com.haoback.sys.entity.SysUser;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 工具类
 * Created by nong on 2017/4/21.
 */
public class CommonUtils {

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    public static SysUser getSysUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        SecurityContextImpl securityContext = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        return (SysUser) securityContext.getAuthentication().getPrincipal();
    }
}
