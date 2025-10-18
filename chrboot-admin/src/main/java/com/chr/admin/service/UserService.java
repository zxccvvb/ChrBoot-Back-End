package com.chr.admin.service;

import com.chr.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.admin.pojo.dto.UserAddDTO;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserUpdateDto;
import com.chr.admin.pojo.vo.req.PageVo;
import com.chr.admin.pojo.vo.req.UserAddVo;
import com.chr.admin.pojo.vo.req.UserUpdateVo;
import com.chr.common.annotation.AutoFill;
import com.chr.common.enums.AutoFillType;
import com.chr.common.result.Result;

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
     * 注册
     * @return
     */
    Result register(UserAddDTO userAddDTO);


    /**
     * 修改用户信息
     * @return
     */
    Result update(UserUpdateVo user);

    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    Result login(UserLoginDTO userLoginDTO);

    /**
     * 根据id去修改用户信息
     * @param userUpdateDto
     * @return
     */
    Result updateById(UserUpdateDto userUpdateDto);
}
