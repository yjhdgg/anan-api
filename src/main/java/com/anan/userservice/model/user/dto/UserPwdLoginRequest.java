package com.anan.userservice.model.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 8:46
 * @Version 1.0
 */
@Data
public class UserPwdLoginRequest implements Serializable {



    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;


    private static final long serialVersionUID = 1L;
}
