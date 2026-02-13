package com.chr.domain.system.role.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName sys_role
 */
@TableName(value ="sys_role")
@Data
public class SysRoleEntity {
    private Long roleId;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private Integer dataScope;

    private String deptIdSet;

    private String remark;

    private Integer status;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}