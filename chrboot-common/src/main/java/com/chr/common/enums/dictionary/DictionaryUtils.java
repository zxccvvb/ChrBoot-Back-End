package com.chr.common.enums.dictionary;

import cn.hutool.core.util.ArrayUtil;
import com.chr.common.enums.common.StatusEnum;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DictionaryUtils {
    private static final Map<String, List<DictionaryData>> DICTIONARY_CACHE = new HashMap<String,List<DictionaryData>>();


    static {
        initDictionaryCache();
    }
    private static void initDictionaryCache() {
        // TODO 这个可以做成自动扫描
        loadInCache(StatusEnum.values());

    }
    public Map<String, List<DictionaryData>> dictionaryCache() {
        return DICTIONARY_CACHE;
    }

    private static String getDictionaryName(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        Dictionary annotation = clazz.getAnnotation(Dictionary.class);

        Objects.requireNonNull(annotation);
        return annotation.name();
    }
    private static void loadInCache(DictionaryEnum[] dictionaryEnums) {
        DICTIONARY_CACHE.put(getDictionaryName(dictionaryEnums[0].getClass()), arrayToList(dictionaryEnums));
    }

    private static List<DictionaryData> arrayToList(DictionaryEnum[] dictionaryEnums) {
        if(ArrayUtil.isEmpty(dictionaryEnums)) {
            return Arrays.asList();
        }
        return Arrays.stream(dictionaryEnums).map(DictionaryData::new).collect(Collectors.toList());
    }



}
