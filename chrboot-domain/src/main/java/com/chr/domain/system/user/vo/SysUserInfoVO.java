package com.chr.domain.system.user.vo;

import com.chr.common.enums.dictionary.DictionaryData;
import com.chr.domain.system.menu.vo.SysMenuVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SysUserInfoVO implements Serializable {
    private Long id;
    private String nickname;
    private String username;
    private Integer status;

    private Map<String, List<DictionaryData>> dictionary;
    private List<String> buttons;
    private List<SysMenuVO> routes;
}
