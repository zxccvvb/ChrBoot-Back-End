package com.chr.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName chr_user
 */
@TableName(value ="chr_user")
@Data
public class User implements Serializable {


    @TableId
    private Long id;

    private String nickname;
    private String username;
    private String password;
    private Integer status;

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