package com.chr.admin.controller.user;


import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserPageQueryDTO;
import com.chr.admin.pojo.dto.UserRegisterDTO;
import com.chr.admin.pojo.dto.UserUpdateDTO;
import com.chr.admin.service.UserService;
import com.chr.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;


@RestController("UserController")
@RequestMapping("/user/user")
@CrossOrigin
@Tag(name = "用户接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO);
        Result result = userService.login(userLoginDTO);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "用户信息接口")
    public Result getUserInfo(){
        log.info("获取用户信息");
        Result result = userService.info();
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}", userRegisterDTO);
        Result result = userService.register(userRegisterDTO);
        return result;
    }

}
