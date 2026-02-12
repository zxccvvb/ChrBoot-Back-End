package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="sys_user")
@Data
public class SysUserEntity{


    @TableId(value = "user_id")
    private Long id;

    private String nickname;
    private String username;
    private String password;
    private Integer status;
    private Integer userType;

    //全局设置了删除的字段名
    private Boolean isDeleted;
    //这里添加全局消息转换器
    private Long createUser;
    private Long updateUser;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}