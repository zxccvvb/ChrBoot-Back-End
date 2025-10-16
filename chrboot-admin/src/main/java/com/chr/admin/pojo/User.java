package com.chr.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * @TableName chr_user
 */
@TableName(value ="chr_user")
@Data
public class User {


    @TableId
    private Integer id;

    private String username;

    private String password;

    //全局设置了删除的字段名
    private Boolean isDeleted;

    //配置Version添加乐观锁
    @Version
    private Integer version;

    //这里添加全局消息转换器
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}