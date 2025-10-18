package com.chr.admin.controller;


import com.chr.admin.pojo.dto.UserAddDTO;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserUpdateDto;
import com.chr.admin.pojo.vo.req.PageVo;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.admin.service.UserService;
import com.chr.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@CrossOrigin
@Tag(name = "用户接口")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "用户列表分页接口")
    public Result getUserListPage(PageVo pageVo){
        Result result = userService.getUserListPage(pageVo);
        return  result;
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录接口")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO){
        Result result = userService.login(userLoginDTO);
        return result;
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册接口")
    public Result register(@RequestBody @Valid UserAddDTO userAddDTO){
        Result result = userService.register(userAddDTO);
        return result;
    }

    @PutMapping
    @Operation(summary = "根据id修改用户接口")
    public Result updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto){
        Result result = userService.updateById(userUpdateDto);
        return result;
    }


}
