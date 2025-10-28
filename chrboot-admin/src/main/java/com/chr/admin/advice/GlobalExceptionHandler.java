package com.chr.admin.advice;


import com.chr.common.exception.BizException;
import com.chr.common.result.Result;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理器
 * 异常处理规则：本类>全局
 */


//Slf4j slf4j+logback SpringBoot默认使用的日志系统 加上Slf4j可以直接使用log调用输出
@Slf4j
//ControllerAdvice说明组件是专门处理全局异常的
//RestControllerAdvice = ControllerAdvice + ResponseBody 返回文本
@RestControllerAdvice
//避免knife4j无法显示的问题
@Hidden
public class GlobalExceptionHandler {


    //全局处理jsr303参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleArithmeticException(MethodArgumentNotValidException e){
        //异常打印错误堆栈
        //e.printStackTrace();
        log.error(e.getMessage());
        //这里循环获取参数报错原因
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        Map<String,String> map = new HashMap<>();
        for(FieldError fieldError : fieldErrors){
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        return Result.build(map,500,"参数异常");
    }

    //数据库重复值异常
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Result handlerDataIntegrityViolationException(DataIntegrityViolationException err){
        SQLIntegrityConstraintViolationException e = (SQLIntegrityConstraintViolationException) err.getCause();
        log.error(e.getMessage());
        if(e.getMessage().contains("Duplicate entry")){
            String[] split = e.getMessage().split(" ");
            String message = split[2] + "已存在";
            return Result.build(null,500,message);
        }
        return Result.build(null,500,e.getMessage());
    }

    //数据库查询为空
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handlerIllegalArgumentException(IllegalArgumentException e){
        log.error(e.getMessage());
        String message = "查询结果为空";
        return Result.build(null,500,message);
    }


    //业务异常处理
    @ExceptionHandler(BizException.class)
    public Result handlerBizException(BizException e){
        log.error(e.getMessage());
        return Result.build(null,e.getCode(),e.getMessage());
    }


    //所有异常最后的处理规则
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        //异常打印错误堆栈
        e.printStackTrace();
        log.error(e.getMessage());
        return Result.build(null,500,e.getMessage());
    }



}
