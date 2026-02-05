package com.chr.admin.security;

import com.alibaba.fastjson.JSON;
import com.chr.common.result.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class UserAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //获取错误信息
        String localizedMessage = accessDeniedException.getLocalizedMessage();

        String json = JSON.toJSONString(Result.ok(localizedMessage));

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(json);
    }
}
