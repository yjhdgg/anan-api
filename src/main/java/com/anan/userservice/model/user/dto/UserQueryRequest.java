package com.anan.userservice.model.user.dto;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 9:50
 * @Version 1.0
 */
public class UserQueryRequest {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 账号
     */
    private String userAccount;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 禁用状态（0未禁用，1禁用）
     */
    private Integer status;


}
