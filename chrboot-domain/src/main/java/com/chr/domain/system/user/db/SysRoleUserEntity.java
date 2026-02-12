package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName sys_role_user
 */
@TableName(value ="sys_role_user")
@Data
public class SysRoleUserEntity {

    private Long roleId;

    private Long userId;
}