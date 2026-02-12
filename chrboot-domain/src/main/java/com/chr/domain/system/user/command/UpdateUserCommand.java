package com.chr.domain.system.user.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class UpdateUserCommand{

    @NotNull
    private Long id;

    @NotBlank
    private String nickname;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Integer status;
}
