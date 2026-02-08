package com.chr.admin.pojo;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName sys_admin_menu
 */
@Data
public class SysAdminMenu {
    private Long menuId;

    private String menuName;

    private Integer menuType;

    private String routerName;

    private Long parentId;

    private String path;

    private Integer isButton;

    private String permission;

    private String metaInfo;

    private String remark;

    private Integer status;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long createUser;

    private Long updateUser;
}