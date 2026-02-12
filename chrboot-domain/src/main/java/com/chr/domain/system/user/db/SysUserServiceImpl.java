package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.query.UserQuery;
import com.chr.domain.system.user.command.UpdateUserCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity>
    implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public IPage<SysUserEntity> getUserListPage(UserQuery userQuery) {
        LambdaQueryWrapper<SysUserEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userQuery.getNickname()!=null, SysUserEntity::getNickname, userQuery.getNickname());
        IPage<SysUserEntity> page = new Page(userQuery.getPageNum(), userQuery.getPageSize());
        sysUserMapper.selectPage(page, queryWrapper);
        return page;
    }


    @Override
    public void addUser(SysUserEntity entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        int rows = sysUserMapper.insert(entity);
        if(rows==0){
            throw new ApiException(ErrorCode.Business.USER_ADD_ERROR);
        }
    }

}