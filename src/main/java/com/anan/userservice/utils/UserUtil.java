package com.anan.userservice.utils;

import com.anan.userservice.common.ErrorCode;
import com.anan.userservice.common.IdRequest;
import com.anan.userservice.exception.BusinessException;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/3/10 15:42
 * @Version 1.0
 */
public class UserUtil {
    private static final ThreadLocal<IdRequest> local = new ThreadLocal<>();

    public static long getId(){
        Long id = local.get().getId();
        if (id==null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return id;
    }

    public static void setId(Long id){
        local.set(new IdRequest(id));
    }
}
