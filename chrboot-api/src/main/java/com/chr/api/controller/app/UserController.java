package com.chr.api.controller.app;


import com.chr.common.result.Result;
import com.chr.domain.system.user.command.LoginUserCommand;
import com.chr.domain.system.user.command.RegisterUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("UserController")
@RequestMapping("/app/user")
@Tag(name = "用户接口")
@Slf4j
public class UserController {

    @Autowired
    private AppUserService appUserService;


    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result login(@RequestBody @Valid LoginUserCommand loginUserCommand){
        log.info("用户登录：{}", loginUserCommand);
        Result result = appUserService.login(loginUserCommand);
        return result;
    }

    @PostMapping("/logout")
    @Operation(summary = "退出接口")
    public Result logout(){
        log.info("用户退出");
        Result result = appUserService.logout();
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "注册接口")
    public Result register(@RequestBody @Valid RegisterUserCommand registerUserCommand){
        log.info("用户注册：{}", registerUserCommand);
        Result result = appUserService.register(registerUserCommand);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "信息接口")
    public Result getUserInfo(){
        log.info("获取用户信息");
        Result result = appUserService.info();
        return result;
    }

}
