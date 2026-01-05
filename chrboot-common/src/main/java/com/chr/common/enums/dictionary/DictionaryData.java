package com.chr.common.enums.dictionary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class DictionaryData implements Serializable {
    private String label;
    private Integer value;
    private String cssTag;

    public DictionaryData(DictionaryEnum enumType) {
        if (enumType != null) {
            this.label = enumType.description();
            this.value = (Integer) enumType.getValue();
            this.cssTag = enumType.cssTag();
        }
    }
}
