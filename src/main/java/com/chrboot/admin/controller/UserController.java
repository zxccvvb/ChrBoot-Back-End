package com.chrboot.admin.controller;


import com.chrboot.admin.pojo.User;
import com.chrboot.admin.pojo.vo.req.PageVo;
import com.chrboot.admin.pojo.vo.req.UserAddVo;
import com.chrboot.admin.pojo.vo.req.UserUpdateVo;
import com.chrboot.admin.pojo.vo.resp.PageRespVo;
import com.chrboot.admin.pojo.vo.resp.UserRespVo;
import com.chrboot.admin.service.UserService;
import com.chrboot.admin.utils.Result;
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

    @PostMapping
    public Result register(@RequestBody @Valid UserAddVo user){
        Result result = userService.register(user);
        return result;
    }

    @PutMapping
    public Result updateUser(@RequestBody @Valid UserUpdateVo user){
        Result result = userService.updateUser(user);
        return result;
    }


}
