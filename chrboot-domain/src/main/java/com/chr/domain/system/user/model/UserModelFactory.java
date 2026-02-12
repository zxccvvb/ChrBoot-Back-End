package com.chr.domain.system.user.model;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.user.db.SysRoleUserEntity;
import com.chr.domain.system.user.db.SysRoleUserService;
import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.db.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户模型工厂
 */
@Component
@RequiredArgsConstructor
public class UserModelFactory {


    private final SysUserService sysUserService;
    private final SysRoleUserService sysRoleUserService;

    public UserModel loadById(Long userId) {
        SysUserEntity byId = sysUserService.getById(userId);
        if (byId == null) {
            throw new ApiException(ErrorCode.Business.COMMON_OBJECT_NOT_FOUND, userId, "用户");
        }

        LambdaQueryWrapper<SysRoleUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleUserEntity::getUserId, userId);
        List<Long> roleIds = sysRoleUserService.list(queryWrapper).stream().map(SysRoleUserEntity::getRoleId)
                .collect(Collectors.toList());

        UserModel userModel = new UserModel(byId, sysUserService,sysRoleUserService);

        userModel.setRoleIds(roleIds);

        return userModel;
    }

    public UserModel create() {
        return new UserModel(sysUserService,sysRoleUserService);
    }
}
