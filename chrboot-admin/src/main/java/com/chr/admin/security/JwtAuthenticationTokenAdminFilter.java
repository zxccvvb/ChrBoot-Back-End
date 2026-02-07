package com.chr.admin.security;

import com.alibaba.druid.util.StringUtils;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.common.properties.JwtProperties;
import com.chr.common.utils.jwt.JwtHelper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * 前后端分离的情况下实现jwt过滤器+redis来验证用户登录
 */
@Component
public class JwtAuthenticationTokenAdminFilter extends OncePerRequestFilter {


    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        if(StringUtils.isEmpty(token)){
            //如果没有token则放行交给后续filter
            filterChain.doFilter(request,response);
            return;
        }
        //检查token是否过期
        if(JwtHelper.isExpired(jwtProperties.getAdminSecretKey(),token)){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_JWT_ERROR);
        }
        //解析token
        Claims claims = JwtHelper.parseJWT(jwtProperties.getAdminSecretKey(),token);
        Object id =  claims.get(JwtClaimsConstant.USER_ID);

        //检查当前token是否是redis中保存的最新token
        String lastAdviceToken = (String)redisTemplate.opsForValue().get(JwtClaimsConstant.ADMIN_ADVICE + id);
        if(!StringUtils.equals(token,lastAdviceToken) && !StringUtils.isEmpty(lastAdviceToken)){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_SESSION_ERROR);
        }

        //从redis中获取用户信息
        AuthDetails authDetails = (AuthDetails) redisTemplate.opsForValue().get(JwtClaimsConstant.ADMIN_LOGIN + id);
        if(Objects.isNull(authDetails)){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_REDIS_ERROR);
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authDetails,null, authDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
