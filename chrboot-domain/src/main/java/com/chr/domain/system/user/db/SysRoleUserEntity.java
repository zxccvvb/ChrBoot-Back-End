package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName sys_role_user
 */
@TableName(value ="sys_role_user")
@Data
public class SysRoleUserEntity {

    @TableField("role_id")
    private Long roleId;
    @TableField("user_id")
    private Long userId;
}