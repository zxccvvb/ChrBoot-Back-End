package com.chr.admin.service;

import com.chr.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.dto.*;
import com.chr.common.result.Result;

public interface AdminUserService extends IService<User> {

    /**
     * 登录接口
     * @param userLoginDTO
     * @return
     */
    Result login(UserLoginDTO userLoginDTO);

    /**
     * 退出登录接口
     * @return
     */
    Result logout();


    /**
     * 信息接口
     * @return
     */
    Result info();

    /**
     * 注册接口
     * @param userRegisterDTO
     * @return
     */
    Result register(UserRegisterDTO userRegisterDTO);


    //    *************管理端*************
    /**
     * 获取所有用户列表
     * 分页
     * @param userPageQueryDTO
     * @return
     */
    Result getUserListPage(UserPageQueryDTO userPageQueryDTO);


    /**
     * 修改用户信息
     * @param userUpdateDTO
     * @return
     */
    Result updateUser(UserUpdateDTO userUpdateDTO);


    /**
     *添加新用户
     * @param userRegisterDTO
     * @return
     */
    Result addUser(UserRegisterDTO userRegisterDTO);

    /**
     * 获取用户
     * @param id
     * @return
     */
    Result getUser(Long id);
}
