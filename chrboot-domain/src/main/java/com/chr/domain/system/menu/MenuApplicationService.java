package com.chr.domain.system.menu;

import com.chr.domain.system.menu.db.SysMenuEntity;
import com.chr.domain.system.menu.db.SysMenuService;
import com.chr.domain.system.menu.vo.MenuVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuApplicationService {
    private final SysMenuService sysMenuService;

    public List<String> getPermissionsByUserId(Long userId){
        return sysMenuService.getPermissionsByUserId(userId);
    }


    public List<MenuVO> getRoutesByUserId(Long userId){
        List<SysMenuEntity> sysMenuEntities = sysMenuService.getRoutesByUserId(userId);
        List<MenuVO> menuVOS = new ArrayList<>();
        for(SysMenuEntity sysMenuEntity : sysMenuEntities){
            MenuVO menuVO = new MenuVO();
            BeanUtils.copyProperties(sysMenuEntity, menuVO);
            menuVOS.add(menuVO);
        }
        return menuVOS;
    }
}
