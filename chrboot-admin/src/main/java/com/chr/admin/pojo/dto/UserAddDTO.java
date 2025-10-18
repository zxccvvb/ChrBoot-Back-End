package com.chr.admin.pojo.dto;

import com.chr.common.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Data
public class UserAddDTO implements Serializable {

    @NotBlank
    private String nickname;

    @NotNull
    @Size(min = 5,max = 10, message = "用户名必须在5-10个字符之间")
    private String username;

    @NotBlank
    @Password //自定义属性校验注解
    private String password;
}
