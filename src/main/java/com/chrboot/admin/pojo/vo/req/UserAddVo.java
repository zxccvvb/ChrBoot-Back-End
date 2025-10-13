package com.chrboot.admin.pojo.vo.req;


import com.chrboot.admin.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddVo {


    @NotBlank(message = "姓名不能为空")
    private String username;

    @Password //自定义属性校验注解
    private String password;
}
