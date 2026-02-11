package com.chr.admin.controller.system;


import com.chr.common.result.PageResult;
import com.chr.common.result.Result;
import com.chr.domain.system.user.UserApplicationService;
import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.dto.SysUserAddDTO;
import com.chr.domain.system.user.dto.SysUserPageQueryDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.dto.SysUserUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户接口")
@RequiredArgsConstructor
public class SysUserController {

    private final UserApplicationService userApplicationService;




    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping
    @Operation(summary = "用户列表分页接口")
    public Result getUserListPage(@ParameterObject SysUserPageQueryDTO sysUserPageQueryDTO){
        PageResult<SysUser> userListPage = userApplicationService.getUserListPage(sysUserPageQueryDTO);
        return Result.ok(userListPage);
    }

    @CacheEvict(value = "user",key = "#sysUserUpdateDTO.id")
    @PutMapping
    @Operation(summary = "修改用户接口")
    public Result updateUser(@RequestBody @Valid SysUserUpdateDTO sysUserUpdateDTO){
        userApplicationService.updateUser(sysUserUpdateDTO);
        return Result.ok("");
    }

    @PostMapping
    @Operation(summary = "添加用户接口")
    public Result addUser(@RequestBody @Valid SysUserAddDTO sysUserAddDTO){
        userApplicationService.addUser(sysUserAddDTO);
        return Result.ok("");
    }

    @Cacheable(value = "user",key = "#id")
    @GetMapping("/{id}")
    @Operation(summary = "获取用户接口")
    public Result getUserById(@PathVariable Long id){
        SysUser user = userApplicationService.getUser(id);
        return Result.ok(user);
    }


}
