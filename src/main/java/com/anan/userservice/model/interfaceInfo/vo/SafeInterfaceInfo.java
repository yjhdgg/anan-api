package com.anan.userservice.model.interfaceInfo.vo;

import lombok.Data;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 16:46
 * @Version 1.0
 */
@Data
public class SafeInterfaceInfo {
    /**
     *
     */
    private Long id;


    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求参数
     */
    private String requestParams;

    /**
     * 请求头
     */
    private String requestHeader;

    /**
     * 响应头
     */
    private String responseHeader;

    /**
     * 禁用状态（0未禁用，1禁用）
     */
    private Integer status;
}
