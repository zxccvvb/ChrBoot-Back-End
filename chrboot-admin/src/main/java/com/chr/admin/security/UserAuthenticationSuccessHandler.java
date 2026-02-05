package com.chr.admin.security;

import com.alibaba.fastjson.JSON;
import com.chr.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); //获取用户权限信息
        Object credentials = authentication.getCredentials(); //获取用户凭证信息
        Object principal = authentication.getPrincipal(); //获取用户身份信息


        String json = JSON.toJSONString(Result.ok(principal));

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
    }
}
