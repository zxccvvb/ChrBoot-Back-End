package com.chr.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.pojo.dto.UserRegisterDTO;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserPageQueryDTO;
import com.chr.admin.pojo.dto.UserUpdateDTO;
import com.chr.admin.pojo.vo.UserInfoVO;
import com.chr.admin.pojo.vo.UserVO;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.admin.pojo.User;
import com.chr.admin.service.UserService;
import com.chr.admin.mapper.UserMapper;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.properties.JwtProperties;
import com.chr.common.result.PageResult;
import com.chr.common.utils.context.BaseContext;
import com.chr.common.utils.jwt.JwtHelper;
import com.chr.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service实现
* @createDate 2025-10-13 11:55:39
*/

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public Result getUserListPage(UserPageQueryDTO userPageQueryDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userPageQueryDTO.getNickname()!=null,User::getNickname,userPageQueryDTO.getNickname());
        IPage<User> page = new Page(userPageQueryDTO.getPageNum(), userPageQueryDTO.getPageSize());
        userMapper.selectPage(page, queryWrapper);
        List<User> userList = page.getRecords();
        List<UserVO> userVOList = new ArrayList<>();
        //脱敏
        for (User user : userList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVOList.add(userVO);
        }
        PageResult<UserVO> pageResult = new PageResult<>(page.getTotal(), userVOList);
        return Result.ok(pageResult);
    }

    @Override
//    @AutoFill(AutoFillType.INSERT)
    public Result register(UserRegisterDTO userRegisterDTO) {
        if(!userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword())){
            throw new ApiException(Business.USER_REGISTER_CONFIRMPASSWORD_ERROR);
        }
        if(userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername,userRegisterDTO.getUsername()))){
            throw new ApiException(Business.USER_REGISTER_USERNAME_ERROR);
        }
        if(userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getNickname,userRegisterDTO.getNickname()))){
            throw new ApiException(Business.USER_REGISTER_NICKNAME_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        int rows = userMapper.insert(user);
        if(rows>0){
            return Result.ok("");
        }else{
            throw new ApiException(Business.USER_REGISTER_ERROR);
        }
    }

    @Override
    public Result login(UserLoginDTO userLoginDTO) {
        boolean existUser = userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername,userLoginDTO.getUsername()));
        if(!existUser){
            throw new ApiException(Business.USER_NOT_EXIST_ERROR);
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,userLoginDTO.getUsername()));
        if(!Objects.equals(user.getPassword(), userLoginDTO.getPassword())){
            throw new ApiException(Business.USER_PASSWORD_ERROR);
        }


        // 生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, user.getId());
        String token = JwtHelper.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);

        return Result.ok(token);
    }

    @Override
    public Result updateUser(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        int rows = userMapper.updateById(user);
        if(rows==0){
            throw new ApiException(Business.USER_UPDATE_ERROR);
        }
        return Result.ok("");
    }


    @Override
    public Result info() {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.selectById(id);
        UserInfoVO userInfoVo = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVo);
        return Result.ok(userInfoVo);
    }

    @Override
    public Result addUser(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        int rows = userMapper.insert(user);
        if(rows>0){
            return Result.ok(null);
        }
        throw new ApiException(Business.USER_ADD_ERROR);
    }

    @Override
    public Result getUser(Long id) {
        User user = userMapper.selectById(id);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return Result.ok(userVO);
    }
}




