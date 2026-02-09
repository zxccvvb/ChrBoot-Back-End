package com.chr.domain.system.menu.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chr.domain.system.menu.db.SysMenu;

import java.util.List;



public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<String> selectPermissionsByUserId(Long userId);
    List<SysMenu> selectRoutesByUserId(Long userId);
}




