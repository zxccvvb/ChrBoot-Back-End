package com.chr.admin.config;


import com.chr.admin.interceptors.LoginProtectedInterceptor;
import com.chr.common.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
     * 拓展mvc框架的消息转换器
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("开始扩展消息转换器...");
        //创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象序列化转为json
        converter.setObjectMapper(new JacksonObjectMapper());
        //将消息转换器放到容器中
        //这里需要滞后一点否则knife4j会失效
        converters.add(6, converter);
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
