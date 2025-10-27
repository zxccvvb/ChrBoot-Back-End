package com.chr.admin.interceptors;

import com.alibaba.druid.util.StringUtils;
import com.chr.common.exception.BizException;
import com.chr.common.enums.BizExceptionEnume;
import com.chr.common.utils.context.BaseContext;
import com.chr.common.utils.jwt.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


/**
 * 登录拦截器 检查token
 */

@Component
@Slf4j
public class LoginProtectedInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtHelper jwtHelper;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取token
        String token = request.getHeader("token");
        //token为空或者token过期则禁止
        if (StringUtils.isEmpty(token) || jwtHelper.isExpiration(token)) {
            throw new BizException(BizExceptionEnume.USER_TOKEN_ERROR);
        }
        Long userId = jwtHelper.getUserId(token);
        log.info("当前用户id为：{}",userId);
        BaseContext.setCurrentId(userId);
        return true;
    }
}
