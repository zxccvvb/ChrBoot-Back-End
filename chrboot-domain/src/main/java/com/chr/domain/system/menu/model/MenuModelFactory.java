package com.chr.domain.system.menu.model;


import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.menu.db.SysMenuEntity;
import com.chr.domain.system.menu.db.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MenuModelFactory {

    private final SysMenuService sysMenuService;

    public MenuModel loadById(Long id){
        SysMenuEntity byId = sysMenuService.getById(id);
        if (byId == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, id, "菜单");
        }
        MenuModel model = new MenuModel(byId, sysMenuService);

        return model;

    }

    public MenuModel create() {
        return new MenuModel(sysMenuService);
    }

}
