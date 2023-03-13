package com.anan.userservice.model.user.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 9:20
 * @Version 1.0
 */
@Data
public class SafeUser implements Serializable {
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
     * 账号
     */
    private String userAccount;


    /**
     * 手机号
     */
    private String phone;

    /**
     * 公钥
     */
    private String accessKey;

    /**
     * 角色(user普通用户role管理员)
     */
    private String role;

    private static final long serialVersionUID = 1L;
}
