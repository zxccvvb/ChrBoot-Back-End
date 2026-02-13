package com.chr.domain.system.role.model;

import cn.hutool.core.bean.BeanUtil;
import com.chr.domain.system.role.db.SysRoleEntity;
import com.chr.domain.system.role.db.SysRoleMenuService;
import com.chr.domain.system.role.db.SysRoleService;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.command.UpdateUserCommand;
import lombok.Data;

import java.util.List;


@Data
public class RoleModel extends SysRoleEntity {

    private SysRoleMenuService sysRoleMenuService;
    private SysRoleService sysRoleService;
    private List<Long> menuIds;


    public RoleModel(SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService) {
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
    }


    public RoleModel(SysRoleEntity entity, SysRoleService sysRoleService, SysRoleMenuService sysRoleMenuService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.sysRoleService = sysRoleService;
        this.sysRoleMenuService = sysRoleMenuService;
    }



    public void loadAddCommand(AddUserCommand command){
        if(command != null){
            BeanUtil.copyProperties(command, this);
        }
    }

    public void loadUpdateCommand(UpdateUserCommand command){
        if(command != null){
            BeanUtil.copyProperties(command, this);
        }
    }


    public void insert(){
        SysRoleEntity entity = new SysRoleEntity();
        BeanUtil.copyProperties(this, entity);
        sysRoleService.save(entity);
    }

    public void updateById() {
        SysRoleEntity entity = new SysRoleEntity();
        BeanUtil.copyProperties(this, entity);
        sysRoleService.updateById(entity);
    }

    public void deleteById() {
        SysRoleEntity entity = new SysRoleEntity();
        BeanUtil.copyProperties(this, entity);
        sysRoleService.removeById(this.getRoleId());
    }
}
