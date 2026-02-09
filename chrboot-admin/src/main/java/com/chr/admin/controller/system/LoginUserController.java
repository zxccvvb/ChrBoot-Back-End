package com.chr.admin.controller.system;


import com.chr.admin.customize.service.login.LoginUserService;
import com.chr.common.result.Result;
import com.chr.domain.system.user.dto.SysUserLoginDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户登录相关接口")
@Slf4j
public class LoginUserController {

    @Autowired
    private LoginUserService loginUserService;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result login(@RequestBody @Valid SysUserLoginDTO sysUserLoginDTO){
        log.info("用户登录：{}", sysUserLoginDTO);
        Result result = loginUserService.login(sysUserLoginDTO);
        return result;
    }

    @PostMapping("/logout")
    @Operation(summary = "退出接口")
    public Result logout(){
        log.info("用户退出");
        Result result = loginUserService.logout();
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "注册接口")
    public Result register(@RequestBody @Valid SysUserRegisterDTO sysUserRegisterDTO){
        log.info("用户注册：{}", sysUserRegisterDTO);
        Result result = loginUserService.register(sysUserRegisterDTO);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "信息接口")
    public Result getUserInfo(){
        log.info("获取用户信息");
        Result result = loginUserService.info();
        return result;
    }
}
