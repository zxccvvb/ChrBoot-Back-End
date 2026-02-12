package com.chr.domain.system.user.vo;

import com.chr.domain.system.user.db.SysUserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserVo{


    public UserVo(SysUserEntity entity){
        if(entity != null){
            BeanUtils.copyProperties(entity, this);
        }
    }

    private Long id;
    private String nickname;
    private String username;
    private String password;
    private Integer status;
    private Integer userType;
    private Boolean isDeleted;
    private Long createUser;
    private Long updateUser;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
