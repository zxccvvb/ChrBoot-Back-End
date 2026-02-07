package com.chr.admin.service;

import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserRegisterDTO;
import com.chr.common.result.Result;

public interface AppUserService {

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
}
