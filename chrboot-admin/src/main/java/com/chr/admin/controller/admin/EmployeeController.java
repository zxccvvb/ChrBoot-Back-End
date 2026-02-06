package com.chr.admin.controller.admin;


import com.chr.admin.pojo.dto.EmployeeLoginDTO;
import com.chr.admin.service.EmployeeService;
import com.chr.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/admin/employee")
@Tag(name="员工接口")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(summary = "员工登录接口")
    public Result login(@RequestBody @Valid EmployeeLoginDTO employeeLoginDTO){
        log.info("员工登录：{}",employeeLoginDTO);
        Result result = employeeService.login(employeeLoginDTO);
        return result;
    }


    @PostMapping("/register")
    @Operation(summary = "员工注册接口")
    public Result register(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        log.info("员工注册：{}",employeeLoginDTO);
        Result result = employeeService.register(employeeLoginDTO);
        return result;
    }

    @GetMapping("/info")
    @Operation(summary = "员工信息接口")
    public Result getUserInfo(){
        log.info("获取员工信息");
        Result result = employeeService.info();
        return result;
    }
}
