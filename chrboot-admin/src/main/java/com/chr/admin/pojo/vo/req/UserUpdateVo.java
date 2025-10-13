package com.chr.admin.pojo.vo.req;


import com.chr.admin.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateVo {


    @NotNull(message = "id不可以为空")
    private Integer id;
    @Size(min = 5,max = 10, message = "用户名必须在5-10个字符之间")
    private String username;
    @Password
    private String password;

}
