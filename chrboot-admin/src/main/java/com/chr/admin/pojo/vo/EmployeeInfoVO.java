package com.chr.admin.pojo.vo;

import com.chr.common.enums.dictionary.DictionaryData;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class EmployeeInfoVO implements Serializable {
    private Long id;
    private String nickname;
    private String username;
    private Integer status;

    private Map<String, List<DictionaryData>> dictionary;
}
