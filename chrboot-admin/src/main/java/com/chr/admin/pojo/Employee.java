package com.chr.admin.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chr.admin.security.Auth;
import lombok.Data;

/**
 * @TableName employee
 */
@TableName(value ="sys_employee")
@Data
public class Employee implements Serializable , Auth {
    @TableId(value = "employee_id")
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