package com.chr.admin.pojo.vo;

import lombok.Data;

@Data
public class UserVo {
    private Long id;
    private String nickname;
    private String username;
    private Integer status;
}
