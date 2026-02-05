package com.chr.admin.config;

import com.chr.admin.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
//springBoot 通过注解的方法默认开启可以省略
//@EnableWebSecurity
public class WebSecurityConfig {

//    /**
//     * 密码加密器 - 核心 Bean
//     * 使用 BCrypt 强哈希算法，自动加盐
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        // 默认强度 10，可改为 12-14 更安全（但更慢）
//        return new BCryptPasswordEncoder();
//    }

    /**
     * 安全过滤器链配置
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 授权配置
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/admin/employee/login",
                                "/admin/employee/register",
                                "/user/user/login",
                                "/user/user/register")
                        //无需授权即可访问当前页面
                        .permitAll()
                        //用户对权限
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        //对所有请求开启授权保护
                        .anyRequest()
                        //已认证的请求会被自动授权
                        .authenticated()
                )
                // 表单登录
                .formLogin(form -> form
//                        .loginPage("/login")
                        //默认成功登录后访问的地址
                        .defaultSuccessUrl("/doc.html")
                        //登录失败之后访问的地址
                        .failureUrl("/login?error")
                        .successHandler(new UserAuthenticationSuccessHandler())
                        .permitAll()
                )
                // 注销
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                )
                // 未认证的请求
                .exceptionHandling(except->{
//                    except.authenticationEntryPoint(new UserAuthenticationEntryPoint());
                    except.accessDeniedHandler(new UserAccessDeniedHandler());
                })
                // 会话并发设置
                .sessionManagement(session->{
                    session.maximumSessions(1)
                            .expiredSessionStrategy(new UserSessionInformationExpiredStrategy());
                })
                // 禁用 CSRF（开发测试时，生产环境建议开启）
                .csrf(csrf -> csrf.disable());

        return http.build();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        //创建基于内存的用户信息管理器
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        //创建UserDetails对象用于管理用户名 用户密码 用户角色 用户权限等内容
//        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("ADMIN").build());
//        return manager;
//    }

//    public UserDetailsService userDetailsService() {
//        //创建基于内存的用户信息管理器
//        DBUserDetailsManager manager = new DBUserDetailsManager();
//        return manager;
//    }
}
