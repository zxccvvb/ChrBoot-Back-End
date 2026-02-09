package com.chr.api.service;

import com.chr.common.result.Result;
import com.chr.domain.system.user.dto.SysUserLoginDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;

public interface AppUserService {

    /**
     * 登录接口
     * @param sysUserLoginDTO
     * @return
     */
    Result login(SysUserLoginDTO sysUserLoginDTO);

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
     * @param sysUserRegisterDTO
     * @return
     */
    Result register(SysUserRegisterDTO sysUserRegisterDTO);
}
