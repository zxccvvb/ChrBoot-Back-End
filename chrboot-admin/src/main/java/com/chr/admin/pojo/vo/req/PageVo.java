package com.chr.admin.pojo.vo.req;


import lombok.Data;

@Data
public class PageVo {

    private int pageNum = 1;
    private int pageSize = 10;
}
