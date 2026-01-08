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

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Integer status;
}
