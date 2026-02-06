package com.chr.admin.security;

import com.aliyun.core.utils.StringUtils;
import com.chr.admin.pojo.Employee;
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

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader(jwtProperties.getAdminTokenName());
        if(StringUtils.isBlank(token)){
            //如果没有token则放行交给后续filter
            filterChain.doFilter(request,response);
            return;
        }
        //解析token
        Claims claims = JwtHelper.parseJWT(token, jwtProperties.getAdminSecretKey());
        String id = (String) claims.get(JwtClaimsConstant.EMP_ID);
        //从redis中获取用户信息
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(JwtClaimsConstant.ADMIN_LOGIN + id);
        if(Objects.isNull(loginUser)){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_REDIS_ERROR);
        }
        //存入SecurityContextHolder
        //TODO 获取权限信息
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        //放行
        filterChain.doFilter(request,response);
    }
}
