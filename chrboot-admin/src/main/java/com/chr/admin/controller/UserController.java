package com.chr.admin.controller;


import com.chr.admin.pojo.vo.req.PageVo;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.admin.service.UserService;
import com.chr.admin.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin
@Tag(name = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "用户列表分页接口")
    public Result getUserListPage(PageVo pageVo){
        Result result = userService.getUserListPage(pageVo);
        return  Result.ok(result);
    }

    @PostMapping("login")
    @Operation(summary = "用户登录接口")
    public Result login(@RequestBody @Valid UserAddVo userAddVo){
        Result result = userService.login(userAddVo);
        return result;
    }

    @PostMapping("register")
    @Operation(summary = "用户注册接口")
    public Result register(@RequestBody @Valid UserAddVo userAddVo){
        Result result = userService.register(userAddVo);
        return result;
    }

    @PutMapping
    @Operation(summary = "用户更新接口")
    public Result updateUser(@RequestBody @Valid UserUpdateVo userUpdateVo){
        System.out.println(userService.getClass());
        Result result = userService.updateUser(userUpdateVo);
        return result;
    }


}
