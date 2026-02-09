package com.chr.admin.customize.service.login;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.domain.system.user.db.mapper.SysUserMapper;
import com.chr.domain.system.menu.db.mapper.SysMenuMapper;
import com.chr.domain.system.menu.db.SysMenu;
import com.chr.domain.system.menu.vo.SysAdminMenuVO;
import com.chr.domain.system.user.db.SysUser;
import com.chr.domain.system.user.dto.SysUserLoginDTO;
import com.chr.domain.system.user.dto.SysUserRegisterDTO;
import com.chr.domain.system.user.vo.SysUserInfoVO;
import com.chr.domain.system.user.login.AuthDetails;
import com.chr.common.constant.JwtClaimsConstant;
import com.chr.common.enums.common.StatusEnum;
import com.chr.common.enums.dictionary.DictionaryUtils;
import com.chr.common.enums.system.UserType;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.properties.JwtProperties;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author dell
* @description 针对表【chr_user】的数据库操作Service实现
* @createDate 2025-10-13 11:55:39
*/

@Slf4j
@Service
public class LoginUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements LoginUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private DictionaryUtils dictionaryUtils;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysMenuMapper sysMenuMapper;


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
        //管理端登录判断用户类型
        if(sysUser.getUserType().equals(UserType.NORMAL.getValue())){
            throw new ApiException(Business.ADMIN_PERMISSION_ERROR);
        }
        //判断用户是否启用
        if(sysUser.getStatus().equals(StatusEnum.DISABLE.getValue())){
            throw new ApiException(Business.LOGIN_STATUS_ERROR);
        }
        Long id = sysUser.getId();

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
        SysUser sysUser = principal.getAuth();
        Long id = sysUser.getId();
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
        SysUser sysUser = principal.getAuth();
        SysUserInfoVO sysUserInfoVO = new SysUserInfoVO();
        BeanUtils.copyProperties(sysUser, sysUserInfoVO);
        sysUserInfoVO.setDictionary(dictionaryUtils.dictionaryCache());
        sysUserInfoVO.setButtons(principal.getPermissions());
        List<SysMenu> sysMenus = sysMenuMapper.selectRoutesByUserId(sysUser.getId());
        List<SysAdminMenuVO> sysAdminMenuVOS = new ArrayList<>();
        for(SysMenu sysMenu : sysMenus){
            SysAdminMenuVO sysAdminMenuVO = new SysAdminMenuVO();
            BeanUtils.copyProperties(sysMenu,sysAdminMenuVO);
            sysAdminMenuVOS.add(sysAdminMenuVO);
        }
        sysUserInfoVO.setRoutes(sysAdminMenuVOS);
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
        SysUser newSysUser = new SysUser();
        BeanUtils.copyProperties(authDetails.getAuth(), newSysUser);
        newSysUser.setPassword(passwordEncoder.encode(newSysUser.getPassword()));
        sysUserMapper.insert(newSysUser);
        return Result.ok("");
    }


}




