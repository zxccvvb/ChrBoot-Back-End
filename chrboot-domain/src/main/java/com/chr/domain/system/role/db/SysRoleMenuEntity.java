package com.chr.domain.system.role.db;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName sys_role_menu
 */
@TableName(value ="sys_role_menu")
@Data
public class SysRoleMenuEntity {
    private Long roleId;

    private Long menuId;
}