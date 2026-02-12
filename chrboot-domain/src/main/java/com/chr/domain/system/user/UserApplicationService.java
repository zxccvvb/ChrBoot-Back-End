package com.chr.domain.system.user;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chr.common.result.PageResult;
import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.db.SysUserService;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.model.UserModel;
import com.chr.domain.system.user.model.UserModelFactory;
import com.chr.domain.system.user.query.UserQuery;
import com.chr.domain.system.user.command.UpdateUserCommand;
import com.chr.domain.system.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final SysUserService sysUserService;
    private final UserModelFactory userModelFactory;

    public PageResult<SysUserEntity> getUserListPage(UserQuery userQuery) {
        IPage<SysUserEntity> page = sysUserService.getUserListPage(userQuery);
        List<SysUserEntity> sysUserEntityList = page.getRecords();
        PageResult<SysUserEntity> pageResult = new PageResult<>(page.getTotal(), sysUserEntityList);
        return pageResult;
    }

    public void updateUser(UpdateUserCommand command) {
        UserModel userModel = userModelFactory.create();
        userModel.loadUpdateCommand(command);
        userModel.updateById();
    }


    public void addUser(AddUserCommand command) {
        UserModel userModel = userModelFactory.create();
        userModel.loadAddCommand(command);
        userModel.insert();
    }

    public UserVo getUser(Long id) {
        SysUserEntity sysUserEntity = sysUserService.getById(id);
        return new UserVo(sysUserEntity);
    }

}
