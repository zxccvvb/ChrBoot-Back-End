package com.chr.admin.security;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chr.admin.mapper.EmployeeMapper;
import com.chr.admin.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {



    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    @Override
    public void createUser(UserDetails user) {
        Employee employee = new Employee();
        employee.setUsername(user.getUsername());
        employee.setPassword(user.getPassword());
        employee.setNickname("test");
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getUsername,username));
        if(employee==null){
            throw new UsernameNotFoundException(username);
        }else{
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            return new User(
                    employee.getUsername(),
                    employee.getPassword(),
                    employee.getStatus()==1,
                    true, //账号是否未过期
                    true, //用户凭证是否未过期
                    true, //用户是否未被锁定
                    authorities //权限列表
                    );
        }
    }
}
