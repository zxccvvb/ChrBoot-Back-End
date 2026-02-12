package com.chr.domain.system.user.db;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chr.domain.system.user.query.UserQuery;


public interface SysUserService extends IService<SysUserEntity> {

    /**
     * 获取所有用户列表
     *
     * 分页
     * @param userQuery
     * @return
     */
    IPage<SysUserEntity> getUserListPage(UserQuery userQuery);



    /**
     *添加新用户
     * @param entity
     * @return
     */
    void addUser(SysUserEntity entity);

}
