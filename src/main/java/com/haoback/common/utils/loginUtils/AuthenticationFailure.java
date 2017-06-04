package com.haoback.common.utils.loginUtils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录失败处理器 结果可返回json
 * 所有抛出异常：
 * BadCredentialsException (org.springframework.security.authentication)
 * UsernameNotFoundException (org.springframework.security.core.userdetails)
 * SessionAuthenticationException (org.springframework.security.web.authentication.session)
 * ActiveDirectoryAuthenticationException (org.springframework.security.ldap.authentication.ad)
 * ProviderNotFoundException (org.springframework.security.authentication)
 * AuthenticationServiceException (org.springframework.security.authentication)
 * InternalAuthenticationServiceException (org.springframework.security.authentication)
 * NonceExpiredException (org.springframework.security.web.authentication.www)
 * AuthenticationCancelledException (org.springframework.security.openid)
 * AccountStatusException (org.springframework.security.authentication)
 * LockedException (org.springframework.security.authentication)
 * DisabledException (org.springframework.security.authentication)
 * CredentialsExpiredException (org.springframework.security.authentication)
 * AccountExpiredException (org.springframework.security.authentication)
 * InsufficientAuthenticationException (org.springframework.security.authentication)
 * PreAuthenticatedCredentialsNotFoundException (org.springframework.security.web.authentication.preauth)
 * AuthenticationCredentialsNotFoundException (org.springframework.security.authentication)
 * RememberMeAuthenticationException (org.springframework.security.web.authentication.rememberme)
 * CookieTheftException (org.springframework.security.web.authentication.rememberme)
 * InvalidCookieException (org.springframework.security.web.authentication.rememberme)
 * Created by nong on 2017/3/30.
 */
public class AuthenticationFailure implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("code", "0");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");

        String message = "";

        if(e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            message = "账号或密码错误";
        }else if(e instanceof LockedException){
            message = "账号被锁定";
        }else if(e instanceof DisabledException){
            message = "账号被禁用";
        }else{
            message = "登录失败";
        }

        result.put("msg", message);
        PrintWriter out = httpServletResponse.getWriter();
        out.append(JSONObject.toJSONString(result));
    }
}
