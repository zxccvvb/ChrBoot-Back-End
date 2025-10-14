package com.chr.admin.interceptors;

import com.alibaba.druid.util.StringUtils;
import com.chr.admin.exception.BizException;
import com.chr.admin.exception.BizExceptionEnume;
import com.chr.admin.utils.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 登录拦截器 检查token
 */

@Component
public class LoginProtectedInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtHelper jwtHelper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取token
        String token = request.getHeader("token");
        //token为空或者token过期则禁止
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {
            throw new BizException(BizExceptionEnume.USER_TOKEN_ERROR);
        }
        return true;
    }
}
