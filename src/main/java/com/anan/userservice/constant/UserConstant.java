package com.anan.userservice.constant;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 9:35
 * @Version 1.0
 */
public interface UserConstant {
    String  USER_INFO_CACHE = "user:login:";
    long USER_INFO_CACHE_TTL = 7 * 24 * 60 * 60;

    String USER_ADMIN_ROLE = "admin";
}
