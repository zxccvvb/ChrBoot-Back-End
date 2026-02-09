package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.common.result.PageResult;
import com.chr.common.result.Result;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.dto.SysUserPageQueryDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.dto.SysUserUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result getUserListPage(SysUserPageQueryDTO sysUserPageQueryDTO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(sysUserPageQueryDTO.getNickname()!=null, SysUser::getNickname, sysUserPageQueryDTO.getNickname());
        IPage<SysUser> page = new Page(sysUserPageQueryDTO.getPageNum(), sysUserPageQueryDTO.getPageSize());
        sysUserMapper.selectPage(page, queryWrapper);
        List<SysUser> sysUserList = page.getRecords();
        PageResult<SysUser> pageResult = new PageResult<>(page.getTotal(), sysUserList);
        return Result.ok(pageResult);
    }

    @Override
    public Result updateUser(SysUserUpdateDTO sysUserUpdateDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserUpdateDTO, sysUser);
        int rows = sysUserMapper.updateById(sysUser);
        if(rows==0){
            throw new ApiException(ErrorCode.Business.USER_UPDATE_ERROR);
        }
        return Result.ok("");
    }


    @Override
    public Result addUser(SysUserRegisterDTO sysUserRegisterDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRegisterDTO, sysUser);
        int rows = sysUserMapper.insert(sysUser);
        if(rows>0){
            return Result.ok(null);
        }
        throw new ApiException(ErrorCode.Business.USER_ADD_ERROR);
    }

    @Override
    public Result getUser(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        return Result.ok(sysUser);
    }
}