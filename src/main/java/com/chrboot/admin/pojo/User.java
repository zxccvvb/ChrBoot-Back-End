package com.chrboot.admin.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
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
    private Integer isDeleted;

    //配置Version添加乐观锁
    @Version
    private Integer version;

    private Date createTime;

    private Date updateTime;
}