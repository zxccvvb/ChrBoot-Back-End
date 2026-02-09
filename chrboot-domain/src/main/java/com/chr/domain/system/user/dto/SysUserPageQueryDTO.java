package com.chr.domain.system.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserPageQueryDTO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String nickname;
}
