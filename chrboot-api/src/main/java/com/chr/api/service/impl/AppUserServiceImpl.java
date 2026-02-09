package com.chr.api.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.admin.security.DBUserDetailsManager;
import com.chr.admin.service.AppUserService;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.common.StatusEnum;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.properties.JwtProperties;
import com.chr.common.result.Result;
import com.chr.common.utils.jwt.JwtHelper;
import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.user.dto.SysUserLoginDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.login.AuthDetails;
import com.chr.domain.system.user.vo.SysUserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
public class AppUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
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
     * @param sysUserLoginDTO
     * @return
     */
    @Override
    public Result login(SysUserLoginDTO sysUserLoginDTO) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(sysUserLoginDTO.getUsername(), sysUserLoginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if(Objects.isNull(authenticate)){
            throw new ApiException(Business.LOGIN_PASSOWRD_ERROR);
        }

        AuthDetails principal = (AuthDetails) authenticate.getPrincipal();
        SysUser sysUser = principal.getAuth();

        //判断用户是否启用
        if(sysUser.getStatus().equals(StatusEnum.DISABLE.getValue())){
            throw new ApiException(Business.LOGIN_STATUS_ERROR);
        }
        Long id = sysUser.getId();

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
        SysUser sysUser = principal.getAuth();
        Long id = sysUser.getId();
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
        SysUser sysUser = principal.getAuth();
        SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
        BeanUtils.copyProperties(sysUser, sysUserInfoVO);
        sysUserInfoVO.setDictionary(dictionaryUtils.dictionaryCache());
        return Result.ok(sysUserInfoVO);
    }

    /**
     * 用户注册接口
     * @param sysUserRegisterDTO
     * @return
     */
    @Override
    public Result register(SysUserRegisterDTO sysUserRegisterDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserRegisterDTO, sysUser);
        AuthDetails authDetails = new AuthDetails(sysUser, null);
        dbUserDetailsManager.createUser(authDetails);
        return Result.ok("");
    }



}




