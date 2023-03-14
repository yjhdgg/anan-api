package com.anan.ananapicommon.service;

import com.anan.ananapicommon.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 13630
 * @Description 用户服务
 * @Date 2023/3/13 16:21
 * @Version 1.0
 */
public interface InnerUserService extends IService<User> {
    /**
     * 数据库中查是否已分配给用户秘钥（accessKey）
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);
}
