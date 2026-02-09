package com.chr.admin.customize.service.login;

import com.baomidou.mybatisplus.extension.service.IService;

import com.chr.common.result.Result;

import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.dto.SysUserLoginDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;


public interface LoginUserService extends IService<SysUser> {

    /**
     * 用户登录接口
     * @param sysUserLoginDTO
     * @return
     */
    public Result login(SysUserLoginDTO sysUserLoginDTO) ;
    public Result logout();
    /**
     * 用户信息接口
     * @return
     */
    public Result info();
    /**
     * 用户注册接口
     * @param sysUserRegisterDTO
     * @return
     */
    public Result register(SysUserRegisterDTO sysUserRegisterDTO);


}
