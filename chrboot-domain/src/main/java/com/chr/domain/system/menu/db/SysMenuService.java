package com.chr.domain.system.menu.db;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户id获取用户权限
     * @param userId
     * @return
     */
    List<String> getPermissionsByUserId(Long userId);


    /**
     * 根据用户id获取菜单列表
     * @param userId
     * @return
     */
    List<SysMenu> getRoutesByUserId(Long userId);
}
