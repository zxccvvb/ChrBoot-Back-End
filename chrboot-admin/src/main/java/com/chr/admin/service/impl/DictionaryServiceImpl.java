package com.chr.admin.service.impl;

import com.chr.common.enums.dictionary.DictionaryData;
import com.chr.admin.service.DictionaryService;
import com.chr.common.enums.dictionary.DictionaryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {


    @Autowired
    DictionaryUtils dictionaryUtils;

    @Override
    public Map<String, List<DictionaryData>> getDictionaryList() {
        return dictionaryUtils.dictionaryCache();
    }



}
