package com.chr.admin.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {



    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        User loginUser = new User();
        AuthDetails<User> authDetails = (AuthDetails<User>) user;
        BeanUtils.copyProperties(authDetails.getAuth(),loginUser);
        loginUser.setPassword(passwordEncoder.encode(loginUser.getPassword()));
        userMapper.insert(loginUser);
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
        User employee = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername,username));
        if(employee==null){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_NOTFOUND_ERROR);
        }else{
            //TODO 查询权限
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("admin","test"));
            return new AuthDetails(employee,arrayList);
        }
    }
}
