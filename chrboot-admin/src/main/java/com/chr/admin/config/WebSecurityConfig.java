package com.chr.admin.config;

import com.chr.admin.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//springBoot 通过注解的方法默认开启可以省略
@EnableWebSecurity
//开启基于方法的授权
@EnableMethodSecurity
public class WebSecurityConfig{


    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private ExceptionHandlingFilter exceptionHandlingFilter;

    /**
     * 密码加密器 - 核心 Bean
     * 使用 BCrypt 强哈希算法，自动加盐
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 默认强度 10，可改为 12-14 更安全（但更慢）
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
     * 安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 授权配置
                .authorizeHttpRequests(auth -> auth
                        //放行登录注册接口
                        .requestMatchers(
                                "/admin/employee/login",
                                "/admin/employee/register",
                                "/user/user/login",
                                "/user/user/register").permitAll()
                        //除了上方以外的接口全部授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
                )
                //添加过滤器
                .addFilterBefore(exceptionHandlingFilter, ChannelProcessingFilter.class)
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 表单登录
                .formLogin(form -> form
//                        .loginPage("/login")
                        //默认成功登录后访问的地址
                        .defaultSuccessUrl("/doc.html")
                        //登录失败之后访问的地址
                        .failureUrl("/login?error")
                        .permitAll()
                )
                // 注销
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                // 未认证的请求
                .exceptionHandling(except->{

                })
                // 会话并发设置
                .sessionManagement(session->{
                    //不使用sprintSecurity的session
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // 禁用 CSRF（开发测试时，生产环境建议开启）
                .csrf(csrf -> csrf.disable());

        return http.build();
    }


}
