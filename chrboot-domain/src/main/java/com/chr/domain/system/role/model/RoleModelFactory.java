package com.chr.domain.system.role.model;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.role.db.SysRoleEntity;
import com.chr.domain.system.role.db.SysRoleMenuEntity;
import com.chr.domain.system.role.db.SysRoleMenuService;
import com.chr.domain.system.role.db.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleModelFactory {

    private final SysRoleMenuService sysRoleMenuService;
    private final SysRoleService sysRoleService;

    public RoleModel loadById(Long id){
        SysRoleEntity byId = sysRoleService.getById(id);
        if (byId == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, id, "角色");
        }

        LambdaQueryWrapper<SysRoleMenuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenuEntity::getRoleId, id);
        List<Long> menuIds = sysRoleMenuService.list(queryWrapper).stream().map(SysRoleMenuEntity::getMenuId)
                .collect(Collectors.toList());

        RoleModel roleModel = new RoleModel(byId, sysRoleService,sysRoleMenuService);

        roleModel.setMenuIds(menuIds);

        return roleModel;

    }

    public RoleModel create() {
        return new RoleModel(sysRoleService,sysRoleMenuService);
    }
}
