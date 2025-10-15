package com.chr.admin.controller;


import com.chr.admin.exception.BizException;
import com.chr.admin.exception.BizExceptionEnume;
import com.chr.admin.mapper.UserMapper;
import com.chr.admin.pojo.User;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.service.UserService;
import com.chr.admin.utils.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("world")
@Slf4j
public class HelloWorldController {


    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public void helloWorld(){

    }


}
