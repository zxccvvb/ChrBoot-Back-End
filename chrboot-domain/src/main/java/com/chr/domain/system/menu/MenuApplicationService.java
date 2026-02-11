package com.chr.domain.system.menu;

import com.chr.domain.system.menu.db.SysMenu;
import com.chr.domain.system.menu.db.SysMenuService;
import com.chr.domain.system.menu.vo.SysMenuVO;
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


    public List<SysMenuVO> getRoutesByUserId(Long userId){

        List<SysMenu> sysMenus = sysMenuService.getRoutesByUserId(userId);
        List<SysMenuVO> sysMenuVOS = new ArrayList<>();
        for(SysMenu sysMenu : sysMenus){
            SysMenuVO sysMenuVO = new SysMenuVO();
            BeanUtils.copyProperties(sysMenu, sysMenuVO);
            sysMenuVOS.add(sysMenuVO);
        }
        return sysMenuVOS;
    }
}
