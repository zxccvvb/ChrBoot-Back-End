package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.common.result.Result;
import com.chr.domain.system.user.dto.SysUserPageQueryDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.dto.SysUserUpdateDTO;

public interface SysUserService extends IService<SysUser> {

    /**
     * 获取所有用户列表
     *
     * 分页
     * @param sysUserPageQueryDTO
     * @return
     */
    Result getUserListPage(SysUserPageQueryDTO sysUserPageQueryDTO);


    /**
     * 修改用户信息
     * @param sysUserUpdateDTO
     * @return
     */
    Result updateUser(SysUserUpdateDTO sysUserUpdateDTO);


    /**
     *添加新用户
     * @param sysUserRegisterDTO
     * @return
     */
    Result addUser(SysUserRegisterDTO sysUserRegisterDTO);

    /**
     * 获取用户
     * @param id
     * @return
     */
    Result getUser(Long id);
}
