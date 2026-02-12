package com.chr.api.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.menu.db.mapper.SysMenuMapper;
import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.login.AuthDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {



    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        SysUserEntity newSysUserEntity = new SysUserEntity();
        AuthDetails authDetails = (AuthDetails) user;
        BeanUtils.copyProperties(authDetails.getAuth(), newSysUserEntity);
        newSysUserEntity.setPassword(passwordEncoder.encode(newSysUserEntity.getPassword()));
        sysUserMapper.insert(newSysUserEntity);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        SysUserEntity sysUserEntity = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUserEntity>()
                .eq(SysUserEntity::getUsername,username));
        if(sysUserEntity ==null){
            throw new ApiException(ErrorCode.Business.LOGIN_NOTFOUND_ERROR);
        }else{
            List<String> permissions = sysMenuMapper.selectPermissionsByUserId(sysUserEntity.getId());
            return new AuthDetails(sysUserEntity,permissions);
        }
    }
}
