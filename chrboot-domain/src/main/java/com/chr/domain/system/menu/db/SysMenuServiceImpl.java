package com.chr.domain.system.menu.db;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chr.domain.system.menu.db.mapper.SysMenuMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuEntity>
    implements SysMenuService {

    private final SysMenuMapper sysMenuMapper;


    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        return sysMenuMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public List<SysMenuEntity> getRoutesByUserId(Long userId) {
        return sysMenuMapper.selectRoutesByUserId(userId);

    }
}




