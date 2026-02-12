package com.chr.admin.customize.service.login;

import com.baomidou.mybatisplus.extension.service.IService;

import com.chr.common.result.Result;

import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.command.LoginUserCommand;
import com.chr.domain.system.user.command.RegisterUserCommand;


public interface LoginUserService extends IService<SysUserEntity> {

    /**
     * 用户登录接口
     * @param loginUserCommand
     * @return
     */
    public Result login(LoginUserCommand loginUserCommand) ;

    /**
     * 用户登出接口
     * @return
     */
    public Result logout();
    /**
     * 用户信息接口
     * @return
     */
    public Result info();
    /**
     * 用户注册接口
     * @param registerUserCommand
     * @return
     */
    public Result register(RegisterUserCommand registerUserCommand);


}
