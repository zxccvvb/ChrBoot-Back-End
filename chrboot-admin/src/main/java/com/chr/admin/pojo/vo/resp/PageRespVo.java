package com.chr.admin.pojo.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
public class PageRespVo <T>{

    private List<T> rows;
    private Long total;
    private Long pageNum;
    private Long pageSize;


}
