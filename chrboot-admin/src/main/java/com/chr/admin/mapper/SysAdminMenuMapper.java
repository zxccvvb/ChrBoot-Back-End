package com.chr.admin.mapper;

import com.chr.admin.pojo.SysAdminMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 19774
* @description 针对表【sys_admin_menu】的数据库操作Mapper
* @createDate 2026-02-08 10:49:04
* @Entity com.chr.admin.pojo.SysAdminMenu
*/
public interface SysAdminMenuMapper extends BaseMapper<SysAdminMenu> {
    List<String> selectPermissionsByUserId(Long userId);
    List<SysAdminMenu> selectRoutesByUserId(Long userId);
}




