package com.chr.api.controller.common;


import com.chr.domain.common.dictionary.DictionaryService;
import com.chr.common.enums.dictionary.DictionaryData;
import com.chr.common.exception.ApiException;
import com.chr.common.exception.error.ErrorCode.Business;
import com.chr.common.result.Result;
import com.chr.common.utils.oss.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/common")
@Tag(name = "通用接口")
@Slf4j
public class CommonController {


    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * oss文件上传接口
     */
    @PostMapping("/upload")
    @Operation(summary = "文件上传接口")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        try {
            //获取原始的文件名
            String originalFilename = file.getOriginalFilename();
            //从原始文件名中把后缀拿出
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新的文件名
            String objectName = UUID.randomUUID().toString() + suffix;
            String path = aliOssUtil.upload(file.getBytes(),objectName);
            log.info("文件上传成功，路径为：{}",path);
            return Result.ok(path);
        } catch (IOException e) {
            throw new ApiException(Business.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 全局字典接口
     */
    @GetMapping("/dictionary")
    @Operation(summary = "全局字典获取接口")
    public Result<Map<String, List<DictionaryData>>> getDictionaryList(){
        Map<String, List<DictionaryData>> dictionaryList = dictionaryService.getDictionaryList();
        return Result.ok(dictionaryList);
    }
}
