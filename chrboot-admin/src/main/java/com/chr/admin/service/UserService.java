package com.chr.admin.service;

import com.chr.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.dto.UserRegisterDTO;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserPageQueryDTO;
import com.chr.admin.pojo.dto.UserUpdateDTO;
import com.chr.common.result.Result;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service
* @createDate 2025-10-13 11:55:39
*/
public interface UserService extends IService<User> {

    /**
     * 注册
     * @return
     */
    Result register(UserRegisterDTO userRegisterDTO);


    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    Result login(UserLoginDTO userLoginDTO);


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
     * 获取用户信息
     * @return
     */
    Result getUserInfo();


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
