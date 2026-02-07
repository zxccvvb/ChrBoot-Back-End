package com.chr.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.pojo.Employee;
import com.chr.admin.pojo.dto.EmployeeLoginDTO;
import com.chr.admin.pojo.dto.EmployeeRegisterDTO;
import com.chr.admin.pojo.vo.EmployeeInfoVO;
import com.chr.admin.security.DBUserDetailsManager;
import com.chr.admin.security.AuthDetails;
import com.chr.admin.service.EmployeeService;
import com.chr.admin.mapper.EmployeeMapper;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;

import com.chr.common.properties.JwtProperties;
import com.chr.common.result.Result;
import com.chr.common.utils.jwt.JwtHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
* @author 19774
* @description 针对表【chr_employee(用户表)】的数据库操作Service实现
* @createDate 2026-01-07 14:25:45
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
    implements EmployeeService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private DictionaryUtils dictionaryUtils;
    @Autowired
    private DBUserDetailsManager DBUserDetailsManager;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 员工登录接口
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Result login(EmployeeLoginDTO employeeLoginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(employeeLoginDTO.getUsername(),employeeLoginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if(Objects.isNull(authenticate)){
            throw new ApiException(Business.ADMIN_LOGIN_PASSOWRD_ERROR);
        }

        AuthDetails<Employee> principal = (AuthDetails<Employee>) authenticate.getPrincipal();
        Employee employee = principal.getAuth();
        Long id = employee.getId();

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,id);
        String token =  JwtHelper.createJWT(jwtProperties.getAdminSecretKey(),jwtProperties.getAdminTtl(),claims);
        //登陆时缓存中保存当前用户信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.ADMIN_LOGIN + id,principal);
        //登陆时缓存中保存当前用户设备token信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.ADMIN_ADVICE+id,token);

        return Result.ok(token);
    }

    @Override
    public Result logout() {
        AuthDetails<Employee> principal = (AuthDetails<Employee>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = principal.getAuth();
        Long id = employee.getId();
        redisTemplate.delete(JwtClaimsConstant.ADMIN_LOGIN+id);
        redisTemplate.delete(JwtClaimsConstant.ADMIN_ADVICE+id);
        return Result.ok("");
    }

    /**
     * 员工信息接口
     * @return
     */
    @Override
    public Result info() {
        AuthDetails<Employee> principal = (AuthDetails<Employee>) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Employee employee = principal.getAuth();
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        BeanUtils.copyProperties(employee, employeeInfoVO);
        employeeInfoVO.setDictionary(dictionaryUtils.dictionaryCache());
        return Result.ok(employeeInfoVO);
    }

    /**
     * 员工注册接口
     * @param employeeRegisterDTO
     * @return
     */
    @Override
    public Result register(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRegisterDTO,employee);
        AuthDetails<Employee> authDetails = new AuthDetails<>(employee, null);
        DBUserDetailsManager.createUser(authDetails);
        return Result.ok("");
    }
}




