package com.anan.userservice.model.user.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserPhoneRegisterRequest implements Serializable {


    /**
     * 手机号
     */
    private String phone;


    private static final long serialVersionUID = 1L;
}