package com.chr.admin.service;

import com.chr.admin.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.dto.EmployeeLoginDTO;
import com.chr.admin.pojo.dto.EmployeeRegisterDTO;
import com.chr.common.result.Result;

/**
* @author 19774
* @description 针对表【chr_employee(用户表)】的数据库操作Service
* @createDate 2026-01-07 14:25:45
*/
public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登录接口
     * @param employeeLoginDTO
     * @return
     */
    Result login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 员工退出登录接口
     * @return
     */
    Result logout();


    /**
     * 员工信息接口
     * @return
     */
    Result info();

    /**
     * 员工注册接口
     * @param employeeRegisterDTO
     * @return
     */
    Result register(EmployeeRegisterDTO employeeRegisterDTO);
}
