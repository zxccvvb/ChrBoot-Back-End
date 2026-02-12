package com.chr.domain.system.user.model;

import cn.hutool.core.bean.BeanUtil;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.command.UpdateUserCommand;
import com.chr.domain.system.user.db.SysRoleUserService;
import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.db.SysUserService;
import lombok.Data;

import java.util.List;

@Data
public class UserModel extends SysUserEntity {

    private SysUserService sysUserService;
    private SysRoleUserService sysRoleUserService;
    private List<Long> roleIds;


    public UserModel(SysUserService sysUserService, SysRoleUserService sysRoleUserService) {
        this.sysUserService = sysUserService;
        this.sysRoleUserService = sysRoleUserService;
    }


    public UserModel(SysUserEntity entity, SysUserService sysUserService, SysRoleUserService sysRoleUserService) {
        if (entity != null) {
            BeanUtil.copyProperties(entity, this);
        }
        this.sysUserService = sysUserService;
        this.sysRoleUserService = sysRoleUserService;
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
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtil.copyProperties(this, sysUserEntity);
        sysUserService.addUser(sysUserEntity);
    }

    public void updateById() {
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtil.copyProperties(this, sysUserEntity);
        sysUserService.updateById(sysUserEntity);
    }

    public void deleteById() {
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtil.copyProperties(this, sysUserEntity);
        sysUserService.removeById(this.getId());
    }


}
