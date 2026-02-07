package com.chr.admin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.mapper.UserMapper;
import com.chr.admin.pojo.User;
import com.chr.admin.pojo.dto.UserLoginDTO;
import com.chr.admin.pojo.dto.UserRegisterDTO;
import com.chr.admin.pojo.vo.UserInfoVO;
import com.chr.admin.security.AuthDetails;
import com.chr.admin.security.DBUserDetailsManager;
import com.chr.admin.service.AppUserService;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.properties.JwtProperties;
import com.chr.common.result.Result;
import com.chr.common.utils.jwt.JwtHelper;
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


@Slf4j
@Service
public class AppUserServiceImpl extends ServiceImpl<UserMapper, User>
    implements AppUserService {

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
            throw new ApiException(Business.ADMIN_LOGIN_PASSOWRD_ERROR);
        }

        AuthDetails principal = (AuthDetails) authenticate.getPrincipal();
        User user = principal.getAuth();
        Long id = user.getId();

        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,id);
        String token =  JwtHelper.createJWT(jwtProperties.getAppSecretKey(),jwtProperties.getAppTtl(),claims);
        //登陆时缓存中保存当前用户信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.APP_LOGIN + id,principal);
        //登陆时缓存中保存当前用户设备token信息
        redisTemplate.opsForValue().set(JwtClaimsConstant.APP_ADVICE+id,token);

        return Result.ok(token);
    }

    @Override
    public Result logout() {
        AuthDetails principal = (AuthDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = principal.getAuth();
        Long id = user.getId();
        redisTemplate.delete(JwtClaimsConstant.APP_LOGIN+id);
        redisTemplate.delete(JwtClaimsConstant.APP_ADVICE+id);
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



}




