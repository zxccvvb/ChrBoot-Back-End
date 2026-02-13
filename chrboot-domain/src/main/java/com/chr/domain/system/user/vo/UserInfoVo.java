package com.chr.domain.system.user.vo;

import com.chr.common.enums.dictionary.DictionaryData;
import com.chr.domain.system.menu.vo.MenuVO;
import com.chr.domain.system.user.db.SysUserEntity;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

@Data
public class UserInfoVo{

    public UserInfoVo(SysUserEntity entity){
        if(entity != null){
            BeanUtils.copyProperties(entity, this);
        }
    }
    private Long id;
    private String nickname;
    private String username;
    private Integer status;

    private Map<String, List<DictionaryData>> dictionary;
    private List<String> buttons;
    private List<MenuVO> routes;
}
