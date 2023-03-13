package com.anan.userservice.model.user.dto;

import lombok.Data;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 9:03
 * @Version 1.0
 */
@Data
public class UserUpdateRequest {
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;



    /**
     * 手机号
     */
    private String phone;

    private String role;

}
