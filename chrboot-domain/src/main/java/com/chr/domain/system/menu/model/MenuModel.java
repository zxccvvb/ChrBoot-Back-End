package com.chr.domain.system.menu.model;

import cn.hutool.core.bean.BeanUtil;
import com.chr.domain.system.menu.db.SysMenuEntity;
import com.chr.domain.system.menu.db.SysMenuService;
import com.chr.domain.system.role.db.SysRoleEntity;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.command.UpdateUserCommand;
import lombok.Data;


@Data
public class MenuModel extends SysMenuEntity {

    private SysMenuService sysMenuService;


    public MenuModel(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }


    public MenuModel(SysMenuEntity entity, SysMenuService sysMenuService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.sysMenuService = sysMenuService;
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
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtil.copyProperties(this, entity);
        sysMenuService.save(entity);
    }

    public void updateById() {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtil.copyProperties(this, entity);
        sysMenuService.updateById(entity);
    }

    public void deleteById() {
        SysMenuEntity entity = new SysMenuEntity();
        BeanUtil.copyProperties(this, entity);
        sysMenuService.removeById(this.getMenuId());
    }

}
