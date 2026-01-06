package com.chr.admin.interceptors;

import com.alibaba.druid.util.StringUtils;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.BizExceptionEnume;
import com.chr.common.exception.BizException;
import com.chr.common.properties.JwtProperties;
import com.chr.common.utils.context.BaseContext;
import com.chr.common.utils.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户端登录拦截器 检查token
 */
@Component
@Slf4j
public class jwtTokenUserInterceptor implements HandlerInterceptor {


    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        // 判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            // 当前拦截到的不是动态方法，直接放行
            return true;
        }


        //获取token
        String token = request.getHeader(jwtProperties.getUserTokenName());
        //token为空或者token过期则禁止
        if (StringUtils.isEmpty(token) || JwtHelper.isExpiration(token,jwtProperties.getUserSecretKey())) {
            throw new BizException(BizExceptionEnume.USER_TOKEN_ERROR);
        }
        Claims employe = JwtHelper.parseJWT(token,jwtProperties.getUserSecretKey());
        log.info("当前用户id为：{}",employe.get(JwtClaimsConstant.USER_ID));
        BaseContext.setCurrentId((Long) employe.get(JwtClaimsConstant.USER_ID));
        return true;
    }
}
