package com.chr.admin.pojo.vo.resp;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserRespVo {

    private Integer id;
    private String username;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
