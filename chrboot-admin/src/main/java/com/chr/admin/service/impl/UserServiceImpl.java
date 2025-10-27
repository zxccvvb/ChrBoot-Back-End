package com.chr.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.pojo.dto.UserAddDTO;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserPageQueryDTO;
import com.chr.admin.pojo.dto.UserUpdateDTO;
import com.chr.admin.pojo.vo.UserVO;
import com.chr.common.annotation.AutoFill;
import com.chr.common.enums.AutoFillType;
import com.chr.common.exception.BizException;
import com.chr.common.enums.BizExceptionEnume;
import com.chr.admin.pojo.User;
import com.chr.admin.service.UserService;
import com.chr.admin.mapper.UserMapper;
import com.chr.common.result.PageResult;
import com.chr.common.utils.context.BaseContext;
import com.chr.common.utils.jwt.JwtHelper;
import com.chr.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private JwtHelper jwtHelper;

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
            UserVO userVo = new UserVO();
            BeanUtils.copyProperties(user,userVo);
            userVOList.add(userVo);
        }
        PageResult<UserVO> pageResult = new PageResult<>(page.getTotal(), userVOList);
        return Result.ok(pageResult);
    }

    @Override
    @AutoFill(AutoFillType.INSERT)
    public Result register(UserAddDTO userAddDTO) {
        User user = new User();
        BeanUtils.copyProperties(userAddDTO,user);
        int rows = userMapper.insert(user);
        if(rows>0){
            return Result.ok("注册成功");
        }else{
            throw new BizException(BizExceptionEnume.USER_REGISTER_ERROR);
        }
    }

    @Override
    public Result login(UserLoginDTO userLoginDTO) {
        boolean existUser = userMapper.exists(new LambdaQueryWrapper<User>().eq(User::getUsername,userLoginDTO.getUsername()));
        if(!existUser){
            throw new BizException(BizExceptionEnume.USER_NOT_EXIST_ERROR);
        }
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername,userLoginDTO.getUsername()));
        if(!Objects.equals(user.getPassword(), userLoginDTO.getPassword())){
            throw new BizException(BizExceptionEnume.USER_PASSWORD_ERROR);
        }
        String token = jwtHelper.createToken(user.getId());

        return Result.ok(token);
    }

    @Override
    public Result updateById(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        int rows = userMapper.updateById(user);
        if(rows==0){
            throw new BizException(BizExceptionEnume.USER_UPDATE_ERROR);
        }
        return Result.ok("修改成功");
    }


    @Override
    public Result getUserInfo() {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.selectById(id);
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(user,userVo);
        return Result.ok(userVo);
    }
}




