package com.chrboot.admin.exception;

import lombok.Getter;


public enum BizExceptionEnume {


    USERNAME_EXIST(10001,"用户名已经被注册"),
    USER_REGISTER_ERROR(10002,"用户注册失败"),
    USER_UPDATE_ERROR(10003,"用户修改失败");


    @Getter
    private Integer code;
    @Getter
    private String msg;

    private BizExceptionEnume(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
