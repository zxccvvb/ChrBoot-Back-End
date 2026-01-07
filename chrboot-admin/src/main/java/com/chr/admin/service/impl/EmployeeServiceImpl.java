package com.chr.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.pojo.Employee;
import com.chr.admin.pojo.User;
import com.chr.admin.pojo.dto.EmployeeLoginDTO;
import com.chr.admin.pojo.vo.EmployeeInfoVO;
import com.chr.admin.pojo.vo.UserInfoVO;
import com.chr.admin.service.EmployeeService;
import com.chr.admin.mapper.EmployeeMapper;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.common.exception.error.ErrorCode.Business;

import com.chr.common.properties.JwtProperties;
import com.chr.common.result.Result;
import com.chr.common.utils.context.BaseContext;
import com.chr.common.utils.jwt.JwtHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmployeeMapper employeeMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    DictionaryUtils dictionaryUtils;


    /**
     * 员工登录接口
     * @param employeeLoginDTO
     * @return
     */
    @Override
    public Result login(EmployeeLoginDTO employeeLoginDTO) {
        boolean existEmployee = employeeMapper.exists(new LambdaQueryWrapper<Employee>().eq(Employee::getUsername,employeeLoginDTO.getUsername()));
        if(!existEmployee){
            throw new ApiException(Business.EMPLOYEE_NOT_EXIST_ERROR);
        }
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>().eq(Employee::getUsername,employeeLoginDTO.getUsername()));
        if(!Objects.equals(employee.getPassword(),employeeLoginDTO.getPassword())){
            throw new ApiException(Business.EMPLOYEE_PASSWORD_ERROR);
        }

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token =  JwtHelper.createJWT(jwtProperties.getAdminSecretKey(),jwtProperties.getAdminTtl(),claims);

        return Result.ok(token);
    }

    /**
     * 员工信息接口
     * @return
     */
    @Override
    public Result info() {
        Long id = BaseContext.getCurrentId();
        Employee employee = employeeMapper.selectById(id);
        EmployeeInfoVO employeeInfoVO = new EmployeeInfoVO();
        BeanUtils.copyProperties(employee, employeeInfoVO);
        employeeInfoVO.setDictionary(dictionaryUtils.dictionaryCache());
        return Result.ok(employeeInfoVO);
    }
}




