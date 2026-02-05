package com.chr.admin.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @TableName chr_employee
 */
@Data
public class Employee implements Serializable {
    @TableId
    private Long id;

    private String nickname;

    private String username;

    private String password;

    private Integer status;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}