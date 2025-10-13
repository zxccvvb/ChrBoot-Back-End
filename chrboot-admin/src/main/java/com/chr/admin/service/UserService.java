package com.chr.admin.service;

import com.chr.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.vo.req.PageVo;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.admin.utils.Result;
import jakarta.validation.Valid;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service
* @createDate 2025-10-13 11:55:39
*/
public interface UserService extends IService<User> {


    /**
     * 获取所有用户列表
     * 分页
     *
     * @return
     */
    Result getUserListPage(PageVo pageVo);


    /**
     * 注册用户
     * @return
     */
    Result register(UserAddVo user);


    /**
     * 修改用户信息
     * @return
     */
    Result updateUser(UserUpdateVo user);
}
