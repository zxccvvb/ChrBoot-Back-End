package com.chr.admin.exception;

import lombok.Getter;


public enum BizExceptionEnume {


    USER_EXIST_ERROR(10001,"用户已存在"),
    USER_REGISTER_ERROR(10002,"用户注册失败"),
    USER_UPDATE_ERROR(10003,"用户修改失败"),
    USER_LOGIN_ERROR(10004,"用户未登录"),
    USER_NOT_EXIST_ERROR(10005,"用户名不存在"),
    USER_PASSWORD_ERROR(10006,"用户密码错误"),
    USER_TOKEN_ERROR(10005,"token已经过期");


    @Getter
    private Integer code;
    @Getter
    private String msg;

    private BizExceptionEnume(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
