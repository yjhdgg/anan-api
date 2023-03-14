package com.anan.ananapicommon.service.impl;

import com.anan.ananapicommon.mapper.InterfaceInfoMapper;
import com.anan.ananapicommon.model.entity.InterfaceInfo;
import com.anan.ananapicommon.service.InnerInterfaceInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author 13630
 * @Description TODO
 * @Date 2023/3/13 16:35
 * @Version 1.0
 */
@Service
public class InnerInterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo> implements InnerInterfaceInfoService {
    @Override
    public InterfaceInfo getInterfaceInfo(String path, String method) {
        InterfaceInfo interfaceInfo = getOne(new LambdaQueryWrapper<InterfaceInfo>().eq(InterfaceInfo::getUrl, path).eq(InterfaceInfo::getMethod, method));
        if (interfaceInfo==null){
            return null;
        }
        return interfaceInfo;
    }
}
