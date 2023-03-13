package com.anan.userservice.utils.jwt;

import io.jsonwebtoken.Claims;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/9 8:59
 * @Version 1.0
 */
public class JwtCheckResult {
    private int errCode;

    private boolean success;

    private Claims claims;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
