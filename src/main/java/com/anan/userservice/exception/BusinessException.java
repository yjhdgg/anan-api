package com.anan.userservice.exception;

import com.anan.userservice.common.ErrorCode;

/**
 * @Author anan
 * @Description 业务异常类
 * @Date 2023/3/9 9:11
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
