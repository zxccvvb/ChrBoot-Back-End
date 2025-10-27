package com.chr.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class UserVO implements Serializable {
    private Long id;

    private String nickname;
    private String username;
    private String password;
    private Integer status;
    private Long createUser;
    private Long updateUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
