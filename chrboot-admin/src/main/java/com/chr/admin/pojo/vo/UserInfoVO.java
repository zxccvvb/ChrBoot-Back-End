package com.chr.admin.pojo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO implements Serializable {
    private Long id;
    private String nickname;
    private String username;
    private Integer status;
}
