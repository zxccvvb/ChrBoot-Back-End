package com.chr.admin.controller;


import com.chr.admin.pojo.vo.req.PageVo;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.admin.service.UserService;
import com.chr.admin.utils.Result;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result getUserListPage(PageVo pageVo){
        Result result = userService.getUserListPage(pageVo);
        return  Result.ok(result);
    }

    @PostMapping("login")
    public Result login(@RequestBody @Valid UserAddVo userAddVo){
        Result result = userService.login(userAddVo);
        return result;
    }

    @PostMapping("register")
    public Result register(@RequestBody @Valid UserAddVo userAddVo){
        Result result = userService.register(userAddVo);
        return result;
    }

    @PutMapping
    public Result updateUser(@RequestBody @Valid UserUpdateVo userUpdateVo){
        System.out.println(userService.getClass());
        Result result = userService.updateUser(userUpdateVo);
        return result;
    }


}
