package com.chr.admin.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.admin.mapper.SysAdminMenuMapper;
import com.chr.admin.mapper.UserMapper;
import com.chr.admin.pojo.User;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {



    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysAdminMenuMapper sysAdminMenuMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        User newUser = new User();
        AuthDetails authDetails = (AuthDetails) user;
        BeanUtils.copyProperties(authDetails.getAuth(),newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userMapper.insert(newUser);
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
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,username));
        if(user==null){
            throw new ApiException(ErrorCode.Business.LOGIN_NOTFOUND_ERROR);
        }else{
            List<String> permissions = sysAdminMenuMapper.selectPermissionsByUserId(user.getId());
            return new AuthDetails(user,permissions);
        }
    }
}
