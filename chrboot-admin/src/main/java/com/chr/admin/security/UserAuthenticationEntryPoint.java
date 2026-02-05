package com.chr.admin.security;

import com.alibaba.fastjson.JSON;
import com.chr.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class UserAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //获取错误信息
        String localizedMessage = authException.getLocalizedMessage();

        String json = JSON.toJSONString(Result.ok(localizedMessage));

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
    }
}
