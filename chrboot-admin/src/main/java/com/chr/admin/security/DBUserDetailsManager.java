package com.chr.admin.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.admin.mapper.EmployeeMapper;
import com.chr.admin.pojo.Employee;
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
    private EmployeeMapper employeeMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        Employee employee = new Employee();
        AuthDetails<Employee> authDetails = (AuthDetails<Employee>) user;
        BeanUtils.copyProperties(authDetails.getAuth(),employee);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employeeMapper.insert(employee);
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
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getUsername,username));
        if(employee==null){
            throw new ApiException(ErrorCode.Business.ADMIN_LOGIN_NOTFOUND_ERROR);
        }else{
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("admin","test"));
            return new AuthDetails(employee,arrayList);
        }
    }
}
