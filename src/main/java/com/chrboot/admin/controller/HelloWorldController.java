package com.chrboot.admin.controller;


import com.chrboot.admin.exception.BizException;
import com.chrboot.admin.exception.BizExceptionEnume;
import com.chrboot.admin.pojo.vo.req.UserAddVo;
import com.chrboot.admin.service.UserService;
import com.chrboot.admin.utils.Result;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("world")
public class HelloWorldController {

    @GetMapping
    public Result helloWorld(@Valid UserAddVo userAddVo){
        return Result.ok("hello world");
    }


}
