package com.anan.ananapicommon.service.impl;

import com.anan.ananapicommon.mapper.UserInterfaceInfoMapper;
import com.anan.ananapicommon.model.entity.UserInterfaceInfo;
import com.anan.ananapicommon.service.InnerUserInterfaceInfoService;
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
public class InnerUserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper,UserInterfaceInfo> implements InnerUserInterfaceInfoService {
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
       final String lock = String.valueOf(interfaceInfoId+userId);
        synchronized ( lock.intern() ) {
            UserInterfaceInfo one = getOne(new LambdaQueryWrapper<UserInterfaceInfo>().eq(UserInterfaceInfo::getInterfaceId, interfaceInfoId).eq(UserInterfaceInfo::getUserId, userId));
            if (one==null){
                return false;
            }
            one.setLeftNum(one.getLeftNum()-1);
            one.setTotalNum(one.getTotalNum()+1);
            boolean b = updateById(one);
            if (!b){
                return false;
            }
        }

        return true;
    }

}
