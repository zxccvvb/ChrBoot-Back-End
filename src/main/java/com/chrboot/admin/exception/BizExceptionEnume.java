package com.chrboot.admin.exception;

import lombok.Getter;


public enum BizExceptionEnume {


    TEMPLATE_FIRST("10001","第一个错误"),
    TEMPLATE_SECOND("10002","第二个错误");


    @Getter
    private Integer code;
    @Getter
    private String msg;

    private BizExceptionEnume(String code, String msg){
        this.code = Integer.valueOf(code);
        this.msg = msg;
    }

}
