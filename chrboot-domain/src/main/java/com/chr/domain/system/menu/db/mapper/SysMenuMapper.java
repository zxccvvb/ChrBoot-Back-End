package com.chr.domain.system.menu.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chr.domain.system.menu.db.SysMenuEntity;

import java.util.List;



public interface SysMenuMapper extends BaseMapper<SysMenuEntity> {
    List<String> selectPermissionsByUserId(Long userId);
    List<SysMenuEntity> selectRoutesByUserId(Long userId);
}




