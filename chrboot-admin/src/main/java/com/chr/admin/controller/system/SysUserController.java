package com.chr.admin.controller.system;


import com.chr.common.result.PageResult;
import com.chr.common.result.Result;
import com.chr.domain.system.user.UserApplicationService;
import com.chr.domain.system.user.db.SysUserEntity;
import com.chr.domain.system.user.command.AddUserCommand;
import com.chr.domain.system.user.query.UserQuery;
import com.chr.domain.system.user.command.UpdateUserCommand;
import com.chr.domain.system.user.vo.UserVo;
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
    public Result getUserListPage(@ParameterObject UserQuery userQuery){
        PageResult<SysUserEntity> userListPage = userApplicationService.getUserListPage(userQuery);
        return Result.ok(userListPage);
    }

    @CacheEvict(value = "user",key = "#updateUserCommand.id")
    @PutMapping
    @Operation(summary = "修改用户接口")
    public Result updateUser(@RequestBody @Valid UpdateUserCommand updateUserCommand){
        userApplicationService.updateUser(updateUserCommand);
        return Result.ok("");
    }

    @PostMapping
    @Operation(summary = "添加用户接口")
    public Result addUser(@RequestBody @Valid AddUserCommand addUserCommand){
        userApplicationService.addUser(addUserCommand);
        return Result.ok("");
    }

    @Cacheable(value = "user",key = "#id")
    @GetMapping("/{id}")
    @Operation(summary = "获取用户接口")
    public Result getUserById(@PathVariable Long id){
        UserVo userVo = userApplicationService.getUser(id);
        return Result.ok(userVo);
    }


}
