package com.chr.admin.security;

import com.alibaba.fastjson.JSON;
import com.chr.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String jsonString = JSON.toJSONString(Result.build(null, 403, "未认证的请求"));
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonString);
    }
}
