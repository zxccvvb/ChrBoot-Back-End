package com.chr.admin.config;


import com.chr.admin.interceptors.LoginProtectedInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginProtectedInterceptor loginProtectedInterceptor;

    /**
     * 添加拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("添加拦截器");
        registry.addInterceptor(loginProtectedInterceptor).addPathPatterns("/user/**").excludePathPatterns(new String[]{"/user/login", "/user/register"});
    }

    /**
     * 设置静态资源映射,主要访问接口文档的(js,html,css)
     * 这里开启swagger的静态网址访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开启设置静态资源映射");
   }
}
