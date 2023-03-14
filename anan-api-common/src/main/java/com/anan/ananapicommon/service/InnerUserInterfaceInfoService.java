package com.anan.ananapicommon.service;

import com.anan.ananapicommon.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/13 16:21
 * @Version 1.0
 */
public interface InnerUserInterfaceInfoService extends IService<UserInterfaceInfo> {
    /**
     * 调用接口统计
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId);
}
