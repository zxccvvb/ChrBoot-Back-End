package com.chr.api.service;

import com.chr.common.result.Result;
import com.chr.domain.system.user.command.LoginUserCommand;
import com.chr.domain.system.user.command.RegisterUserCommand;

public interface AppUserService {

    /**
     * 登录接口
     * @param loginUserCommand
     * @return
     */
    Result login(LoginUserCommand loginUserCommand);

    /**
     * 退出登录接口
     * @return
     */
    Result logout();


    /**
     * 信息接口
     * @return
     */
    Result info();

    /**
     * 注册接口
     * @param registerUserCommand
     * @return
     */
    Result register(RegisterUserCommand registerUserCommand);
}
