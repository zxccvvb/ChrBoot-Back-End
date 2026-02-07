package com.chr.admin.controller.admin;


import com.chr.admin.pojo.dto.*;
import com.chr.admin.service.AdminUserService;
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


@RestController("adminUserController")
@RequestMapping("/admin/user")
@CrossOrigin
@Tag(name = "用户接口")
@Slf4j
public class UserController {

    @Autowired
    private AdminUserService adminUserService;




    @PostMapping("/login")
    @Operation(summary = "登录接口")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        log.info("用户登录：{}",userLoginDTO);
        Result result = adminUserService.login(userLoginDTO);
        return result;
    }

    @PostMapping("/logout")
    @Operation(summary = "退出接口")
    public Result logout(){
        log.info("用户退出");
        Result result = adminUserService.logout();
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "注册接口")
    public Result register(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        log.info("用户注册：{}",userRegisterDTO);
        Result result = adminUserService.register(userRegisterDTO);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "信息接口")
    public Result getUserInfo(){
        log.info("获取用户信息");
        Result result = adminUserService.info();
        return result;
    }





    @GetMapping
    @Operation(summary = "用户列表分页接口")
    public Result getUserListPage(@ParameterObject UserPageQueryDTO userPageQueryDTO){
        log.info("用户列表分页接口：{}",userPageQueryDTO);
        Result result = adminUserService.getUserListPage(userPageQueryDTO);
        return  result;
    }

    @CacheEvict(value = "user",key = "#userUpdateDTO.id")
    @PutMapping
    @Operation(summary = "修改用户接口")
    public Result updateUser(@RequestBody @Valid UserUpdateDTO userUpdateDTO){
        log.info("根据id修改用户接口：{}",userUpdateDTO);
        Result result = adminUserService.updateUser(userUpdateDTO);
        return result;
    }

    @PostMapping
    @Operation(summary = "添加用户接口")
    public Result addUser(@RequestBody @Valid UserRegisterDTO userRegisterDTO){
        log.info("添加用户接口：{}", userRegisterDTO);
        Result result = adminUserService.addUser(userRegisterDTO);
        return result;
    }

    @Cacheable(value = "user",key = "#id")
    @GetMapping("/{id}")
    @Operation(summary = "获取用户接口")
    public Result getUserById(@PathVariable Long id){
        log.info("获取用户接口：{}",id);
        Result result = adminUserService.getUser(id);
        return result;
    }


}
