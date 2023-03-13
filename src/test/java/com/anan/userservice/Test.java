package com.anan.userservice;

import com.anan.userservice.model.user.entity.User;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/10 13:42
 * @Version 1.0
 */
public class Test {
    @org.junit.jupiter.api.Test
    public void test() throws IllegalAccessException {
        User user = new User();
        user.setPhone("");
        user.setRole("admin");
        Class<? extends User> aClass = user.getClass();
        for (Field declaredField : aClass.getDeclaredFields()) {
            String name = declaredField.getName();
            declaredField.setAccessible(true);
            Object o = declaredField.get(name);
            System.out.println(o.toString());
//            System.out.println(name);
        }
    }
}
