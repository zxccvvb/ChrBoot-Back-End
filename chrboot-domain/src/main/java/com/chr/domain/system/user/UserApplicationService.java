package com.chr.domain.system.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.common.result.PageResult;
import com.chr.common.result.Result;
import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.db.SysUserService;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.dto.SysUserAddDTO;
import com.chr.domain.system.user.dto.SysUserPageQueryDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.dto.SysUserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserApplicationService {

    private final SysUserService sysUserService;

    public PageResult<SysUser> getUserListPage(SysUserPageQueryDTO sysUserPageQueryDTO) {
        IPage<SysUser> page = sysUserService.getUserListPage(sysUserPageQueryDTO);
        List<SysUser> sysUserList = page.getRecords();
        PageResult<SysUser> pageResult = new PageResult<>(page.getTotal(), sysUserList);
        return pageResult;
    }

    public void updateUser(SysUserUpdateDTO sysUserUpdateDTO) {
        sysUserService.updateUser(sysUserUpdateDTO);
    }


    public void addUser(SysUserAddDTO sysUserAddDTO) {
        sysUserService.addUser(sysUserAddDTO);
    }

    public SysUser getUser(Long id) {
        SysUser sysUser = sysUserService.getUser(id);
        return sysUser;
    }

}
