package com.anan.userservice.common;

import com.anan.userservice.constant.QueryConstant;
import lombok.Data;



/**
 * @Author anan
 * @Description 分页请求
 * @Date 2023/3/9 9:06
 * @Version 1.0
 */
@Data
public class PageRequest<T>  {

    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认升序）
     */
    private String sortOrder = QueryConstant.SORT_ORDER_ASC;

    /**
     * 传参
     */
    private T params;
}
