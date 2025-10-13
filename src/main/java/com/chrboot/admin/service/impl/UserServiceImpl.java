package com.chrboot.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chrboot.admin.exception.BizException;
import com.chrboot.admin.exception.BizExceptionEnume;
import com.chrboot.admin.pojo.User;
import com.chrboot.admin.pojo.vo.req.PageVo;
import com.chrboot.admin.pojo.vo.req.UserAddVo;
import com.chrboot.admin.pojo.vo.req.UserUpdateVo;
import com.chrboot.admin.pojo.vo.resp.PageRespVo;
import com.chrboot.admin.pojo.vo.resp.UserRespVo;
import com.chrboot.admin.service.UserService;
import com.chrboot.admin.mapper.UserMapper;
import com.chrboot.admin.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service实现
* @createDate 2025-10-13 11:55:39
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getUserListPage(PageVo pageVo) {
        IPage<User> page = new Page(pageVo.getPageNum(), pageVo.getPageSize());
        userMapper.selectPage(page, null);
        List<User> userList = page.getRecords();
        List<UserRespVo> userRespVoList = new ArrayList<>();

        //脱敏
        for (User user : userList) {
            UserRespVo userRespVo = new UserRespVo();
            BeanUtils.copyProperties(user,userRespVo);
            userRespVoList.add(userRespVo);
        }
        PageRespVo<UserRespVo> userPageRespVo = new PageRespVo<>(userRespVoList,page.getTotal(),page.getCurrent(),page.getSize());
        return Result.ok(userPageRespVo);
    }

    @Override
    public Result register(UserAddVo userAddVo) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,userAddVo.getUsername());
        boolean existUsername = userMapper.exists(lambdaQueryWrapper);

        //如果用户名存在直接抛出用户名注册的业务异常
        if(existUsername){
            throw new BizException(BizExceptionEnume.USERNAME_EXIST);
        }

        User user = new User();
        BeanUtils.copyProperties(userAddVo,user);
        int rows = userMapper.insert(user);
        if(rows>0){
            return Result.ok("注册成功");
        }else{
            throw new BizException(BizExceptionEnume.USER_REGISTER_ERROR);
        }

    }

    @Override
    public Result updateUser(UserUpdateVo userUpdateVo) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateVo,user);
        User handlerUser = userMapper.selectById(userUpdateVo.getId());
        user.setVersion(handlerUser.getVersion());

        int rows = userMapper.updateById(user);
        if(rows>0){
            return Result.ok("修改成功");
        }else{
            throw new BizException(BizExceptionEnume.USER_UPDATE_ERROR);
        }
    }


}




