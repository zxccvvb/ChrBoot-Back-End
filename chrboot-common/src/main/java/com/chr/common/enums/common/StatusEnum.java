package com.chr.common.enums.common;

import com.chr.common.enums.dictionary.CssTag;
import com.chr.common.enums.dictionary.Dictionary;
import com.chr.common.enums.dictionary.DictionaryEnum;
import lombok.Getter;


@Dictionary(name = "common.status")
public enum StatusEnum implements DictionaryEnum<Integer> {

    ENABLE(1, "正常", CssTag.PRIMARY),
    DISABLE(0, "停用", CssTag.DANGER);

    private final Integer value;
    private final String label;
    private final String cssTag;


    StatusEnum(Integer value, String label, String cssTag) {
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
