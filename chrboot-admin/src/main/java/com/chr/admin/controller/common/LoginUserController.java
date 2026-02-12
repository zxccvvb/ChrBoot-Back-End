package com.chr.admin.controller.common;


import com.chr.admin.customize.service.login.LoginUserService;
import com.chr.common.result.Result;
import com.chr.domain.system.user.command.LoginUserCommand;
import com.chr.domain.system.user.command.RegisterUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户登录相关接口")
public class LoginUserController {

    @Autowired
    private LoginUserService loginUserService;

    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result login(@RequestBody @Valid LoginUserCommand loginUserCommand){
        Result result = loginUserService.login(loginUserCommand);
        return result;
    }

    @PostMapping("/logout")
    @Operation(summary = "退出接口")
    public Result logout(){
        Result result = loginUserService.logout();
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "注册接口")
    public Result register(@RequestBody @Valid RegisterUserCommand registerUserCommand){
        Result result = loginUserService.register(registerUserCommand);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "信息接口")
    public Result getUserInfo(){
        Result result = loginUserService.info();
        return result;
    }
}
