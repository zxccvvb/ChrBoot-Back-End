package com.chrboot.admin.pojo.vo.req;


import lombok.Data;

@Data
public class UserUpdateVo {

    private Integer id;
    private String username;
    private String password;
}
