package com.chr.admin.service;

import com.chr.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.dto.UserAddDTO;
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
    Result register(UserAddDTO userAddDTO);


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
     * 根据id去修改用户信息
     * @param userUpdateDTO
     * @return
     */
    Result updateById(UserUpdateDTO userUpdateDTO);


    /**
     * 获取用户信息
     * @return
     */
    Result getUserInfo();
}
