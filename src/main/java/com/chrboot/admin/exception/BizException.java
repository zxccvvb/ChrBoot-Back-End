package com.chrboot.admin.exception;


import lombok.Data;

@Data
public class BizException extends RuntimeException{


    private Integer code;
    private String msg;


    public BizException(Integer code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public BizException(BizExceptionEnume bizExceptionEnume){
        super(bizExceptionEnume.getMsg());
        this.code = bizExceptionEnume.getCode();
        this.msg = bizExceptionEnume.getMsg();
    }
}
