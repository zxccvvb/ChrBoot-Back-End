package com.chr.admin.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserPageQueryDTO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String nickname;
}
