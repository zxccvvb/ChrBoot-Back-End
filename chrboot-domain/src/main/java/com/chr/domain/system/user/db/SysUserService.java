package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.domain.system.user.dto.SysUserAddDTO;
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
    IPage<SysUser> getUserListPage(SysUserPageQueryDTO sysUserPageQueryDTO);


    /**
     * 修改用户信息
     * @param sysUserUpdateDTO
     * @return
     */
    void updateUser(SysUserUpdateDTO sysUserUpdateDTO);


    /**
     *添加新用户
     * @param sysUserAddDTO
     * @return
     */
    void addUser(SysUserAddDTO sysUserAddDTO);

    /**
     * 获取用户
     * @param id
     * @return
     */
    SysUser getUser(Long id);
}
