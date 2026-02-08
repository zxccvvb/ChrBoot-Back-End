package com.chr.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.mapper.SysAdminMenuMapper;
import com.chr.admin.pojo.SysAdminMenu;
import com.chr.admin.pojo.dto.*;
import com.chr.admin.pojo.vo.SysAdminMenuVO;
import com.chr.admin.pojo.vo.UserInfoVO;
import com.chr.admin.security.AuthDetails;
import com.chr.admin.security.DBUserDetailsManager;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.admin.pojo.User;
import com.chr.admin.service.AdminUserService;
import com.chr.admin.mapper.UserMapper;
import com.chr.common.enums.common.StatusEnum;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.enums.system.UserType;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.properties.JwtProperties;
import com.chr.common.result.PageResult;
import com.chr.common.utils.jwt.JwtHelper;
import com.chr.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service实现
* @createDate 2025-10-13 11:55:39
*/

@Slf4j
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User>
    implements AdminUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DictionaryUtils dictionaryUtils;
    @Autowired
    private DBUserDetailsManager dbUserDetailsManager;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysAdminMenuMapper sysAdminMenuMapper;


    /**
     * 用户登录接口
     * @param userLoginDTO
     * @return
     */
    @Override
    public Result login(UserLoginDTO userLoginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(),userLoginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if(Objects.isNull(authenticate)){
            throw new ApiException(Business.LOGIN_PASSOWRD_ERROR);
        }

        AuthDetails principal = (AuthDetails) authenticate.getPrincipal();
        User user = principal.getAuth();
        //管理端登录判断用户类型
        if(user.getUserType().equals(UserType.NORMAL.getValue())){
            throw new ApiException(Business.ADMIN_PERMISSION_ERROR);
        }
        //判断用户是否启用
        if(user.getStatus().equals(StatusEnum.DISABLE.getValue())){
            throw new ApiException(Business.LOGIN_STATUS_ERROR);
        }
        Long id = user.getId();

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,id);
        String token =  JwtHelper.createJWT(jwtProperties.getAdminSecretKey(),jwtProperties.getAdminTtl(),claims);
        //登陆时缓存中保存当前用户信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.ADMIN_LOGIN + id,principal);
        //登陆时缓存中保存当前用户设备token信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.ADMIN_ADVICE+id,token);

        return Result.ok(token);
    }

    @Override
    public Result logout() {
        AuthDetails principal = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getAuth();
        Long id = user.getId();
        redisTemplate.delete(JwtClaimsConstant.ADMIN_LOGIN+id);
        redisTemplate.delete(JwtClaimsConstant.ADMIN_ADVICE+id);
        return Result.ok("");
    }

    /**
     * 用户信息接口
     * @return
     */
    @Override
    public Result info() {
        AuthDetails principal = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getAuth();
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(user, userInfoVO);
        userInfoVO.setDictionary(dictionaryUtils.dictionaryCache());
        userInfoVO.setButtons(principal.getPermissions());
        List<SysAdminMenu> sysAdminMenus = sysAdminMenuMapper.selectRoutesByUserId(user.getId());
        List<SysAdminMenuVO> sysAdminMenuVOS = new ArrayList<>();
        for(SysAdminMenu sysAdminMenu : sysAdminMenus){
            SysAdminMenuVO sysAdminMenuVO = new SysAdminMenuVO();
            BeanUtils.copyProperties(sysAdminMenu,sysAdminMenuVO);
            sysAdminMenuVOS.add(sysAdminMenuVO);
        }
        userInfoVO.setRoutes(sysAdminMenuVOS);
        return Result.ok(userInfoVO);
    }

    /**
     * 用户注册接口
     * @param userRegisterDTO
     * @return
     */
    @Override
    public Result register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO,user);
        AuthDetails authDetails = new AuthDetails(user, null);
        dbUserDetailsManager.createUser(authDetails);
        return Result.ok("");
    }





    @Override
    public Result getUserListPage(UserPageQueryDTO userPageQueryDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(userPageQueryDTO.getNickname()!=null,User::getNickname,userPageQueryDTO.getNickname());
        IPage<User> page = new Page(userPageQueryDTO.getPageNum(), userPageQueryDTO.getPageSize());
        userMapper.selectPage(page, queryWrapper);
        List<User> userList = page.getRecords();
        PageResult<User> pageResult = new PageResult<>(page.getTotal(), userList);
        return Result.ok(pageResult);
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
        return Result.ok(user);
    }
}




