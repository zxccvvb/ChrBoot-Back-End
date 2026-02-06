package com.chr.admin.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 将过滤器中的所有异常通过全局异常处理捕获 统一处理
 */
@Component
public class ExceptionHandlingFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            // 捕获过滤器链中的所有异常
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}