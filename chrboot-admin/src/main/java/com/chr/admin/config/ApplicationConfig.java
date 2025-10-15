package com.chr.admin.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@MapperScan("com.chr.admin.mapper")
public class ApplicationConfig {
}
