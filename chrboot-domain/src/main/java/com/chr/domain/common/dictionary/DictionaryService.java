package com.chr.domain.common.dictionary;


import com.chr.common.enums.dictionary.DictionaryData;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    Map<String, List<DictionaryData>> getDictionaryList();
}
