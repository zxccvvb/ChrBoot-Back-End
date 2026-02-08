package com.chr.admin.pojo.vo;

import lombok.Data;

@Data
public class SysAdminMenuVO {
    private Long menuId;

    private String menuName;

    private Integer menuType;

    private String routerName;

    private Long parentId;

    private String path;

    private Integer isButton;

    private String metaInfo;

    private String remark;
}
