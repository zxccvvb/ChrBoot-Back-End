package com.chr.admin.pojo.vo.req;


import com.chr.admin.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddVo {


    @NotNull
    @Size(min = 5,max = 10, message = "用户名必须在5-10个字符之间")
    private String username;

    @NotNull
    @Password //自定义属性校验注解
    private String password;
}
