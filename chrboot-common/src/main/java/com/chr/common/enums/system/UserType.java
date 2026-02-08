package com.chr.common.enums.system;

import com.chr.common.enums.dictionary.CssTag;
import com.chr.common.enums.dictionary.Dictionary;
import com.chr.common.enums.dictionary.DictionaryEnum;

@Dictionary(name = "system.userType")
public enum UserType implements DictionaryEnum<Integer> {
    ADMIN(1, "管理员", CssTag.PRIMARY),
    NORMAL(0, "普通用户", CssTag.DANGER);

    private final Integer value;
    private final String label;
    private final String cssTag;


    UserType(Integer value, String label, String cssTag) {
        this.value = value;
        this.label = label;
        this.cssTag = cssTag;
    }

    @Override
    public String cssTag() {
        return cssTag;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String description() {
        return label;
    }
}
