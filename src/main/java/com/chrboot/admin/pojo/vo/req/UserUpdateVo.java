package com.chrboot.admin.pojo.vo.req;


import com.chrboot.admin.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateVo {


    @NotNull(message = "id不可以为空")
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String username;
    @Password
    private String password;

}
