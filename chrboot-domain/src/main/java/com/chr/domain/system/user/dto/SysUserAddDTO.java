package com.chr.domain.system.user.dto;

import com.chr.common.annotation.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserAddDTO implements Serializable {

    @NotBlank
    private String nickname;

    @NotBlank
    @Size(min = 5,max = 10, message = "用户名必须在5-10个字符之间")
    private String username;

    @NotBlank
    @Password
    private String password;

    private Integer status;
}
