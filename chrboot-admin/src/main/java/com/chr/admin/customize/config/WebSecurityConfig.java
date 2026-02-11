package com.chr.admin.customize.config;

import com.chr.infrastructure.exception.ExceptionHandlingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;

@Configuration
//springBoot 通过注解的方法默认开启可以省略
@EnableWebSecurity
//开启基于方法的授权
@EnableMethodSecurity
public class WebSecurityConfig{


    @Autowired
    private JwtAuthenticationTokenAdminFilter jwtAuthenticationTokenAdminFilter;
    @Autowired
    private ExceptionHandlingFilter exceptionHandlingFilter;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 密码加密器 - 核心 Bean
     * 使用 BCrypt 强哈希算法，自动加盐
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 默认强度 10，强度越高越慢
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    /**
     * 管理端安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain adminfilterChain(HttpSecurity http) throws Exception {
        http
                //路径隔离
                .securityMatcher("/admin/**")
                // 授权配置
                .authorizeHttpRequests(auth -> auth
                        //放行登录注册接口
                        .requestMatchers(
                                "/admin/user/login",
                                "/admin/user/register").permitAll()
                        //除了上方以外的接口全部授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
                )
                //添加过滤器
                .addFilterBefore(exceptionHandlingFilter, DisableEncodeUrlFilter.class)
                .addFilterBefore(jwtAuthenticationTokenAdminFilter, UsernamePasswordAuthenticationFilter.class)
                //配置异常处理器
                .exceptionHandling(excptionHandler->{
                    //认证失败异常
                    excptionHandler.authenticationEntryPoint(authenticationEntryPoint);
                    //权限不足异常
                    excptionHandler.accessDeniedHandler(accessDeniedHandler);
                })
                // 会话并发设置
                .sessionManagement(session->{
                    //不使用sprintSecurity的session
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 禁用 CSRF（开发测试时，生产环境建议开启）
                .csrf(csrf -> csrf.disable())
                // 允许跨域并且使用webMvc配置的跨域规则
                .cors(Customizer.withDefaults());
        return http.build();
    }


}
