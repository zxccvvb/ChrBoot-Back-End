package com.chr.admin.customize.service.login;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.domain.system.menu.db.mapper.SysMenuMapper;
import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.login.AuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername,username));
        if(sysUser ==null){
            throw new ApiException(ErrorCode.Business.LOGIN_NOTFOUND_ERROR);
        }else{
            List<String> permissions = sysMenuMapper.selectPermissionsByUserId(sysUser.getId());
            return new AuthDetails(sysUser,permissions);
        }
    }
}
