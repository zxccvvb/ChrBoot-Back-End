package com.chr.admin.controller.system;


import com.chr.common.result.Result;
import com.chr.domain.system.user.db.SysUserService;
import com.chr.domain.system.user.dto.SysUserPageQueryDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.dto.SysUserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户接口")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;




    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping
    @Operation(summary = "用户列表分页接口")
    public Result getUserListPage(@ParameterObject SysUserPageQueryDTO sysUserPageQueryDTO){
        log.info("用户列表分页接口：{}", sysUserPageQueryDTO);
        Result result = sysUserService.getUserListPage(sysUserPageQueryDTO);
        return  result;
    }

    @CacheEvict(value = "user",key = "#sysUserUpdateDTO.id")
    @PutMapping
    @Operation(summary = "修改用户接口")
    public Result updateUser(@RequestBody @Valid SysUserUpdateDTO sysUserUpdateDTO){
        log.info("根据id修改用户接口：{}", sysUserUpdateDTO);
        Result result = sysUserService.updateUser(sysUserUpdateDTO);
        return result;
    }

    @PostMapping
    @Operation(summary = "添加用户接口")
    public Result addUser(@RequestBody @Valid SysUserRegisterDTO sysUserRegisterDTO){
        log.info("添加用户接口：{}", sysUserRegisterDTO);
        Result result = sysUserService.addUser(sysUserRegisterDTO);
        return result;
    }

    @Cacheable(value = "user",key = "#id")
    @GetMapping("/{id}")
    @Operation(summary = "获取用户接口")
    public Result getUserById(@PathVariable Long id){
        log.info("获取用户接口：{}",id);
        Result result = sysUserService.getUser(id);
        return result;
    }


}
