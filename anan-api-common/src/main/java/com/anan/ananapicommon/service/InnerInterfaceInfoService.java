package com.anan.ananapicommon.service;

import com.anan.ananapicommon.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/13 16:21
 * @Version 1.0
 */
public interface InnerInterfaceInfoService extends IService<InterfaceInfo> {
    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param path
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
