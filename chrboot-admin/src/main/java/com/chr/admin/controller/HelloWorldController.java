package com.chr.admin.controller;


import com.chr.admin.exception.BizException;
import com.chr.admin.exception.BizExceptionEnume;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.service.UserService;
import com.chr.admin.utils.Result;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("world")
@Slf4j
public class HelloWorldController {

    @GetMapping
    public void helloWorld(){

        while (true){
            log.info("666666666666666666666666666");
        }
    }


}
